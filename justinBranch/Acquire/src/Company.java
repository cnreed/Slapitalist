import java.util.ArrayList;
import java.util.List;

class Company {

	public static final String RESET = "\u001B[0m";

	public int CID; // company number ID;
	private String companyName; // name of company intialized
	public int companySize;
	public ArrayList<Tile> companyTiles; // all tiles owned by the
											// company
	private static String companyColor; // specific color associated with the
										// company

	private static ArrayList<StockCertificate> companySharesList;

	private static List<playerNode> shareHolders;
	private int companyTier;
	private int stockCount;
	boolean isSafe; // start false
	boolean gameEndable; // start false
	boolean onBoard; // start false

	public Company(String companyName, int companyTier, String companyColor,
			int companySize, int occurence) {
		this.companyName = companyName;
		this.companyTier = companyTier;
		Company.companyColor = companyColor;
		this.companySize = companySize;
		this.CID = occurence;
		this.stockCount = 25;
		companyTiles = new ArrayList<Tile>();
		PayCliff paycliff = new PayCliff(this.companyTier);
		// System.out.println(companyColor + companyName + " initiated." +
		// RESET);
	}

	int getCID() {
		return this.CID;
	}

	void increment_size() {
		companySize++;
		if (companySize == Main.getSafeSize()) {
			isSafe = true;
		} // makes company safe if safe_size is achieved
	}

	void setSafe() {
		isSafe = true;
	}

	public boolean getSafe() {
		return isSafe;
	}

	void setEndable() {
		gameEndable = true;
	}

	void setOnboard() {
		onBoard = true;
	}

	void dissolve() { // drops all backend values to 0, which cascades to
						// prices, etc.
		onBoard = false; // doesn't affect people that have stock
		isSafe = false;
		gameEndable = false;
		companySize = 2;

	}

	public void addTile(Tile tile) {
		companyTiles.add(tile);
		// increment_size();
	}

	public String getCompanyName() {
		return companyName;
	}

	public int getStockCount() {
		return this.stockCount;
	}

	public int getSharePrice(int companySize) {
		return PayCliff.getSharePrice(companySize);
	}

	/*
	 * verifies that we can sellstock by decrementing stock count and return
	 * result
	 */
	public boolean soldStock(int quantity) {
		if (stockCount > quantity) {
			stockCount -= quantity;
			return true;
		} else {
			return false;
		}
	}
}
