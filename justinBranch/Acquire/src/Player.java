import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Player {

	public static Logger log = Logger.getLogger(Player.class);
	int totalCash;
	String name;

	int pid; // game session unique id
	int handSize = 6;
	Tile[] hand = new Tile[handSize];
	int companies_started;
	int companies_merged;
	int number_of_turns;
	static int numHand = 0; // The number of Tiles in the hand.
	private static ArrayList<Integer> playerStockList;

	boolean my_turn;
	boolean my_merge_turn;

	public Player(String name, Board board) {
		this.name = name;
		this.totalCash = 6000;
		this.hand = new Tile[handSize];
		this.numHand = 0;
		playerStockList = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			playerStockList.add(i, 0);
		}
		// System.out.println("LIST SIZE: " + playerStockList.size());
	}

	public void addHand(Tile tile) {
		if (numHand >= 6) {
			System.out.println("Cheating is prohibited");
			return;
		}
		tile.statusUpdate(1);
		hand[numHand] = tile;
		this.numHand++;
	}

	public void showHand() {
		for (int i = 0; i < hand.length; i++) {
			System.out.println((i + 1) + ". " + (char) (hand[i].row + 65) + ""
					+ (hand[i].col + 1));
		}
	}

	/**
	 * Places a new tile in the location of an old tile.
	 * 
	 * @param loc
	 *            - Location in the hand
	 * @param tile
	 *            - Tile to add to the hand
	 */
	public void updateHand(int loc, Tile tile) {
		hand[loc] = tile;
		numHand++;
	}

	public int getCash() {

		return totalCash;
	}
	
	public void updateCash(int money) {
		totalCash += money;
	}

	public String getName() {
		return name;
	}

	public String printTile(Tile tile) {
		return tile.row + "" + tile.col;
	}

	/**
	 * Do something with the board.
	 * 
	 * @param loc
	 *            - the location in the hand.
	 */
	public int placeTile(int loc) {
		Tile tile = hand[loc];
		tile.statusUpdate(2);
		hand[loc] = null;
		numHand--;
		return loc;
		// TODO: Might not have to return the location.
	}

	public void printHand() {
		System.out.print("Your hand: ");
		for (int i = 0; i < hand.length; i++) {
			if (!hand[i].getSubStatus().equals("UNPLAYABLE")
					|| !(hand[i].getStatus().equals("UNPLAYABLE"))) {
				hand[i].toString();
			}
		}
		System.out.println();
		System.out.print("Unplayable tiles in your hand: ");
		for (int i = 0; i < hand.length; i++) {
			if (hand[i].getSubStatus().equals("UNPLAYABLE")) {
				hand[i].toString();
			}
		}
	}


	/**
	 * @return the playerStockList
	 */
	public static ArrayList<Integer> getPlayerStockList() {
		return playerStockList;
	}

	/**
	 * @param playerStockList
	 *            the playerStockList to set
	 */
	public static void setPlayerStockList(ArrayList<Integer> playerStockList) {
		Player.playerStockList = playerStockList;
	}

	// public void addCertificate(Company company, Integer amount, int
	// playerIndex) {
	// System.out.println("LIST SIZE: " + this.playerStockList.size());
	// /* if they already have stock in this company, just add quantity */
	// if (this.playerStockList.get(company.CID) != 0) {
	// this.playerStockList.set(company.CID,
	// this.playerStockList.get(company.CID) + amount);
	// }
	// /* else just add to list */
	// else {
	// this.playerStockList.set(company.CID, amount);
	// }
	// System.out.println("Congratulations on your acquisition of " + amount
	// + " share(s) of " + company.getCompanyName() + "!");
	//
	// }
	//
	// public int sharesQuery(Company company) {
	//
	// if (this.playerStockList.get(company.CID) != null)
	// return this.playerStockList.get(company.CID);
	//
	// return 0;
	// }
}
