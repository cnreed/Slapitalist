import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class Company {

	public int CID; // company number ID;
	private String companyName; // name of company intialized
	private int companySize;
	private static ArrayList<Tile> companyTiles; // all tiles owned by the
													// company
	private static Color companyColor; // specific color associated with the
										// company
	private static List<playerNode> shareHolders;
	private int companyTier;
	boolean isSafe; // start false
	boolean gameEndable; // start false
	boolean onBoard; // start false

	public Company(String companyName, int companyTier, Color companyColor,
			int companySize, int occurence) {
		this.companyName = companyName;
		this.companyTier = companyTier;
		this.companyColor = companyColor;
		this.companySize = companySize;
		this.CID = occurence;

		shareHolders = new LinkedList();
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

	public static Color getCompany_Color() {
		return companyColor;
	}

	void insertShareHolder(String name, int shareCount) {

		playerNode tempNode = new playerNode(name, shareCount);
		shareHolders.add(tempNode);
	}

	Player getMajority() {

		playerNode maj = shareHolders.get(0);
		for (playerNode player : shareHolders) {
			if (player.shareCount > maj.shareCount) {
				maj = player;
			}
		}

		return Main.getPlayer(maj.playerName);
	}

	void set_safe() {
		isSafe = true;
	}

	void set_endable() {
		gameEndable = true;
	}

	void set_onboard() {
		onBoard = true;
	}

	void dissolve() { // drops all backend values to 0, which cascades to
						// prices, etc.
		onBoard = false; // doesn't affect people that have stock
		isSafe = false;
		gameEndable = false;
		companySize = 2;

	}

}