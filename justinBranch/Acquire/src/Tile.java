/**
 * Individual Tiles for the game board.
 * 
 * @author Carolyn
 *
 */

import org.apache.log4j.Logger;

public class Tile {
	
	public static Logger log = Logger.getLogger(Tile.class);
	int row;
	int col;
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;
	boolean visited;

	Company ownerCompany; // what company owns it
	Player ownerPlayer; // what player owns it
	String status; // Current status of the tile
	String[] statuses = { "INBAG", "INHAND", "ONBOARD", "QUARANTINED",
			"UNPLAYABLE", "INCOMPANY" };
	// List of statuses the tile can have.

	boolean safe;
	String subStatus;

	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		this.visited = false;
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
		this.subStatus = statuses[statusIndex];
		this.visited = false;
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
	 * This status is for tiles that are in someone's hand, that become
	 * unplayable.
	 * 
	 * @param statusIndex
	 */
	public void subStatusUpdate(int statusIndex) {
		this.subStatus = statuses[statusIndex];
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

	/**
	 * A boolean that checks if we are above above any possible boolean values
	 * 
	 * @return
	 */
	public boolean getTop() {
		return top;
	}

	/**
	 * A boolean that checks if we are past the right side of the grid.
	 * 
	 * @return
	 */
	public boolean getRight() {
		return right;
	}

	/**
	 * A boolean check that makes sure we have not surpassed the bottom of the
	 * grid.
	 * 
	 * @return
	 */
	public boolean getBottom() {
		return bottom;
	}

	/**
	 * A boolean check that makes sure we have not surpassed the left side of
	 * the grid.
	 * 
	 * @return
	 */

	public boolean getLeft() {
		return left;
	}

	public String getSubStatus() {
		return subStatus;
	}
	
	

	@Override
	public String toString() {
		return String.valueOf((char) (this.row + 65))
				+ String.valueOf(this.col + 1);
	}

}
