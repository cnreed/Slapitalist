

/**
 * Individual Tiles for the game board.
 * 
 * @author Carolyn
 *
 */
public class Tile {
	String row;				// The string letter
	int col;				// The col location on the board
	int row_x;				// The row location on the board
	boolean top;			// Check if we are within the board
	boolean bottom;			// Check if we are within the board
	boolean left;			// Check if we are within the board
	boolean right;			// Check if we are within the board

	String ownerCompany; 	// what company owns it
	String ownerPlayer; 	// what player owns it
	String status;			// Current status of the tile
	String subStatus;		// Secondary status such as a tile becoming unplayable
	String []statuses = {"INBAG", "INHAND", "ONBOARD", "QUARANTINED", "UNPLAYABLE",
							"INCOMPANY"}; 
							//List of statuses the tile can have.

	boolean safe;

	public Tile(String row, int col) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
	}

	public Tile(String row, int col, int row_x, String ownerCompany, String ownerPlayer,
			int statusIndex) {
		this.row = row;
		this.col = col;
		this.row_x = row_x;
		top = bottom = left = right = true;
		this.ownerCompany = ownerCompany;
		this.ownerPlayer = ownerPlayer;
		this.status = statuses[statusIndex];
		this.subStatus = statuses[statusIndex];
	}
	
	/**
	 * Return the row of a particular tile.
	 * @return
	 */
	public String getRow() {
		return row;
	}
	
	public int getRow_x() {
		return row_x;
	}
	
	/**
	 * Return the col of a particular tile.
	 * @return
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Updates the status of the tile.
	 * @param statusIndex
	 */
	public void statusUpdate(int statusIndex) {
		this.status = statuses[statusIndex];
	}
	
	/**
	 * This status is for tiles that are in someone's hand, that become
	 * unplayable.
	 * @param statusIndex
	 */
	public void subStatusUpdate(int statusIndex) {
		this.subStatus = statuses[statusIndex];
	}
	/**
	 * Returns the status of the tile.
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * A boolean that checks if we are above above any possible boolean values
	 * @return
	 */
	public boolean getTop() {
		return top;
	}
	
	/**
	 * A boolean that checks if we are past the right side of the grid.
	 * @return
	 */
	public boolean getRight() {
		return right;
	}
	
	/**
	 * A boolean check that makes sure we have not surpassed the bottom of the
	 * grid.
	 * @return
	 */
	public boolean getBottom() {
		return bottom;
	}
	
	/**
	 * A boolean check that makes sure we have not surpassed the left side of 
	 * the grid.
	 * @return
	 */
	public boolean getLeft() {
		return left;
	}
	
	public String getSubStatus() {
		return subStatus;
	}
	
	public String print() {
		return row + "" + col;
	}

}