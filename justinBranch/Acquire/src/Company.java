
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class Company {

	public static final String RESET = "\u001B[0m";

	public int CID; // company number ID;
	private String companyName; // name of company intialized
	private int companySize;
	public ArrayList<Tile> companyTiles; // all tiles owned by the
													// company
	private static String companyColor; // specific color associated with the
										// company
	private static List<playerNode> shareHolders;
	private int companyTier;
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

		shareHolders = new LinkedList<playerNode>();

		System.out.println(companyColor + companyName + " initiated." + RESET);
	}

	/*
	 * We'll need to implement the pricing cliff here. it'll have to be weighted
	 * based on company_Tier; Im the future we can create a base value and tier
	 * growth scale to make it more flexible for player options. but for now. we
	 * can hardcode the base value in. His code has good schema for it. -j
	 */

	int getCID() {
		return this.CID;
	}

	void increment_size() {
		companySize++;
		if (companySize == Main.getSafeSize()) {
			isSafe = true;
		} // makes company safe if safe_size is achieved
	}

	void insertShareHolder(String name, int shareCount) {
		shareHolders.add(new playerNode(name, shareCount));
	}

	String getMajority() {
		Collections.sort(shareHolders);
		return shareHolders.get(0).playerName;
	}

	String getMinority() {
		Collections.sort(shareHolders);
		return shareHolders.get(1).playerName;
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
	}

}