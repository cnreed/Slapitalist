import java.util.ArrayList;

import org.apache.log4j.Logger;

class Company {

	public static Logger log = Logger.getLogger(Company.class);
	public static final String RESET = "\u001B[0m";

	private int CID; // company number ID;
	private String companyName; // name of company intialized
	public int companySize;
	public ArrayList<Tile> companyTiles; // all tiles owned by the
											// company
	private static String companyColor; // specific color associated with the
										// company
	private static int[] sharePrice = new int[42];

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
		calculateSharePrice(companyTier);
		// System.out.println(this.companyName + " " + sharePrice[2] + " to "
		// + sharePrice[41]);
	}

	private void calculateSharePrice(int tier) {
		int tierValue = 0;
		// System.out.println("Tier: " + tier);
		tierValue = 100 * tier;

		for (int i = 0; i < sharePrice.length; i++) {
			if (i < 6)
				sharePrice[i] = (i * 100) + tierValue;
			if (i > 5 && i < 11)
				sharePrice[i] = (600) + tierValue;
			if (i > 10 && i < 21)
				sharePrice[i] = (700) + tierValue;
			if (i > 20 && i < 31)
				sharePrice[i] = (800) + tierValue;
			if (i > 30 && i < 41)
				sharePrice[i] = (900) + tierValue;
			if (i == 41)
				sharePrice[i] = 1000 + tierValue;
		}
	}
	

	public int getMajorityPayout() {
		return sharePrice[this.companySize] * 10;
	}

	public int getMinorityPayout() {
		return sharePrice[this.companySize] * 5;
	}

	public int getCID() {
		return this.CID;
	}

	void incrementSize() {
		companySize++;
		if (companySize == Main.getSafeSize()) {
			isSafe = true;
			// TODO - Set unplayable
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
		companySize = 0;

	}

	public void addTile(Tile tile) {
		incrementSize();
		companyTiles.add(tile);
	}

	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param stockCount
	 *            the stockCount to set
	 */
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}

	public int getStockCount() {
		return this.stockCount;
	}

	public int getSharePrice(int companySize) {
		return sharePrice[companySize];
	}
	
	public void addStockBack(int quantity) {
		stockCount += quantity;
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
	
	public void logPrintTiles() {
		for(int i = 0; i < companyTiles.size(); i++) {
			Tile tile = companyTiles.get(i);
			log.debug(" " + tile.toString());
		}
	}
}
