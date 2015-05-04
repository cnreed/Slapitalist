import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Game {

	public static Logger log = Logger.getLogger(Game.class);
	private static Scanner scan;
	private static int numPlayers;

	private static int companiesOnBoard; // tells Phase 2 that we can buy stock
	private static int companiesSafe;
	private static int companies41;
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;
	private static ArrayList<Company> companyList;
	private static Grid board; // TODO: This board here! - Carolyn
	private static int x, y;

	private static Player[] players;
	private static int playerIndex; // global who's turn is it.
	private static int[][] playerStockList; // numplayers then companyCID
	private static boolean companyOnBoard = false;

	private static boolean gameInPlay = true;
	private static boolean testingGame = false;

	public Game(int x, int y) {

		scan = new Scanner(System.in);

		/* initialize board */
		board = new Grid(x, y); // TODO: This board here? - Carolyn

		/* initialize players */
		players = getPlayers();

		/* figure out who goes first */
		playerIndex = whoIsFirst(board);

		/* Rearrange turn sequence so first player is first in array */
		players = rearrangePlayerSequence(playerIndex);

		/* draw tiles for each player */
		drawStartingTiles(board);

		/* initialize and build payCliff for companies */

		initCompanies();

		if (testingGame) {
			System.out.println("TESTING MERGE!");
			companyList = initCompanies();

			testingMerge();
			System.exit(0);

		}
		log.debug("Starting gameInPlay");
		playerStockList = new int[numPlayers][7];
		while (gameInPlay) {
			Player player = players[playerIndex];
			log.debug(player.name + "'s turn is started.");
			log.debug(player.name + " "
					+ Arrays.toString(playerStockList[playerIndex]));
			getMove(player, board);
			playerIndex++;
			playerIndex = playerIndex % numPlayers;
			endConditions();
			log.debug(player.name + "'s turn is ended.");

		}

		scan.close();
	}
	
	private void endConditions() {
		for(int i = 0; i < companyList.size(); i++) {
			Company comp = companyList.get(i);
			if(comp.size() == 11) {
				log.debug("companiesSafe: "+ companiesSafe);
				companiesSafe++;
			}
			if(comp.size() >= 41) {
				log.debug("Companies over 41: " + companies41);
				companies41++;
			}
		}
		if(companiesSafe == 7) {
			//Can end the game
		}
		if(companies41 > 1) {
			int choice = validator("Would you like to end the game? 1 for Yes, 0 for no", 0, 1);
			if(choice == 1) {
				System.exit(0);
			}
			
			//Can end game
		}
	}

	private void testingMerge() {
		Player player = players[playerIndex];
		Tile tile = board.getTile(0, 0);
		Tile tile2 = board.getTile(1, 0);
		tile.statusUpdate(2);
		tile2.statusUpdate(2);
		tile.setOwnerPlayer(players[0]);
		tile2.setOwnerPlayer(players[0]);
		// selectCompany(tile, tile2);
		Company comp = tile.getOwnerCompany();
		// for(int i = 0; i < 3; i++) {
		// for(int j = 0; j < 3; j++) {
		// Tile mTile = board.getTile(i, j);
		// mTile.statusUpdate(2);
		// comp.addTile(mTile);
		//
		// }
		// }
		// printTiles(comp);
		board.print();

		Tile tile3 = board.getTile(0, 2);
		Tile tile4 = board.getTile(1, 2);
		tile3.statusUpdate(2);
		tile4.statusUpdate(2);
		tile3.setOwnerPlayer(players[1]);
		tile4.setOwnerPlayer(players[1]);
		// selectCompany(tile3, tile4);
		Company comp1 = tile3.getOwnerCompany();

		board.print();

		Tile tile5 = board.getTile(0, 1);
		tile5.statusUpdate(2);
		// merge(comp, comp1, tile5);
		board.print();
		// System.out.println(tile.toString());
		printTiles(comp1);

	}

	/**
	 * 
	 * @param playerIndex
	 * @param board
	 * @return
	 */
	private boolean getMove(Player player, Grid board) {

		/* Phase 1 - Tile Placement / Company Creation / Merge */
		int choice = playerTurnPartOne(player, board);
		while(choice < 0) {
			System.out.println("I'm sorry, this tile is not playable right now, please pick a new tile");
			choice = playerTurnPartOne(player, board);
			System.out.println("choice: " + choice);
		}

		/* Phase 2 - Stock Purchase */
		if (companiesOnBoard > 0) {
			playerTurnPartTwo(player, board);
		} else {
			System.out.println("Sorry, no investment opportunities yet.");
		}
		/* Phase 3 - Draw from bag */
		return playerTurnPartThree(player, board, choice);

	}

	private int playerTurnPartOne(Player player, Grid board) {
		//TODO
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		ArrayList<Company> companies = new ArrayList<Company>();
		board.print();
		System.out.println("\nPlayer " + player.getName());
		System.out.println("Money: " + player.getCash());
		System.out.println("\nWhich tile would you like to place?");
		player.showHand();

		// Possible bug: When we have less than 6 tiles. I don't think this is
		// coded to handle less tiles
		int choice = validator("", 1, 6) - 1;

		boolean playable = playTile(player.hand[choice]);
		System.out.println("Playing " + player.hand[choice].toString());

		if (playable) {
			System.out.println(player.getName()
					+ " successfully placed down tile "

					+ player.hand[choice].toString() + ".");
			checkAdj(player.hand[choice], player.hand[choice].getRow(),
					player.hand[choice].getCol(), player, tiles, companies,
					player.hand[choice]);

		}
		else {
			return -1;
		}
		return choice;
	}

	private void playerTurnPartTwo(Player player, Grid board) {
		//log.debug(player.name + " "
		//		+ Arrays.toString(player.getPlayerStockList().toArray()));
		int buyYesOrNo = validator(
				"Would you like to buy stock in an active company?\n (1) Yes (2) No",
				1, 2);
		if (buyYesOrNo == 1) {
			int stockTotal = 0;
			while (stockTotal < 3) {
				System.out
						.println("What company would you like to buy stock in?");
				// Display Stocks, their prices, and amount left to purchase.
				int displayCount = 1;
				ArrayList<Company> tempBuyList = new ArrayList<>();
				for (Company company : companyList) {
					if (company.onBoard && company.getStockCount() > 0) {
						tempBuyList.add(company);
						System.out.println("Company Size: "
								+ company.companySize);
						System.out.println(displayCount + ". "
								+ company.getCompanyName() + " @ $"
								+ company.getSharePrice(company.companySize)
								+ " with " + company.getStockCount()
								+ " shares remaining. \nYou currently own "
								+ sharesQuery(playerIndex, company)
								+ " shares.");
						log.debug("Company: " + company.getCompanyName() +
								" Company stock: " + company.getStockCount());
						displayCount++;
					}
				}
				// player chooses stock to buy
				Company companyToBuy = tempBuyList.get(validator("", 1,
						displayCount) - 1);

				int companyStockPrice = companyToBuy
						.getSharePrice(companyToBuy.companySize);

				// how many stock do you want in this company?
				System.out.println("How many shares of stock in "
						+ companyToBuy.getCompanyName()
						+ " would you like to purchase? \n (current limit: "
						+ (3 - stockTotal) + ", current balance: "
						+ player.totalCash + ")");

				// get amount
				Integer selectionCount = validator("", 0, (3 - stockTotal));
				// if player can afford the value wanted and it's below the
				// maximum purchase count
				if (selectionCount > 0
						&& (player.totalCash >= (companyStockPrice * selectionCount))) {
					player.totalCash -= (companyStockPrice * selectionCount);
					System.out.println("New cash balance: " + player.totalCash);

					// add stock to player's stocklist,
					// remove amount from comapny's stock list
					System.out.println("Player " + player.name);
					addCertificate(companyToBuy, selectionCount, playerIndex);

					stockTotal += selectionCount;

				} else if (player.totalCash == 0) {
					break;
				}
				selectionCount = 0;
			}
		}

	}

	private boolean playerTurnPartThree(Player player, Grid board, int choice) {
		Tile newTile = board.bagPop();
		player.hand[choice] = newTile;
		return true;
	}

	/**
	 * Checks the surrounding tiles of a tile that was just laid. This function
	 * creates companies and merges companies when the opportunity arises.
	 * 
	 * @param tile
	 *            - The tile that was just placed on the board.
	 * @param x
	 *            - the x coordinate of the tile.
	 * @param y
	 *            - the y coordinate of the tile.
	 * @param player
	 *            - The player who placed the tile.
	 */
	private void checkAdj(Tile tile, int x, int y, Player player,
			ArrayList<Tile>tiles, ArrayList<Company> companies, Tile orig) {

		Tile tileTop, tileLeft, tileRight, tileBottom;
		Company top, left, right, bottom = null;


		// Check to see where we are on the board.
		board.checkBoundaries(tile, x, y);
		tile.visited = true;
		//log.debug("tile: " + tile.toString() + " x " + x + " y " + y);
		if (tile.getTop()) {
			
			tileTop = board.getTile(x - 1, y);
			top = tileTop.getOwnerCompany();
			//log.debug("tileTop.visited: " + tileTop.visited);
			if (top != null) {
				top.logPrintTiles();
				companies.add(top);
			} else if (tileTop.getStatus().equals("ONBOARD") && 
					tileTop.visited == false) {
				tiles.add(tileTop);
				//log.debug("Proceeding into tileTop");
				checkAdj(tileTop, x-1, y, player, tiles, companies, orig);
			}
		}
		
		if (tile.getLeft()) {
			
			tileLeft = board.getTile(x, y - 1);
			left = tileLeft.getOwnerCompany();
			//log.debug("tileLeft.visited: " + tileLeft.visited);
			if (left != null) {
				if (!companies.contains(left)) {
					left.logPrintTiles();
					companies.add(left);
				}
			} else if (tileLeft.getStatus().equals("ONBOARD") &&
					tileLeft.visited == false) {
				tiles.add(tileLeft);
				//log.debug("Proceeding into tileLeft");
				checkAdj(tileLeft, x, y-1, player, tiles, companies, orig);
			}
		}
		if (tile.getBottom()) {
			
			tileBottom = board.getTile(x + 1, y);
			bottom = tileBottom.getOwnerCompany();
			//log.debug("tileBottom.visited: " + tileBottom.visited);
			if (bottom != null) {
				if (!companies.contains(bottom)) {
					bottom.logPrintTiles();
					companies.add(bottom);
				}
			} else if (tileBottom.getStatus().equals("ONBOARD") &&
					tileBottom.visited == false) {
				tiles.add(tileBottom);
				//log.debug("Proceeding into tileBottom");
				checkAdj(tileBottom, x+1, y, player, tiles, companies, orig);

			}
		}
		if (tile.getRight()) {
			tileRight = board.getTile(x, y + 1);
			right = tileRight.getOwnerCompany();
			//log.debug("tileRight.visited: " + tileRight.visited);
			if (right != null) {
				if (!companies.contains(right)) {
					right.logPrintTiles();
					companies.add(right);
				}
			} else if (tileRight.getStatus().equals("ONBOARD") &&
					tileRight.visited == false) {
				tiles.add(tileRight);
				//log.debug("Proceeding into tileRight");
				checkAdj(tileRight, x, y+1, player, tiles, companies, orig);
			}
		}
		tile.visited = false;

		// If there are multiple companies on the board MERGE!
		if (companies.size() > 1 && tile == orig) {
			merge(companies, tile);
			return;
		}
		// Adds the tiles to the company if there's only one company on the
		// board.
		else if (companies.size() == 1) { // This seems redundant.
			
			Company comp = companies.get(0);
			comp.logPrintTiles();
			tile.setOwnerCompany(comp);
			comp.addTile(tile);

			if (tiles.size() > 0) {
				for (int i = 0; i < tiles.size(); i++) {
					Tile aTile = tiles.get(i);
					if (!comp.companyTiles.contains(aTile)) {
						aTile.setOwnerCompany(comp);
						comp.addTile(aTile);
						aTile.subStatusUpdate(5);
						log.debug("Company: " + comp.getCompanyName());
						log.debug("Tiles");
						comp.logPrintTiles();
					}
				}
			}
			return;
		}
		// Creates a new company
		if (tiles.size() >= 1 && tiles.size() < 5 && tile == orig) {
			if (companiesOnBoard < 7) {
				tiles.add(tile);
				Company results = selectCompany(tiles);
				System.out.println("You have selected: "
						+ results.getCompanyName());
				addCertificate(results, 1, playerIndex);
				
			} else {
				System.out
						.println("All companies are on the board. You cannot "
								+ "place this tile. Please select another tile.");
				tile.statusUpdate(3);
			}
			return;
		}

	}

	/**
	 * 
	 * @param tileInPlay
	 * @return
	 */
	private boolean playTile(Tile tileInPlay) {
		/*
		 * this casting back and forth between string and index for
		 * setting/getting status is a little clunky, we should consider
		 * revising.
		 */
		
		//TODO Fix this right here. I don't know how to get it
		if(companiesOnBoard == 7) {
			if(isAnythingNearMe(tileInPlay)) {
				return false;
			}
		}
		if (tileInPlay.getStatus().equals("INHAND")) {
			tileInPlay.setOwnerPlayer(players[playerIndex]);
			tileInPlay.statusUpdate(2);
		}
		return true;
	}
	
	private boolean isAnythingNearMe(Tile tile) {
		int x = tile.getRow();
		int y = tile.getCol();
		Tile nearMe = null;
		board.checkBoundaries(tile, x, y);
		if(tile.getTop()) {
			nearMe = board.getTile(x-1, y);
			System.out.println(nearMe.status);
			if(nearMe.status.equals("ONBOARD") && !nearMe.getSubStatus().equals("INCOMPANY")) {
				return true;
			}
		}
		if(tile.getRight()) {
			nearMe = board.getTile(x, y+1);
			System.out.println(nearMe.status);
			if(nearMe.status.equals("ONBOARD") && !nearMe.getSubStatus().equals("INCOMPANY")) {
				return true;
			}
		}
		if(tile.getBottom()) {
			nearMe = board.getTile(x+1, y);
			System.out.println(nearMe.status);
			if(nearMe.status.equals("ONBOARD") && !nearMe.getSubStatus().equals("INCOMPANY")) {
				return true;
			}
		}
		if(tile.getLeft()) {
			nearMe = board.getTile(x, y-1);
			System.out.println(nearMe.status);
			if(nearMe.status.equals("ONBOARD") && !nearMe.getSubStatus().equals("INCOMPANY")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Depending upon who is first, the players are reorganized based on
	 * seniority.
	 * 
	 * @param first
	 * @return
	 */
	private Player[] rearrangePlayerSequence(int first) {
		Player[] tempPlayers = new Player[players.length];

		for (int i = 0; i < players.length; i++) {
			tempPlayers[i] = players[(first + i) % players.length];
		}

		return tempPlayers;
	}

	/**
	 * Draws the tiles into the player's hand.
	 * 
	 * @param board
	 */
	private void drawStartingTiles(Grid board) {
		for (Player player : players) {
			Player.numHand = 0;
			for (int i = 0; i < player.handSize; i++) {
				Tile tempTile = board.bagPop();
				player.addHand(tempTile);
			}
		}
	}

	/**
	 * Chooses which player will go first.
	 * 
	 * @param board
	 * @return
	 */
	private int whoIsFirst(Grid board) {
		int pLength = players.length;
		Tile tempTile;
		ArrayList<Double> distances = new ArrayList<Double>();

		for (int i = 0; i < pLength; i++) {
			tempTile = board.bagPop();
			playTile(tempTile);

			double pythag = Math.sqrt(tempTile.col * tempTile.col
					+ tempTile.row * tempTile.row)
					+ tempTile.col;
			distances.add(pythag);

			// System.out.print(players[i].name);
			// System.out.printf(" %.2f \n", distances.get(i));
		}
		return distances.indexOf(Collections.min(distances));
	}

	/**
	 * Initializes the list of players.
	 * 
	 * @return
	 */
	private static Player[] getPlayers() {

		numPlayers = validator("Enter the number of Players (Max 6): ", 2, 6);

		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Name of player " + (i + 1) + ": ");
			String name = scan.next();
			players[i] = new Player(name, board);
		}

		return players;
	}

	/**
	 * A helper method that prints the companies that have not been selected and
	 * their tiers.
	 */
	private void companyListing() {

		String print = "Tier 1 Hotel Chains: \n\t\t";
		// System.out.println("Tier 1 Hotel Chains:\n\t ");
		if (!Rahoi.onBoard) {
			print += "Rahoi ";
		}
		if (!Tower.onBoard) {
			print += "Tower ";
		}
		print += "\nTier 2 Hotel Chains: \n\t\t ";
		if (!American.onBoard) {
			print += "American ";
		}
		if (!Worldwide.onBoard) {
			print += "WorldWide ";
		}
		if (!Festival.onBoard) {
			print += "Festival ";
		}
		print += "\nTier 3 Hotel Chains: \n\t\t";
		if (!Continental.onBoard) {
			print += "Continental ";
		}
		if (!Imperial.onBoard) {
			print += "Imperial ";
		}
		System.out.println(print);

	}

	/**
	 * A helper method that prints only the remaining companies that are not on
	 * the board. This does not print the tiers.
	 * 
	 * @return
	 */
	private String companiesRemaining() {
		String print = "";
		if (!Rahoi.onBoard)
			print += "1: Rahoi\n";
		if (!Tower.onBoard)
			print += "2: Tower\n";
		if (!American.onBoard)
			print += "3: American\n";
		if (!Worldwide.onBoard)
			print += "4: Worldwide\n";
		if (!Festival.onBoard)
			print += "5: Festival\n";
		if (!Continental.onBoard)
			print += "6: Continental\n";
		if (!Imperial.onBoard)
			print += "7: Imperial";
		return print;

	}

	/**
	 * The player gets to choose which company they want to found.
	 * 
	 * @param tiles
	 * @return
	 */
	public Company selectCompany(ArrayList<Tile> tiles) {

		System.out.println("You have an opportunity to create a company!\n"
				+ "Would you like to list the companies and their tiers?"
				+ "\n (1) Yes (2) No");
		int answer = validator("", 1, 2);
		if (answer == 1) {
			companyListing();
		}

		System.out.println(companiesRemaining());
		int index = scan.nextInt();
		while (companyList.get(index - 1).onBoard) {
			System.out.println("That company is already on the board. Please "
					+ "select another company: ");
			index = scan.nextInt();
		}
		if (companiesOnBoard == 7) {
			System.out.println("I'm sorry, all companies are on board. ");
			return null; // Let's do something else then return.
		}
		switch (index - 1) {

		case 0:
			setCompany(Rahoi, tiles);
			return Rahoi;
		case 1:
			setCompany(Tower, tiles);
			return Tower;
		case 2:
			setCompany(American, tiles);
			return American;
		case 3:
			setCompany(Worldwide, tiles);
			return Worldwide;
		case 4:
			setCompany(Festival, tiles);
			return Festival;
		case 5:
			setCompany(Continental, tiles);
			return Continental;
		case 6:
			setCompany(Imperial, tiles);
			return Imperial;
		}

		return null;

	}

	/**
	 * Initializes individual companies on the board.
	 * 
	 * @param company
	 *            - The company to be set on the board.
	 * @param tiles
	 *            - The tiles that are going to be put into the company
	 */
	public void setCompany(Company company, ArrayList<Tile> tiles) {

		// company.insertShareHolder(tiles.get(0).getOwnerPlayer().getName(),
		// 1);
		company.setOnboard();
		for (int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			company.addTile(tile);
			tile.setOwnerCompany(company);
			tile.subStatusUpdate(5);
		}
		companiesOnBoard++; // increment companies on board
		System.out.println("The company size: " + company.companySize);

	}

	/**
	 * Initializes all the companies in the game.
	 */
	static ArrayList<Company> initCompanies() {

		companyList = new ArrayList<Company>();
		/* Tier 0 */
		Rahoi = new Company("Rahoi", 0, "\u001B[34m", 0, 0);
		companyList.add(Rahoi);
		Tower = new Company("Tower", 0, "\u001B[32m", 0, 1);
		companyList.add(Tower);

		/* Tier 1 */
		American = new Company("American", 1, "\u001B[36m", 0, 2);
		companyList.add(American);
		Worldwide = new Company("Worldwide", 1, "\u001B[35m", 0, 3);
		companyList.add(Worldwide);
		Festival = new Company("Festival", 1, "\u001B[31m", 0, 4);
		companyList.add(Festival);

		/* Tier 2 */
		Continental = new Company("Continental", 2, "\u001B[33m", 0, 5);
		companyList.add(Continental);
		Imperial = new Company("Imperial", 2, "\u001B[37m", 0, 6);
		companyList.add(Imperial);

		System.out.println("All Companies Initialized");
		return companyList;
	}

	/**
	 * Merges two or more companies together when possible. Larger companies
	 * take over smaller ones. If two companies are the same size then the
	 * player chooses which company gets to remain. TODO: If two companies are
	 * safe, these companies cannot be merged. Set the tiles between them as
	 * unplayable.
	 * 
	 * @param companies
	 * @param mTile
	 */
	public void merge(ArrayList<Company> companies, Tile mTile) {

		int i;
		Company largest = findLargest(companies);
		System.out.println("Largest Company: " + largest.getCompanyName());
		int index = companies.indexOf(largest);
		companies.remove(index);
		for (i = 0; i < companies.size(); i++) {
			Company comp = companies.get(i);
			minMaxPayout(comp);
			if(!comp.getCompanyName().equals(largest.getCompanyName())) {
				System.out.println("Merging " + comp.getCompanyName() + " into "
						+ largest.getCompanyName() + "...");
				System.out.println("Company size: " + comp.companySize);
				for (int j = 0; j < comp.companySize; j++) {
					Tile tile = comp.companyTiles.get(j);
					tile.setOwnerCompany(largest);
					largest.addTile(tile);
				}
				companiesOnBoard--;
			}
		}
		largest.addTile(mTile);
		log.debug("Doing the mergeTurn: ");
		mergeTurn(companies, largest);
		dissolveCompanies(companies);
		

	}
	
	/**
	 * Dissolves the companies that have just been merged.
	 * @param companies - Companies to be dissolved.
	 */
	private void dissolveCompanies(ArrayList<Company> companies) {
		for(int i = 0; i < companies.size(); i++) {
			Company comp = companies.get(i);
			comp.dissolve();
		}
	}

	/**
	 * Finds the largest company in the given ArrayList. If there are companies
	 * of the same size, then the player chooses which company gets to remain on
	 * the board.
	 * 
	 * @param companies - List of companies in which to find the largest.
	 * @return
	 */
	public Company findLargest(ArrayList<Company> companies) {

		// Scanner scan = new Scanner(System.in);
		Company largest = companies.get(0);
		int size = companies.get(0).companySize;
		ArrayList<Company> equal = new ArrayList<Company>();
		for (int i = 1; i < companies.size(); i++) {
			Company compare = companies.get(i);
			int sCompare = compare.companySize;
			if (sCompare > size) {
				largest = compare;
				size = sCompare;
			}
			if (sCompare == size) {
				equal.add(compare);
			}
		}
		if (equal.size() > 1) {

			String print = "";
			System.out.println("There are companies of equal size");
			System.out
					.println("Please choose which company will remain on the "
							+ "board.");
			for (int i = 0; i < equal.size(); i++) {
				print += (i + 1) + ": " + equal.get(i).getCompanyName() + "\n";
			}
			System.out.println("Which company would you prefer? : ");
			System.out.println(print);
			int sel = scan.nextInt();
			largest = equal.get(sel);
		}
		// scan.close();
		return largest;
	}
	
	/**
	 * 
	 * @param companies
	 * @param winner
	 */
	private void mergeTurn(ArrayList<Company> companies, Company winner) {
		
		
		int numTurns = 0;
		int index = playerIndex;
		while(numTurns < players.length) {
			for(int i = 0; i < companies.size(); i++) {
				Company comp = companies.get(i);
				if(sharesQuery(index, comp) > 0) {
					log.debug("Payout for player: " + players[index].getName());
					payout(comp, winner, players[index], index);
				}
				index = index % numPlayers;
				numTurns++;
			}
		}
		
	}

	/**
	 * 
	 * @param company
	 * @param index
	 */
	private void sellBackStock(Company company, int index) {
		
		//TODO: Update their total money!
		int shares = sharesQuery(index, company);
		System.out.println("This is how much stock you own: " + shares);
		System.out.println("How much stock would you like to sell back?");
		int stock = scan.nextInt();
		while(stock > shares || stock <=0) {
			System.out.println("The amount you entered is invalid!");
			System.out.println("How much stock would you like to sell back?");
			stock = scan.nextInt();
		}
		int money = company.getSharePrice(company.companySize);
		players[index].updateCash(money*shares);
		
		
	}
	
	/**
	 * Allows a player to trade back stock when a company is being merged. 
	 * @param company - The now defunct company.
	 * @param winner - The largest company.
	 * @param index - 
	 */
	private void tradeBackStock(Company company, Company winner, int index) {
		
		//TODO: Make sure people don't cheat
		int trade = 0;
		System.out.println("How much stock would you like to trade back?" +
				" (Remember for every 2 stock you trade back you receieve 1" +
				"stock of: " + winner.getCompanyName() + ")");
		trade = scan.nextInt();
		while(trade % 2 != 0) {
			System.out.println("You didn't enter an even number. Please enter"
					+ "an even number.");
			trade = scan.nextInt();
		}
		company.addStockBack(trade);
//		winner.soldStock(trade/2);
		addCertificate(winner, trade/2, index);
	}
	
	/**
	 * 
	 * @param company
	 * @param winner
	 * @param player
	 */
	private void payout(Company company, Company winner, Player player, int index) {

		int choice;

		System.out.println("What would you like to do?: \t(1) Hold stock\n "
				+ "\t(2) sell stock\n \t(3) Trade in stock");
		choice = scan.nextInt();
		switch (choice - 1) {

		case 0: // HOLD
			System.out.println("You decided to hold your stock.");
			return;
		case 1: // SELL
			System.out.println("You decided to sell your stock.");
			sellBackStock(company, index);
			return;
		case 2: // TRADE
			System.out.println("You decided to trade your stock.");
			System.out.println("For every 2 defunct stock trade cards "
					+ "of company, " + company.getCompanyName() + ", "
					+ "traded in, you get one stock from the surviving "
					+ "comany.");
			tradeBackStock(company, winner, index);
			return;

		}

	}

	/**
	 * Prints the tiles inside of the company. This is for testing purposes
	 * only.
	 * 
	 * @param comp
	 */
	public void printTiles(Company comp) {
		System.out.println(comp.companySize);
		for (int i = 0; i < comp.companySize - 1; i++) {
			Tile tile = comp.companyTiles.get(i);
			System.out.println(tile.toString() + " ");
		}
	}

	public void addCertificate(Company company, Integer amount, int playerIndex) {
		// System.out.println("LIST SIZE: " + this.playerStockList.size());
		/* if they already have stock in this company, just add quantity */
		if (playerStockList[playerIndex][company.getCID()] != 0) {
			playerStockList[playerIndex][company.getCID()] += amount;
		}
		/* else just add to list */
		else {
			playerStockList[playerIndex][company.getCID()] = amount;

		}
		company.soldStock(amount);
		System.out.println("Congratulations on your acquisition of " + amount
				+ " share(s) of " + company.getCompanyName() + "!");

	}

	public int sharesQuery(int playerIndex, Company company) {
		return playerStockList[playerIndex][company.getCID()];
	}

	public void minMaxPayout(Company company) {

		int ID = company.getCID();
		int max = 0;
		int min = 0;

		int majorityPayout = company.getMajorityPayout();
		int minorityPayout = company.getMinorityPayout();
		ArrayList<Integer> Max = new ArrayList<>();
		ArrayList<Integer> Min = new ArrayList<>();
		Player tempP;

		/*
		 * Start Majority Work
		 */

		// Traverse List and Find Max
		for (int i = 0; i < playerStockList.length; i++) {
			int thisCheck = playerStockList[ID][i];
			if (max < thisCheck)
				max = thisCheck;
		}

		// Add all Maxes to a list
		for (int i = 0; i < playerStockList.length; i++) {
			if (playerStockList[ID][i] == max) {
				Max.add(i);
			}
		}

		// I don't think it's possible for a company to NOT have at least 1
		// stock owner. If this is true, then we can delete this. i'm just
		// paranoid.

		// if (Max.size() == 0) {
		// System.out.println("No one owns any stock in "
		// + company.getCompanyName() + "; there will no payout.");
		// return;
		// }

		/* If one max, then they get the max and we move to min */
		if (Max.size() == 1) {
			tempP = players[Max.get(0)];
			System.out.println(tempP.getName()
					+ " is a majority stockholder and receives $"
					+ majorityPayout + "!");
			tempP.totalCash += majorityPayout;

			/*
			 * If more than one max, we add max and min and distribute to max
			 * players only. sorry min!
			 */
		} else if (Max.size() > 1) {
			majorityPayout /= Max.size();
			// Rounding magic
			majorityPayout = round(majorityPayout);
			// Distribute to each Majority
			for (int i = 0; i < Max.size(); i++) {
				tempP = players[Max.get(i)];
				System.out.println(tempP.getName()
						+ " is a majority stockholder and receives $"
						+ majorityPayout + "!");
				tempP.totalCash += majorityPayout;
			}
			System.out
					.println("No money will be distributed to minority shareholder(s) :(");
			return;
		}

		/*
		 * Start Minority Work
		 */

		// Determine if there are any mins
		for (int i = 0; i < playerStockList.length; i++) {
			// if this number is greater than min and it's NOT in the Max list,
			// then it's a valid min
			int thisCheck = playerStockList[ID][i];
			if (min < thisCheck && !Max.contains(i)) {
				min = thisCheck;
			}
		}
		for (int i = 0; i < playerStockList.length; i++) {
			if (playerStockList[ID][i] == min) {
				Min.add(i);
			}
		}
		// If we have no Min, then distribute minPayout to Max. If there was
		// more than one max, we would have already
		// consumed the minority, so we would never be here, so no worries!
		if (Min.size() == 0) {
			/* There is only one max if we're here */
			tempP = players[Max.get(0)];
			System.out.println("Since there is no minority stockholder for "
					+ company.getCompanyName() + ", " + tempP.getName()
					+ " will receive the minority payout of $"
					+ company.getMinorityPayout() + " as well!");
			tempP.totalCash += company.getMinorityPayout();
			return;
		}
		// We have 1 min, give them the entire getminority amount
		else if (Min.size() == 1) {
			tempP = players[Min.get(0)];
			System.out.println(tempP.getName()
					+ " is the sole minority shareholder of "
					+ company.getCompanyName() + " and will receive $"
					+ company.getMinorityPayout() + "!");
			tempP.totalCash += company.getMinorityPayout();
			return;

			// We have multiple min, give them an evenly distributed getminority
			// amount
		} else if (Min.size() > 1) {

			// Distriubute payout among all min
			minorityPayout /= Min.size();
			minorityPayout = round(minorityPayout);
			for (int i = 0; i < Max.size(); i++) {
				players[Min.get(i)].totalCash += minorityPayout;
			}
		}

	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	int round(int number) {
		number = number % 100 > 50 ? ((number / 100) * 100) + 100
				: (number / 100) * 100;
		return number;
	}

	public static int validator(String message, int low, int high) {
		int poo = 0;
		scan = new Scanner(System.in);
		int check = 50;
		while (poo == 0) {
			System.out.println(message);
			check = scan.nextInt();
			if (low <= check && check <= high) {
				poo++;
			} else {
				System.out.println("Please enter an number between " + low
						+ " and " + high + ", inclusively.");
			}
		}
		return check;
	}
}
