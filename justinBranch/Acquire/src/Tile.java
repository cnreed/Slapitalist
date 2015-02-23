/**
 * Individual Tiles for the game board.
 * 
 * @author Carolyn
 *
 */
public class Tile {
	int row;
	int col;
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;

	Company ownerCompany; // what company owns it
	Player ownerPlayer; // what player owns it
	String status; // Current status of the tile
	String[] statuses = { "INBAG", "INHAND", "ONBOARD", "QUARANTINED",
			"UNPLAYABLE" };
	// List of statuses the tile can have.

	boolean safe;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
	}

	public Tile(int row, int col, Company ownerCompany, Player ownerPlayer,
			int statusIndex) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
		this.ownerCompany = ownerCompany;
		this.ownerPlayer = ownerPlayer;
		this.status = statuses[statusIndex];
	}

	/**
	 * Return the row of a particular tile.
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Return the col of a particular tile.
	 * 
	 * @return
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Updates the status of the tile.
	 * 
	 * @param statusIndex
	 */
	public void statusUpdate(int statusIndex) {
		this.status = statuses[statusIndex];
	}

	/**
	 * Returns the status of the tile.
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the ownerCompany
	 */
	public Company getOwnerCompany() {
		return ownerCompany;
	}

	/**
	 * @param ownerCompany
	 *            the ownerCompany to set
	 */
	public void setOwnerCompany(Company ownerCompany) {
		this.ownerCompany = ownerCompany;
	}

	/**
	 * @return the ownerPlayer
	 */
	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	/**
	 * @param ownerPlayer
	 *            the ownerPlayer to set
	 */
	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}

	public boolean getTop() {
		return top;
	}

	public boolean getRight() {
		return right;
	}

	public boolean getBottom() {
		return bottom;
	}

	public boolean getLeft() {
		return left;
	}

	@Override
	public String toString() {
		return String.valueOf((char) (this.row + 65))
				+ String.valueOf(this.col + 1);
	}

}