import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

	private static Scanner scan;
	private static int numPlayers;

	private static int companiesOnBoard; // tells Phase 2 that we can buy stock
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;
	private static ArrayList<Company> companyList;
	private static Grid board; // TODO: This board here! - Carolyn
	private static int x, y;

	private static Player[] players;
	private static int playerIndex; // global who's turn is it.
	private static boolean companyOnBoard = false; /*
													 * if we can buy stock at
													 * all
													 */
	
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
		
		if(testingGame) {
			System.out.println("TESTING MERGE!");
		companyList = initCompanies();

			testingMerge();
			System.exit(0);

		}
		while (gameInPlay) {
			Player player = players[playerIndex];
			getMove(player, board);
			playerIndex++;
			playerIndex = playerIndex % numPlayers;
		}

		scan.close();
	}

	private void testingMerge() {
		Player player = players[playerIndex];
		Tile tile = board.getTile(0, 0);
		Tile tile2 = board.getTile(1, 0);
		tile.statusUpdate(2);
		tile2.statusUpdate(2);
		tile.setOwnerPlayer(players[0]);
		tile2.setOwnerPlayer(players[0]);
//		selectCompany(tile, tile2);
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
//		selectCompany(tile3, tile4);
		Company comp1 = tile3.getOwnerCompany();

		board.print();

		Tile tile5 = board.getTile(0, 1);
		tile5.statusUpdate(2);
//		merge(comp, comp1, tile5);
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
		board.print();
		System.out.println("\nPlayer " + player.getName());
		System.out.println("\nWhich tile would you like to place?");
		player.showHand();

		int choice = scan.nextInt() - 1;

		boolean playable = playTile(player.hand[choice]);
		System.out.println("Playing " + player.hand[choice].toString());

		if (playable) {
			System.out.println(player.getName()
					+ " successfully placed down tile "

					+ player.hand[choice].toString() + ".");
			checkAdj(player.hand[choice], player.hand[choice].getRow(),
					player.hand[choice].getCol(), player);

		}
		return choice;
	}

	private void playerTurnPartTwo(Player player, Grid board) {
		System.out
				.println("Would you like to buy stock in an active company?\n (1) Yes (2) No");
		int buyYesOrNo = scan.nextInt();
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
								+ player.sharesQuery(company) + " shares.");
						displayCount++;
					}
				}
				// player chooses stock to buy
				Company companyToBuy = tempBuyList.get(scan.nextInt() - 1);

				int companyStockPrice = companyToBuy
						.getSharePrice(companyToBuy.companySize);

				// how many stock do you want in this company?
				System.out.println("How many shares of stock in "
						+ companyToBuy.getCompanyName()
						+ " would you like to purchase? \n (current limit: "
						+ (3 - stockTotal) + ", current balance: "
						+ player.totalCash + ")");

				// get amount
				int selectionCount = scan.nextInt();
				// if player can afford the value wanted and it's below the
				// maximum purchase count
				if (selectionCount > 0
						&& (player.totalCash >= (companyStockPrice * selectionCount))) {
					player.totalCash -= (companyStockPrice * selectionCount);
					System.out.println("New cash balance: " + player.totalCash);

					// add stock to player's stocklist,
					// remove amount from comapny's stock list
					StockCertificate newStock = new StockCertificate(
							companyToBuy, selectionCount, player);

					// rebalance majority/minority
					// TODO: figure out a better rebalancing of
					// majority/minority and tie-cases
					stockTotal += selectionCount;

				} else if (player.totalCash == 0) {
					break;
				}
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
	 * @param tile - The tile that was just placed on the board.
	 * @param x - the x coordinate of the tile.
	 * @param y - the y coordinate of the tile.
	 * @param player - The player who placed the tile. 
	 */
	private void checkAdj(Tile tile, int x, int y, Player player) {
		
		Tile tileTop, tileLeft, tileRight, tileBottom;
		Company top, left, right, bottom;
		int companyCount = 0;
		int onBoardCount = 0;
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		ArrayList<Company> companies = new ArrayList<Company>();
	
		//Check to see where we are on the board.
		board.checkBoundaries(tile, x, y);
		
		if(tile.getTop()) {
			tileTop = board.getTile(x-1, y);
			top = tileTop.getOwnerCompany();
			if(top != null) {
				companyCount++;
				companies.add(top);
			}
			else if(tileTop.getStatus().equals("ONBOARD")) {
				onBoardCount++;
				tiles.add(tileTop);
			}
		}
		if(tile.getLeft()) {
			tileLeft = board.getTile(x, y-1);
			left = tileLeft.getOwnerCompany();
			if(left != null) {
				if(!companies.contains(left)) {
					companyCount++;
					companies.add(left);
				}
			} 
			else if(tileLeft.getStatus().equals("ONBOARD")) {
				onBoardCount++;
				tiles.add(tileLeft);
			}
		}
		if(tile.getBottom()) {
			tileBottom = board.getTile(x+1, y);
			bottom = tileBottom.getOwnerCompany();
			if(bottom != null) {
				
				if(!companies.contains(bottom)) {
					companyCount++;
					companies.add(bottom);
				}
			} 
			else if(tileBottom.getStatus().equals("ONBOARD")) {
				onBoardCount++;
				tiles.add(tileBottom);
			}
		}
		if(tile.getRight()) {
			tileRight = board.getTile(x, y+1);
			right = tileRight.getOwnerCompany();
			if(right != null) {
				
				if(!companies.contains(right)) {
					companyCount++;
					companies.add(right);
				}
			} 
			else if(tileRight.getStatus().equals("ONBOARD")) {
				onBoardCount++;
				tiles.add(tileRight);
			}
		}
//		System.out.println("companyCount is: " + companyCount);
//		System.out.println("onBoardCount is: " + onBoardCount);
		
		//If there are multiple companies on the board MERGE!
		if(companyCount > 1) {
			merge(companies, tile);
			return;
		}
		//Adds the tiles to the company if there's only one company on the
		//board.
		else if(companyCount == 1) { //This seems redundant. 
			Company comp = companies.get(0);
			tile.setOwnerCompany(comp);
			comp.addTile(tile);
			
			if(tiles.size() > 0) {
				for(int i = 0; i < tiles.size(); i++) {
					Tile aTile = tiles.get(i);
					if(!comp.companyTiles.contains(aTile)) {
						aTile.setOwnerCompany(comp);
						comp.addTile(aTile);
					}
				}
			}
			return;
		}
		//Creates a new company
		if(onBoardCount >= 1 && onBoardCount < 5) {
			if(companiesOnBoard < 7) {
				tiles.add(tile);
				Company results = selectCompany(tiles);
				System.out.println("You have selected: " + results.getCompanyName());
				new StockCertificate(results, 1, player);
			}
			else {
				System.out.println("All companies are on the board. You cannot "
						+ "place this tile. Please select another tile.");
				tile.statusUpdate(3);
			}
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

		if (tileInPlay.getStatus().equals("INHAND")) {
			tileInPlay.setOwnerPlayer(players[playerIndex]);
			tileInPlay.statusUpdate(2);
		}
		return true;
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

		boolean gate = false;
		while (!gate) {
			System.out.print("Enter the number of Players (Max 6): ");
			numPlayers = scan.nextInt();
			if (numPlayers > 1 && numPlayers < 7) {
				gate = true;
			}
		}

		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Name of player " + (i + 1) + ": ");
			String name = scan.next();
			players[i] = new Player(name, board);
		}

		return players;
	}

	/**
	 * A helper method that prints the companies that have not been selected
	 * and their tiers.
	 */
	private void companyListing() {

		String print = "Tier 1 Hotel Chains: \n\t\t";
//		System.out.println("Tier 1 Hotel Chains:\n\t ");
		if(!Rahoi.onBoard) {
			print += "Rahoi ";
		}
		if(!Tower.onBoard) {
			print += "Tower ";
		}
		print += "\nTier 2 Hotel Chains: \n\t\t ";
		if(!American.onBoard) {
			print += "American ";
		}
		if(!Worldwide.onBoard) {
			print += "WorldWide ";
		}
		if(!Festival.onBoard) {
			print += "Festival ";
		}
		print += "\nTier 3 Hotel Chains: \n\t\t";
		if(!Continental.onBoard) {
			print += "Continental ";
		}
		if(!Imperial.onBoard) {
			print += "Imperial ";
		}
		System.out.println(print);

	}
	
	/**
	 * A helper method that prints only the remaining companies that are not on
	 * the board. This does not print the tiers.
	 * @return
	 */
	private String companiesRemaining() {
		String print = "";
		if(!Rahoi.onBoard) print += "1: Rahoi\n";
		if(!Tower.onBoard) print += "2: Tower\n";
		if(!American.onBoard) print += "3: American\n";
		if(!Worldwide.onBoard) print += "4: Worldwide\n";
		if(!Festival.onBoard) print += "5: Festival\n";
		if(!Continental.onBoard) print += "6: Continental\n";
		if(!Imperial.onBoard) print += "7: Imperial";
		return print;
		
	}

	/**
	 * The player gets to choose which company they want to found.
	 * 
	 * @param i
	 * @return
	 */
	public Company selectCompany(ArrayList<Tile>tiles) {

//		Scanner scan = new Scanner(System.in);
		System.out.println("You have an opportunity to create a company!\n"
				+ "Would you like to list the companies and their tiers?"
				+ "\n (1) Yes (2) No");
		int answer = scan.nextInt();
		if (answer == 1) {
			companyListing();
		}
		String results = companiesRemaining();
		System.out.println(results);
		int index = scan.nextInt();
		while(companyList.get(index-1).onBoard) {
			System.out.println("That company is already on the board. Please "
					+ "select another company: ");
			index = scan.nextInt();
		}
//		scan.close();
		if(companiesOnBoard == 7) {
			System.out.println("I'm sorry, all companies are on board. ");
			return null;
		}
		switch (index-1) {

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
	 * @param company - The company to be set on the board.
	 * @param tiles - The tiles that are going to be put into the company
	 */
	public void setCompany(Company company, ArrayList<Tile>tiles) {

//		company.insertShareHolder(tiles.get(0).getOwnerPlayer().getName(), 1);
		company.setOnboard();
		for(int i = 0; i < tiles.size(); i++) {
			Tile tile = tiles.get(i);
			company.addTile(tile);
			tile.setOwnerCompany(company);			
		}
		companiesOnBoard++; // increment companies on board
		System.out.println("The company size: "+ company.companySize);
		
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
	 * player chooses which company gets to remain.
	 * TODO: If two companies are safe, these companies cannot be merged. Set
	 * the tiles between them as unplayable.
	 * @param companies
	 * @param mTile
	 */
	public void merge(ArrayList<Company> companies, Tile mTile) {
		
		Company largest = findLargest(companies);
		System.out.println("Largest Company: " + largest.getCompanyName());
		int index = companies.indexOf(largest);
		companies.remove(index);
		for(int i = 0; i < companies.size(); i++) {
			Company comp = companies.get(i);
			System.out.println("Merging " + comp.getCompanyName() + 
					" into " + largest.getCompanyName() + "...");
			System.out.println("Company size: " + comp.companySize);
			for(int j = 0; j < comp.companySize; j++) {
				Tile tile = comp.companyTiles.get(j);
				tile.setOwnerCompany(largest);
				largest.addTile(tile);		
			}
			companiesOnBoard--;
		}
		largest.addTile(mTile);

	}
	
	/**
	 * Finds the largest company in the given ArrayList. If there are companies
	 * of the same size, then the player chooses which company gets to remain
	 * on the board.
	 * @param companies
	 * @return
	 */
	public Company findLargest(ArrayList<Company> companies) {
		
//		Scanner scan = new Scanner(System.in);
		Company largest = companies.get(0);
		int size = companies.get(0).companySize;
		ArrayList<Company>equal = new ArrayList<Company>();
		equal.addAll(equal);
		for(int i = 1; i < companies.size(); i++) {
			Company compare = companies.get(i);
			int sCompare = compare.companySize;
			if(sCompare > size) {
				largest = compare;
				size = sCompare;
			}
			if(sCompare == size) {
				equal.add(compare);
			}
		}
		if(equal.size() > 1) {
			
			String print = "";
			System.out.println("There are companies of equal size");
			System.out.println("Please choose which company will remain on the "
					+ "board.");
			for(int i = 0; i < equal.size(); i++) {
				print += (i+1) + ": " + equal.get(i).getCompanyName() + "\n";
			}
			System.out.println("Which company would you prefer? : ");
			System.out.println(print);
			int sel = scan.nextInt();
			largest = equal.get(sel);
		}
//		scan.close();
		return largest;
	}

	/**
	 * Prints the tiles inside of the company. This is for testing purposes
	 * only.
	 * @param comp
	 */
	public void printTiles(Company comp) {
		System.out.println(comp.companySize);
		for (int i = 0; i < comp.companySize - 1; i++) {
			Tile tile = comp.companyTiles.get(i);
			System.out.println(tile.toString() + " ");
		}
	}

}
