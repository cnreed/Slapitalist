import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Game {

	public static Logger log = Logger.getLogger(Game.class);
	private static Scanner scan;
	private static int numPlayers;
	private static int hitOver7 = 0;
	public static int tilesLeftOnBoard; // TODO: Change back!

	private static int companiesOnBoard; // tells Phase 2 that we can buy stock
	private static int companiesSafe;
	private static int companies41;
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;
	private static ArrayList<Company> companyList;
	private static ArrayList<Tile> orphanTiles;
	private static ArrayList<Company> safeList = new ArrayList<Company>();
	private static Grid board; // TODO: This board here! - Carolyn

	private static Player[] players;
	private static int playerIndex; // global who's turn is it.
	private static int[][] playerStockList; // numplayers then companyCID

	private static boolean gameInPlay = true;
	private static boolean testingGame = false;

	public Game(int x, int y, boolean testing) {
		tilesLeftOnBoard = x * y;
		log.debug("Initial Tiles on the Board: " + tilesLeftOnBoard);
		scan = new Scanner(System.in);
		board = new Grid(x, y);
		orphanTiles = new ArrayList<Tile>();
		initCompanies();

	}

	public Game(int x, int y) {

		tilesLeftOnBoard = x * y;
		log.debug("Initial Tiles on the Board: " + tilesLeftOnBoard);
		scan = new Scanner(System.in);
		orphanTiles = new ArrayList<Tile>();
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
		if (allTilesUnplayable()) {
			System.exit(0);
		}
		for (int i = 0; i < companyList.size(); i++) {
			Company comp = companyList.get(i);
			if (comp.size() == 11) {
				if (comp != null && !safeList.contains(comp)) {
					log.debug("companiesSafe: " + companiesSafe);
					companiesSafe++;
					safeList.add(comp);
				}
			}
			if (comp.size() >= 41) {
				log.debug("Companies over 41: " + companies41);
				companies41++;
			}
		}
		if (companiesSafe == 7) {
			System.out.println("All the companies are Safe!");
			int choice = validator(
					"Would you like to end the game? 1 for Yes, 0 for no", 0, 1);
			if (choice == 1) {
				payPlayers();
				determineWinner();
				System.exit(0);
			}
		}
		if (companies41 > 1) {
			System.out.println("There's atleast one company over 41");
			int choice = validator(
					"Would you like to end the game? 1 for Yes, 0 for no", 0, 1);
			if (choice == 1) {
				payPlayers();
				determineWinner();
				System.exit(0);
			}

			// Can end game
		}
	}

	private void payPlayers() {
		// TODO Auto-generated method stub

	}

	private void determineWinner() {

		ArrayList<Player> equalSize = new ArrayList<Player>();
		Player first = players[0];
		for (int i = 1; i < players.length; i++) {
			Player player = players[i];
			if (first.getCash() < player.getCash()) {
				first = player;
				if (player.getCash() > equalSize.get(0).getCash()) {
					equalSize.clear();
				}
			}
			if (first.getCash() == player.getCash()) {
				equalSize.add(player);
				equalSize.add(first);
			}
		}
		System.out.println("And the winner is...");
		if (equalSize.size() > 0) {
			for (int i = 0; i < equalSize.size(); i++) {
				System.out.println(equalSize.get(i).getName());
			}
		} else {
			System.out.println(first.getName());
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
		// Company comp = tile.getOwnerCompany();
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
		while (choice < 0) {
			System.out
					.println("I'm sorry, this tile is not playable right now, please pick a new tile");
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

		} else {
			return -1;
		}
		return choice;
	}

	private void playerTurnPartTwo(Player player, Grid board) {
		// log.debug(player.name + " "
		// + Arrays.toString(player.getPlayerStockList().toArray()));
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
						log.debug("Company: " + company.getCompanyName()
								+ " Company stock: " + company.getStockCount());
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
			ArrayList<Tile> tiles, ArrayList<Company> companies, Tile orig) {

		Tile tileTop, tileLeft, tileRight, tileBottom;
		Company top, left, right, bottom = null;

		// Check to see where we are on the board.

		tile.visited = true;
		if (tile.getTop()) {

			tileTop = board.getTile(x - 1, y);
			top = tileTop.getOwnerCompany();
			// log.debug("tileTop.visited: " + tileTop.visited);
			if (top != null) {
				if (!companies.contains(top)) {
					top.logPrintTiles();
					companies.add(top);
				}
			} else if (tileTop.getStatus().equals("ONBOARD")
					&& tileTop.visited == false) {
				tiles.add(tileTop);
				if (orphanTiles.contains(tileTop)) {
					orphanTiles.remove(tileTop);
					orphanReset(tileTop);
				}
				checkAdj(tileTop, x - 1, y, player, tiles, companies, orig);
			}
		}

		if (tile.getLeft()) {

			tileLeft = board.getTile(x, y - 1);
			left = tileLeft.getOwnerCompany();
			// log.debug("tileLeft.visited: " + tileLeft.visited);
			if (left != null) {
				if (!companies.contains(left)) {
					left.logPrintTiles();
					companies.add(left);
				}
			} else if (tileLeft.getStatus().equals("ONBOARD")
					&& tileLeft.visited == false) {
				tiles.add(tileLeft);
				if (orphanTiles.contains(tileLeft)) {
					orphanTiles.remove(tileLeft);
					orphanReset(tileLeft);
				}
				checkAdj(tileLeft, x, y - 1, player, tiles, companies, orig);
			}
		}
		if (tile.getBottom()) {
			// board.printBoundaries(tile);
			tileBottom = board.getTile(x + 1, y);
			bottom = tileBottom.getOwnerCompany();
			// log.debug("tileBottom.visited: " + tileBottom.visited);
			if (bottom != null) {
				if (!companies.contains(bottom)) {
					bottom.logPrintTiles();
					companies.add(bottom);
				}
			} else if (tileBottom.getStatus().equals("ONBOARD")
					&& tileBottom.visited == false) {
				tiles.add(tileBottom);
				if (orphanTiles.contains(tileBottom)) {
					orphanTiles.remove(tileBottom);
					orphanReset(tileBottom);
				}
				checkAdj(tileBottom, x + 1, y, player, tiles, companies, orig);

			}
		}
		if (tile.getRight()) {
			tileRight = board.getTile(x, y + 1);
			right = tileRight.getOwnerCompany();
			// log.debug("tileRight.visited: " + tileRight.visited);
			if (right != null) {
				if (!companies.contains(right)) {
					right.logPrintTiles();
					companies.add(right);
				}
			} else if (tileRight.getStatus().equals("ONBOARD")
					&& tileRight.visited == false) {
				tiles.add(tileRight);
				if (orphanTiles.contains(tileRight)) {
					orphanTiles.remove(tileRight);
					orphanReset(tileRight);
				}
				checkAdj(tileRight, x, y + 1, player, tiles, companies, orig);
			}
		}
		tile.visited = false;

		// If there are multiple companies on the board MERGE!
		if (companies.size() > 1 && tile == orig) {
			System.out.println("I'm merging with companies: ");
			for (int i = 0; i < companies.size(); i++) {
				System.out.println("Company: "
						+ companies.get(i).getCompanyName());
			}
			merge(companies, tile);
			return;
		}
		// Adds the tiles to the company if there's only one company on the
		// board.
		if (companies.size() == 1 && tile == orig) { // This seems redundant.

			Company comp = companies.get(0);
			comp.logPrintTiles();
			tile.setOwnerCompany(comp);
			comp.addTile(tile);
			tile.subStatusUpdate(5);

			if (tiles.size() > 0) {

				for (int i = 0; i < tiles.size(); i++) {
					Tile aTile = tiles.get(i);
					if (!comp.companyTiles.contains(aTile)) {
						aTile.setOwnerCompany(comp);
						comp.addTile(aTile);
						aTile.subStatusUpdate(5);
						if (comp.isSafe) {
							setUnplayable(comp);
						}
						log.debug("Company: " + comp.getCompanyName());
						log.debug("Tiles");
						comp.logPrintTiles();

					}

				}
				if (companiesOnBoard == 7) {
					checkDiagonals();
				}

			}

		}
		// Creates a new company
		else if (tiles.size() >= 1 && tile == orig) {

			if (companiesOnBoard < 7) {
				tiles.add(tile);

				Company results = selectCompany(tiles);
				System.out.println("You have selected: "
						+ results.getCompanyName());
				addCertificate(results, 1, playerIndex);

			}

		} else if (tiles.size() == 0 && tile == orig && companiesOnBoard == 7) {
			System.out.println("I got here!");
			orphanTiles.add(tile);
			isAnythingNearMe(tile);
			checkDiagonals();
			logPrintTileStatus();
		}

	}

	@SuppressWarnings("unused")
	private void setUnplayable() {
		// TODO Auto-generated method stub
		Company comp = null;
		Company otherCompare = null;
		logPrintTileStatus(board);
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);

				comp = tile.getOwnerCompany();
				if (comp != null) {
					if (comp.isSafe) {
						if (tile.getBottom()) {
							Tile guy = board.getTile(i + 1, j);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getBottom()) {
								Tile otherGuy = board.getTile(i + 2, j);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
							// else /*if() */{
							// // log.debug("Tile's company: " +
							// comp.getCompanyName() +
							// " and Compare's company: " +
							// compare.getCompanyName());
							// guy.subStatusUpdate(3);
							// }
						}
						if (tile.getTop()) {
							Tile guy = board.getTile(i - 1, j);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getTop()) {
								Tile otherGuy = board.getTile(i - 2, j);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
						if (tile.getRight()) {
							Tile guy = board.getTile(i, j + 1);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getRight()) {
								Tile otherGuy = board.getTile(i, j + 2);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
						if (tile.getLeft()) {
							Tile guy = board.getTile(i, j - 1);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getLeft()) {
								Tile otherGuy = board.getTile(i, j - 2);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
					}
				}
			}
		}

		logPrintTileStatus(board);

	}

	public void setUnplayable(Grid board) {
		// TODO Auto-generated method stub
		Company comp = null;
		Company otherCompare = null;
		logPrintTileStatus(board);
		// boolean booleanVals [] = {false, false, false, false};
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);

				comp = tile.getOwnerCompany();
				if (comp != null) {
					if (comp.isSafe) {
						if (tile.getBottom()) {
							Tile guy = board.getTile(i + 1, j);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getBottom()) {
								Tile otherGuy = board.getTile(i + 2, j);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
							// else /*if() */{
							// // log.debug("Tile's company: " +
							// comp.getCompanyName() +
							// " and Compare's company: " +
							// compare.getCompanyName());
							// guy.subStatusUpdate(3);
							// }
						}
						if (tile.getTop()) {
							Tile guy = board.getTile(i - 1, j);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getTop()) {
								Tile otherGuy = board.getTile(i - 2, j);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
						if (tile.getRight()) {
							Tile guy = board.getTile(i, j + 1);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getRight()) {
								Tile otherGuy = board.getTile(i, j + 2);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
						if (tile.getLeft()) {
							Tile guy = board.getTile(i, j - 1);
							// log.debug("Guy: " + guy.toString());
							Company compare = guy.getOwnerCompany();
							// System.out.println("Compare : " + compare);

							if (guy.getLeft()) {
								Tile otherGuy = board.getTile(i, j - 2);
								otherCompare = otherGuy.getOwnerCompany();

							}
							if (compare == null) {
								// if(compare != null && compare != comp) {
								// log.debug("Tile's company: " +
								// comp.getCompanyName() +
								// " and Compare's company: " +
								// compare.getCompanyName());
								guy.subStatusUpdate(3);
							} else if (otherCompare != null
									&& otherCompare.isSafe
									&& !otherCompare.getCompanyName().equals(
											compare.getCompanyName())) {
								guy.subStatusUpdate(4);
							}
						}
					}
				}
			}
		}

		logPrintTileStatus(board);

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

		if (companiesOnBoard == 7) {
			if (!nextToOrphanPlayable(tileInPlay)) {
				return false;
			}
		}
		if (tileInPlay.getStatus().equals("INHAND")) {
			tileInPlay.setOwnerPlayer(players[playerIndex]);
			tileInPlay.statusUpdate(2);
			tilesLeftOnBoard--;
			log.debug("tilesLeftOnBoard: " + tilesLeftOnBoard);
		}
		return true;
	}

	/**
	 * 
	 * @param tile
	 * @return
	 */
	private void isAnythingNearMe(Tile tile) {
		int x = tile.getRow();
		int y = tile.getCol();
		Tile nearMe = null;
		Tile check = null;

		if (tile.getTop()) {
			nearMe = board.getTile(x - 1, y);
			if (!nearMe.getStatus().equals("ONBOARD")) {
				if (nearMe.getTop()) {
					check = board.getTile(x - 2, y);
					if (check.getSubStatus().equals("INCOMPANY")) {
						nearMe.subStatusUpdate(0);
					} else {
						nearMe.subStatusUpdate(4);
					}
				} else {
					nearMe.subStatusUpdate(4);
				}
			}
		}

		if (tile.getRight()) {
			nearMe = board.getTile(x, y + 1);
			if (!nearMe.getStatus().equals("ONBOARD")) {
				if (nearMe.getRight()) {
					check = board.getTile(x, y + 2);
					if (check.getSubStatus().equals("INCOMPANY")) {
						nearMe.subStatusUpdate(0);
					} else {
						nearMe.subStatusUpdate(4);
					}
				} else {
					nearMe.subStatusUpdate(4);
				}
			}
		}

		if (tile.getBottom()) {
			nearMe = board.getTile(x + 1, y);
			if (!nearMe.getStatus().equals("ONBOARD")) {
				if (nearMe.getBottom()) {
					check = board.getTile(x + 2, y);
					if (check.getSubStatus().equals("INCOMPANY")) {
						nearMe.subStatusUpdate(0);
					} else {
						nearMe.subStatusUpdate(4);
					}

				} else {
					nearMe.subStatusUpdate(4);
				}
			}
		}

		if (tile.getLeft()) {
			nearMe = board.getTile(x, y - 1);
			if (!nearMe.getStatus().equals("ONBOARD")) {
				if (nearMe.getLeft()) {
					check = board.getTile(x, y - 2);
					if (check.getSubStatus().equals("INCOMPANY")) {
						nearMe.subStatusUpdate(0);
					} else {
						nearMe.subStatusUpdate(4);
					}
				} else {
					nearMe.subStatusUpdate(4);
				}
			}
		}

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
		if (companiesOnBoard == 7) {
			addOrphans();
			hitOver7++;
		}
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
			if (!comp.getCompanyName().equals(largest.getCompanyName())) {
				System.out.println("Merging " + comp.getCompanyName()
						+ " into " + largest.getCompanyName() + "...");
				System.out.println("Company size: " + comp.companySize);
				for (int j = 0; j < comp.companySize; j++) {
					Tile tile = comp.companyTiles.get(j);
					tile.setOwnerCompany(largest);
					largest.addTile(tile);
				}
				companiesOnBoard--;

			}
		}
		if (hitOver7 >= 1) {
			for (int j = 0; j < orphanTiles.size(); j++) {
				orphanReset(orphanTiles.get(j));
			}
			orphanTiles.clear();
		}
		largest.addTile(mTile);
		log.debug("Doing the mergeTurn: ");
		mergeTurn(companies, largest);
		dissolveCompanies(companies);

	}

	/**
	 * Dissolves the companies that have just been merged.
	 * 
	 * @param companies
	 *            - Companies to be dissolved.
	 */
	private void dissolveCompanies(ArrayList<Company> companies) {
		for (int i = 0; i < companies.size(); i++) {
			Company comp = companies.get(i);
			comp.dissolve();
		}
	}

	/**
	 * Finds the largest company in the given ArrayList. If there are companies
	 * of the same size, then the player chooses which company gets to remain on
	 * the board.
	 * 
	 * @param companies
	 *            - List of companies in which to find the largest.
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
				equal.clear();
			}
			if (sCompare == size) {
				equal.add(compare);
			}
		}

		if (equal.size() > 0) {
			if (!equal.contains(largest)) {
				equal.add(largest);
			}
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
			largest = equal.get(sel - 1);
			System.out.println("You selected: " + largest.getCompanyName());
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

		logPrintAllShareQueries();
		while (numTurns < players.length) {
			for (int i = 0; i < companies.size(); i++) {
				Company comp = companies.get(i);
				if (sharesQuery(index, comp) > 0) {
					log.debug("Payout for player: " + players[index].getName());
					payout(comp, winner, players[index], index);
				}
				index++;
				index = index % numPlayers;
				System.out.println("This is index: " + index);
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

		int shares = sharesQuery(index, company);
		System.out.println("This is how much stock you own: " + shares);
		System.out.println("How much stock would you like to sell back?");
		int stock = scan.nextInt();
		while (stock > shares || stock <= 0) {
			System.out.println("The amount you entered is invalid!");
			System.out.println("How much stock would you like to sell back?");
			stock = scan.nextInt();
		}
		int money = company.getSharePrice(company.companySize);
		players[index].updateCash(money * shares);

	}

	/**
	 * Allows a player to trade back stock when a company is being merged.
	 * 
	 * @param company
	 *            - The now defunct company.
	 * @param winner
	 *            - The largest company.
	 * @param index
	 *            -
	 */
	private void tradeBackStock(Company company, Company winner, int index) {

		// TODO: Make sure people don't cheat
		int trade = 0;
		System.out.println("How much stock would you like to trade back?"
				+ " (Remember for every 2 stock you trade back you receieve 1"
				+ "stock of: " + winner.getCompanyName() + ")");
		trade = scan.nextInt();
		while (trade % 2 != 0) {
			System.out.println("You didn't enter an even number. Please enter"
					+ "an even number.");
			trade = scan.nextInt();
		}
		company.addStockBack(trade);
		// winner.soldStock(trade/2);
		addCertificate(winner, trade / 2, index);
	}

	/**
	 * 
	 * @param company
	 * @param winner
	 * @param player
	 */
	private void payout(Company company, Company winner, Player player,
			int index) {

		int choice;

		System.out.println("What would you like to do?: " + player.getName()
				+ " \t(1) Hold stock\n " + "\t(2) sell stock\n \t(3) Trade in "
				+ "stock");
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
	 * Checks the diagonals of an orphan tile to see if there are in companies
	 * near it. If so set the corresponding adjacent tile to not UNPLAYABLE
	 * 
	 * @param tile
	 */
	private void checkDiagonals() {

		Tile check = null;
		int row = 0;
		int col = 0;

		for (int i = 0; i < orphanTiles.size(); i++) {

			Tile tile = orphanTiles.get(i);
			// log.debug("Tile: " + tile.toString());
			row = tile.getRow();
			col = tile.getCol();
			if (tile.getTop() && tile.getRight()) {
				check = board.getTile(row - 1, col + 1);
				if (check.getSubStatus().equals("INCOMPANY")) {
					check = board.getTile(row - 1, col);
					check.statusUpdate(0);
					check = board.getTile(row, col + 1);
					check.statusUpdate(0);
				}
			}
			if (tile.getTop() && tile.getLeft()) {
				check = board.getTile(row - 1, col - 1);
				if (check.getSubStatus().equals("INCOMPANY")) {
					check = board.getTile(row - 1, col);
					check.statusUpdate(0);
					check = board.getTile(row, col + 1);
					check.statusUpdate(0);
				}
			}
			if (tile.getBottom() && tile.getRight()) {
				check = board.getTile(row + 1, col + 1);
				if (check.getSubStatus().equals("INCOMPANY")) {
					check = board.getTile(row + 1, col);
					check.statusUpdate(1);
					check = board.getTile(row, col + 1);
					check.statusUpdate(0);
				}

			}
			if (tile.getBottom() && tile.getLeft()) {
				check = board.getTile(row + 1, col - 1);
				if (check.getSubStatus().equals("INCOMPANY")) {
					check = board.getTile(row + 1, col);
					check.statusUpdate(0);
					check = board.getTile(row, col + 1);
					check.statusUpdate(0);
				}
			}
		}
	}

	/**
	 * Gets the tiles inbetween two or more companies that are safe. A modified
	 * version of the first setUnplayable
	 * 
	 * @return
	 */
	public void setUnplayable(Grid board, Company comp) {

		int x = board.x_size;
		int y = board.y_size;
		Tile two = null;
		Tile setTile = null;
		Tile tile = null;
		Company twoComp = null;
		ArrayList<Tile> quarantined = new ArrayList<Tile>();
		ArrayList<Tile> unplayable = new ArrayList<Tile>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				tile = board.getTile(i, j);
				Company temp = tile.getOwnerCompany();
				if (comp.equals(temp)) {
					// bottom down two
					if (i + 2 < x) {
						two = board.getTile(i + 2, j);
						twoComp = two.getOwnerCompany();
						if (!twoComp.equals(temp)) {
							setTile = board.getTile(i + 1, j);
							if (twoComp.isSafe && temp.isSafe) {
								if (!unplayable.contains(setTile)) {
									unplayable.add(setTile);
								} else if (twoComp.isSafe || temp.isSafe) {
									if (!quarantined.contains(setTile)) {
										quarantined.add(setTile);
									}
								} else { // Neither of them are safe
											// Do nothing
											// For testing purposes only
								}
							}

						}
					}
					if (j + 2 > y) { // Right
						two = board.getTile(i, j + 2);
						twoComp = two.getOwnerCompany();
						if (!twoComp.equals(temp)) {
							setTile = board.getTile(i, j + 1);
							if (twoComp.isSafe && temp.isSafe) {
								if (!unplayable.contains(setTile)) {
									unplayable.add(setTile);
								} else if (twoComp.isSafe || temp.isSafe) {
									if (!quarantined.contains(setTile)) {
										quarantined.add(setTile);
									}
								} else { // Neither of them are safe
									// Do nothing
									// For testing purposes only
								}
							}

						}
						if (i - 2 >= 0) {
							two = board.getTile(i - 2, j);
							twoComp = two.getOwnerCompany();
							if (!twoComp.equals(temp)) {
								setTile = board.getTile(i - 1, j);
								if (twoComp.isSafe && temp.isSafe) {
									if (!unplayable.contains(setTile)) {
										unplayable.add(setTile);
									} else if (twoComp.isSafe || temp.isSafe) {
										if (!quarantined.contains(setTile)) {
											quarantined.add(setTile);
										}
									} else { // Neither of them are safe
										// Do nothing
										// For testing purposes only
									}
								}

							}

						}
						if (j - 2 >= 0) {
							two = board.getTile(i, j - 2);
							twoComp = two.getOwnerCompany();
							if (!twoComp.equals(temp)) {
								setTile = board.getTile(i, j - 1);
								if (twoComp.isSafe && temp.isSafe) {
									if (!unplayable.contains(setTile)) {
										unplayable.add(setTile);
									} else if (twoComp.isSafe || temp.isSafe) {
										if (!quarantined.contains(setTile)) {
											quarantined.add(setTile);
										}
									} else { // Neither of them are safe
										// Do nothing
										// For testing purposes only
									}
								}

							}

						}

					}

				} // if (comp.equals(temp))

			}// inner for loop
		} // outer for loop

		// Quarantine go through
		log.debug("Quarantine: ");
		String message = "";
		for (int i = 0; i < quarantined.size(); i++) {
			quarantined.get(i).subStatusUpdate(3);
			message += quarantined.get(i).toString();
			log.debug(message + " ");
		}

		// Unplayable go through
		log.debug("Unplayable: ");
		for (int i = 0; i < unplayable.size(); i++) {
			unplayable.get(i).statusUpdate(4);
			unplayable.get(i).subStatusUpdate(4);
			message += unplayable.get(i).toString();
			log.debug(message);
		}

	}

	/**
	 * This is the production version of setUnplayable. It checks to see if
	 * there is a tile inbetween the Company comp and another company. If so
	 * then add the tile to the right arraylist.
	 * 
	 * @param comp
	 */
	private void setUnplayable(Company comp) {

		int x = board.x_size;
		int y = board.y_size;
		Tile two = null;
		Tile setTile = null;
		Tile tile = null;
		Company twoComp = null;
		ArrayList<Tile> quarantined = new ArrayList<Tile>();
		ArrayList<Tile> unplayable = new ArrayList<Tile>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				tile = board.getTile(i, j);
				Company temp = tile.getOwnerCompany();
				if (comp.equals(temp)) {
					// bottom down two
					if (i + 2 < x) {
						two = board.getTile(i + 2, j);
						twoComp = two.getOwnerCompany();
						if (!twoComp.equals(temp)) {
							setTile = board.getTile(i + 1, j);
							if (twoComp.isSafe && temp.isSafe) {
								if (!unplayable.contains(setTile)) {
									unplayable.add(setTile);
								} else if (twoComp.isSafe || temp.isSafe) {
									if (!quarantined.contains(setTile)) {
										quarantined.add(setTile);
									}
								} else { // Neither of them are safe
											// Do nothing
											// For testing purposes only
								}
							}

						}
					}
					if (j + 2 > y) { // Right
						two = board.getTile(i, j + 2);
						twoComp = two.getOwnerCompany();
						if (!twoComp.equals(temp)) {
							setTile = board.getTile(i, j + 1);
							if (twoComp.isSafe && temp.isSafe) {
								if (!unplayable.contains(setTile)) {
									unplayable.add(setTile);
								} else if (twoComp.isSafe || temp.isSafe) {
									if (!quarantined.contains(setTile)) {
										quarantined.add(setTile);
									}
								} else { // Neither of them are safe
									// Do nothing
									// For testing purposes only
								}
							}

						}
						if (i - 2 >= 0) {
							two = board.getTile(i - 2, j);
							twoComp = two.getOwnerCompany();
							if (!twoComp.equals(temp)) {
								setTile = board.getTile(i - 1, j);
								if (twoComp.isSafe && temp.isSafe) {
									if (!unplayable.contains(setTile)) {
										unplayable.add(setTile);
									} else if (twoComp.isSafe || temp.isSafe) {
										if (!quarantined.contains(setTile)) {
											quarantined.add(setTile);
										}
									} else { // Neither of them are safe
										// Do nothing
										// For testing purposes only
									}
								}

							}

						}
						if (j - 2 >= 0) {
							two = board.getTile(i, j - 2);
							twoComp = two.getOwnerCompany();
							if (!twoComp.equals(temp)) {
								setTile = board.getTile(i, j - 1);
								if (twoComp.isSafe && temp.isSafe) {
									if (!unplayable.contains(setTile)) {
										unplayable.add(setTile);
									} else if (twoComp.isSafe || temp.isSafe) {
										if (!quarantined.contains(setTile)) {
											quarantined.add(setTile);
										}
									} else { // Neither of them are safe
										// Do nothing
										// For testing purposes only
									}
								}

							}

						}

					}

				} // if (comp.equals(temp))

			}// inner for loop
		} // outer for loop

		// Quarantine go through
		log.debug("Quarantine: ");
		String message = "";
		for (int i = 0; i < quarantined.size(); i++) {
			quarantined.get(i).subStatusUpdate(3);
			message += quarantined.get(i).toString();
			log.debug(message + " ");
		}

		// Unplayable go through
		log.debug("Unplayable: ");
		for (int i = 0; i < unplayable.size(); i++) {
			unplayable.get(i).statusUpdate(4);
			unplayable.get(i).subStatusUpdate(4);
			message += unplayable.get(i).toString();
			log.debug(message);
		}

	}

	private boolean nextToOrphanPlayable(Tile tile) {

		if (!tile.getSubStatus().equals("UNPLAYABLE")) {
			return true;
		} else {
			return false;
		}

	}

	private void addOrphans() {

		logPrintTileStatus();
		for (int x = 0; x < board.x_size; x++) {
			for (int y = 0; y < board.y_size; y++) {
				Tile tile = board.getTile(x, y);
				if (tile.getStatus().equals("ONBOARD")
						&& !tile.getSubStatus().equals("INCOMPANY")) {
					isAnythingNearMe(tile);
					orphanTiles.add(tile);
				}
			}
		}

		String list = "";
		for (int i = 0; i < orphanTiles.size(); i++) {
			list += orphanTiles.get(i).toString() + " ";
		}
		log.debug("orphanTiles\n\t\t\t\t" + list);
		logPrintTileStatus();
		// logPrintCompanyTiles();
		checkDiagonals();
		logPrintTileStatus();
	}

	/**
	 * resets tile statuses from unplayable to INBAG
	 * 
	 * @param tile
	 */
	private void orphanReset(Tile tile) {

		int row = tile.getRow();
		int col = tile.getCol();
		Tile reset = null;
		if (tile.getTop()) {
			reset = board.getTile(row - 1, col);
			if (reset.getOwnerCompany() == null) {
				reset.statusUpdate(0);
			}
		}
		if (tile.getRight()) {
			reset = board.getTile(row, col + 1);
			if (reset.getOwnerCompany() == null) {
				reset.statusUpdate(0);
			}
		}
		if (tile.getBottom()) {
			reset = board.getTile(row + 1, col);
			if (reset.getOwnerCompany() == null) {
				reset.statusUpdate(0);
			}
		}
		if (tile.getLeft()) {
			reset = board.getTile(row, col - 1);
			if (reset.getOwnerCompany() == null) {
				reset.statusUpdate(0);
			}
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

	/**
	 * Internal version of logPrintTileStatus()
	 */
	private void logPrintTileStatus() {
		String message = "\n";
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);
				message += tile.toString() + " " + tile.getSubStatus()
						+ "\t\t\t\t";
			}
			message += "\n";
		}
		log.debug(message);
	}

	/**
	 * Logs all the subStatuses of the tiles on the board TODO: Rename this
	 * function so it makes more sense
	 * 
	 * @param board
	 */
	public void logPrintTileStatus(Grid board) {
		String message = "\n";
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);
				message += tile.toString() + " " + tile.getSubStatus()
						+ "\t\t\t\t";
			}
			message += "\n";
		}
		log.debug(message);
	}

	/**
	 * Adds a stock certificate to the player's 2D array of stock
	 * 
	 * @param company
	 * @param amount
	 * @param playerIndex
	 */
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

	/**
	 * Determines the min max payout to a player
	 * 
	 * @param company
	 */
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
			int thisCheck = playerStockList[i][ID];
			if (max < thisCheck)
				max = thisCheck;
		}

		// Add all Maxes to a list
		for (int i = 0; i < playerStockList.length; i++) {
			if (playerStockList[i][ID] == max) {
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
			int thisCheck = playerStockList[i][ID];
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
	 * Production version of allTilesUnplayable. If all tiles on the board are
	 * unplayable then the game should end.
	 * 
	 * @return
	 */
	private boolean allTilesUnplayable() {
		int count = 0;
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);
				if (tile.getSubStatus().equals("UNPLAYABLE")) {
					count++;
				}
			}
		}
		if (count == tilesLeftOnBoard) {
			return true;
		}
		return false;
	}

	/**
	 * Testing version of allTilesUnplayable. This function is used for
	 * CompanyTesting.java
	 * 
	 * @param board
	 * @param tilesLeftOnBoard
	 * @return
	 */
	public boolean allTilesUnplayable(Grid board, int tilesLeftOnBoard) {
		int count = 0;
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);
				if (tile.getSubStatus().equals("UNPLAYABLE")) {
					count++;
				}
			}
		}
		if (count == tilesLeftOnBoard) {
			return true;
		}
		return false;
	}

	/**
	 * Logs all of the shares that a player currently owns
	 */
	private void logPrintAllShareQueries() {
		String message = "\n";
		for (int i = 0; i < playerStockList.length; i++) {
			message += players[i].getName() + "\t\t\t [";
			for (int j = 0; j < playerStockList[i].length; j++) {
				message += " " + playerStockList[i][j];
			}
			message += "]\n";
		}
		log.debug(message);
	}

	/**
	 * Prints the tiles in the company.
	 */
	@SuppressWarnings("unused")
	private void logPrintCompanyTiles() {
		for (int i = 0; i < 7; i++) {
			companyList.get(i).logPrintTiles();
			System.out.println("Hello World!");
		}
	}

	/**
	 * Rounds the number so we cannot deal in....blaaahhhhh I didn't write this.
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
