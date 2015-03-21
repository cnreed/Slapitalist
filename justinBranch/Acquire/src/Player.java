import java.util.HashMap;

public class Player {

	int totalCash;
	String name;

	int pid; // game session unique id
	int handSize = 6;
	Tile[] hand = new Tile[handSize];
	int companies_started;
	int companies_merged;
	int number_of_turns;
	static int numHand = 0; // The number of Tiles in the hand.

	public static HashMap<Company, StockCertificate> playerStockList = new HashMap<>();

	boolean my_turn;
	boolean my_merge_turn;

	public Player(String name, Grid board) {
		// System.out.println("Creating player " + name);
		this.name = name;
		this.totalCash = 6000;
		this.hand = new Tile[handSize];
		this.numHand = 0;
	}

	public void addHand(Tile tile) {
		// System.out.println("Tile # " + numHand + " for " + this.name);
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

	public String getName() {
		return name;
	}

	// public Tile getInitTile() {
	// return initTile;
	// }

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

	public void stockAquisition() {
		int numStock = 0;
		while (numStock != 3) {
			buyStock();
		}
	}

	/**
	 * TODO: have a switch statement of stocks.
	 * 
	 * @return
	 */
	public int buyStock() {
		return 0;
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

	public void addCertificate(StockCertificate certificate) {

		/* if they already have stock in this company, just add quantity */
		if (playerStockList.get(certificate.companyOwner) != null) {
			StockCertificate tempCert = playerStockList
					.get(certificate.companyOwner);
			int oldQuantity = tempCert.quantity;
			tempCert.setQuantity(oldQuantity + certificate.quantity);
			playerStockList.put(certificate.companyOwner, tempCert);
		}
		/* else just add to list */
		else {
			playerStockList.put(certificate.companyOwner, certificate);
		}
		System.out.println("Congratulations on your acquisition of "
				+ certificate.quantity + " share(s) of "
				+ certificate.companyOwner.getCompanyName() + "!");

	}

	public int sharesQuery(Company company) {

		if (playerStockList.get(company) != null)
			return playerStockList.get(company).quantity;

		return 0;
	}
}
