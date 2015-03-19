import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

	private static Scanner scan;
	private static int numPlayers;
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;
	private static ArrayList<Company> companyList;
	private static Grid board; // TODO: This board here! - Carolyn
	private static int x, y;

	private static Player[] players;
	private static int whosTurn;
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
		int firstPlayer = whoIsFirst(board);
		whosTurn = firstPlayer;
		System.out.println(players[firstPlayer].getName() + " goes first!");

		/* Rearrange turn sequence so first player is first in array */
		players = rearrangePlayerSequence(firstPlayer);

		/* draw tiles for each player */
		drawStartingTiles(board);

		/* initialize and build payCliff for companies */
		companyList = initCompanies();

		if (testingGame) {
			testingMerge();
			System.exit(0);

		}
		while (gameInPlay) {

			getMove(whosTurn, board);
			whosTurn++;
			whosTurn = whosTurn % numPlayers;
		}

		scan.close();
	}

	private void testingMerge() {

		Tile tile = board.getTile(0, 0);
		Tile tile2 = board.getTile(1, 0);
		tile.statusUpdate(2);
		tile2.statusUpdate(2);
		tile.setOwnerPlayer(players[0]);
		tile2.setOwnerPlayer(players[0]);
		selectCompany(tile, tile2);
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
		selectCompany(tile3, tile4);
		Company comp1 = tile3.getOwnerCompany();

		board.print();

		Tile tile5 = board.getTile(0, 1);
		tile5.statusUpdate(2);
		merge(comp, comp1, tile5);
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
	private boolean getMove(int playerIndex, Grid board) {
		Player player = players[playerIndex];
		int choice = playerTurnPartOne(player, board);

		for (Company co : companyList) {
			if (co.onBoard) {
				playerTurnPartTwo(player, board);
			}
			continue;
		}
		return playerTurnPartThree(player, board, choice);

	}

	private int playerTurnPartOne(Player player, Grid board) {
		board.print();
		System.out.println("Player " + player.getName());
		System.out.println("\nWhat tile would you like to place?");
		player.showHand();

		int choice = scan.nextInt() - 1;

		boolean playable = playTile(player.hand[choice]);
		System.out.println("Playing " + player.hand[choice].toString());

		if (playable) {
			System.out.println(player.getName()
					+ " successfully placed down tile "
					+ player.hand[choice].toString());
			checkAdjaceny(player.hand[choice], player.hand[choice].getRow(),
					player.hand[choice].getCol());

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
						System.out.println(displayCount + ". "
								+ company.getCompanyName() + " @ $"
								+ company.getSharePrice(company.companySize)
								+ " with " + company.getStockCount()
								+ " shares remaining. You currently own "
								+ player.sharesQuery(company) + " shares.");
						displayCount++;
					}
				}
				// player chooses stock to buy
				int stockBuyChoice = scan.nextInt() - 1;
				// how many stock do you want in this company?
				Company companyToBuy = tempBuyList.get(stockBuyChoice);
				int companyStockPrice = companyToBuy
						.getSharePrice(companyToBuy.companySize);
				System.out.println("How many shares of stock in "
						+ companyToBuy.getCompanyName()
						+ " would you like to purchase?");
				System.out.println("Available shares for purchase: "
						+ (3 - stockTotal));
				// get amount
				int selectionCount = scan.nextInt() - 1;
				// if player can afford the value wanted and it's below the
				// maximum purchase count
				if (selectionCount > -1
						&& (player.money >= (companyStockPrice * selectionCount))) {

					// first take their money.
					player.money -= (companyStockPrice * selectionCount);
					System.out.println("Money subtracted!");

					// add stock to player's stocklist,
					// remove amount from comapny's stock list
					StockCertificate newStock = new StockCertificate(
							companyToBuy, selectionCount + 1, player);

					// rebalance majority/minority
					// TODO: figure out a better rebalancing of
					// majority/minority and tie-cases
					stockTotal += selectionCount + 1;
					System.err.println("StockTotal: " + stockTotal);

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
	 * Possibly a test method?
	 * 
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void checkAdjaceny(Tile tile, int x, int y) {
		Tile compare;
		String results;
		board.checkBoundaries(tile, x, y);
		if (tile.getTop()) {
			compare = board.getTile(x - 1, y);
			if (compare.getStatus().equals("ONBOARD")) {
				if (compare.getOwnerCompany() == null) {
					results = selectCompany(tile, compare);
					System.out.println(results);
				} else {
					Company com = compare.getOwnerCompany();
					com.addTile(tile);
					return;
				}
			}
		}
		if (tile.getLeft()) {
			compare = board.getTile(x, y - 1);
			if (compare.getStatus().equals("ONBOARD")) {
				if (compare.getOwnerCompany() == null) {
					results = selectCompany(tile, compare);
					System.out.println(results);
				} else {
					Company com = compare.getOwnerCompany();
					com.addTile(tile);
					return;
				}
			}
		}
		if (tile.getBottom()) {
			compare = board.getTile(x + 1, y);
			if (compare.getStatus().equals("ONBOARD")) {
				if (compare.getOwnerCompany() == null) {
					results = selectCompany(tile, compare);
					System.out.println(results);
				} else {
					Company com = compare.getOwnerCompany();
					com.addTile(tile);
					return;
				}
			}
		}
		if (tile.getRight()) {
			compare = board.getTile(x, y + 1);
			if (compare.getStatus().equals("ONBOARD")) {
				if (compare.getOwnerCompany() == null) {
					results = selectCompany(tile, compare);
					System.out.println(results);
				} else {
					Company com = compare.getOwnerCompany();
					com.addTile(tile);
					return;
				}
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
			tileInPlay.setOwnerPlayer(players[whosTurn]);
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
	 * Prints the companies and their tiers. TODO: List which companies are on
	 * the board and which ones are not on the board.
	 */
	private void companyListing() {

		System.out.println("Tier 1 Hotel Chains:\n\t Rahoi, Tower");
		System.out.println("Tier 2 Hotel Chains:\n\t American, Worldwide, "
				+ "Festival");
		System.out.println("Tier 3 Hotel Chains:\n\t Continental, Imperial");

	}

	/**
	 * TODO: To be written
	 * 
	 * @param i
	 * @return
	 */
	public String selectCompany(Tile tile, Tile tile2) {

		System.out
				.println("Would you like to list the companies and their tiers?"
						+ "1 for Yes, 0 for No");
		int answer = scan.nextInt();
		if (answer == 1) {
			companyListing();
		}

		System.out.println(" 1: Rahoi, 2: Tower, 3: American, 4: Worldwide,"
				+ "5: Festival, 6: Continental, 7: Imperial");
		int index = scan.nextInt();
		switch (index - 1) {
		case 0:
			setCompany(Rahoi, tile, tile2);
			return "You have selected Rahoi";
		case 1:
			setCompany(Tower, tile, tile2);
			return "You have selected Tower";
		case 2:
			setCompany(American, tile, tile2);
			return "You have selected American";
		case 3:
			setCompany(Worldwide, tile, tile2);
			return "You have selected Worldwide";
		case 4:
			setCompany(Festival, tile, tile2);
			return "You have selected Festival";
		case 5:
			setCompany(Continental, tile, tile2);
			return "You have selected Continental";
		case 6:
			setCompany(Imperial, tile, tile2);
			return "You have selected Imperial";
		}

		return null;

	}

	public void setCompany(Company company, Tile tile, Tile tile2) {
		// System.out.println(company.getCommpanyName());
		// System.out.println(tile.toString());
		// System.out.println(tile2.toString());
		company.addTile(tile);
		company.addTile(tile2);
		company.increment_size();
		company.increment_size();
		company.setOnboard();
		tile.setOwnerCompany(company);
		tile2.setOwnerCompany(company);

		// System.out.println("The company size: "+ );

	}

	/**
	 * Initializes all the companies in the game.
	 */
	static ArrayList<Company> initCompanies() {

		ArrayList<Company> companyList = new ArrayList<Company>();
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

	public static void merge(Company comp1, Company comp2, Tile mTile) {

		System.out.println("company 2 companySize: " + comp2.companySize);
		for (int i = 0; i < comp2.companySize; i++) {
			Tile tile = comp2.companyTiles.get(i);
			tile.setOwnerCompany(comp1);
			comp1.addTile(tile);
		}
		comp1.addTile(mTile);
		comp2 = null;

	}

	public void printTiles(Company comp) {
		System.out.println(comp.companySize);
		for (int i = 0; i < comp.companySize - 1; i++) {
			Tile tile = comp.companyTiles.get(i);
			System.out.println(tile.toString() + " ");
		}
	}

}
