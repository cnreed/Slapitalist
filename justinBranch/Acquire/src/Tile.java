

/**
 * Individual Tiles for the game board.
 * 
 * @author Carolyn
 *
 */
public class Tile {
	String row;
	int col;
	boolean top;
	boolean bottom;
	boolean left;
	boolean right;

	String ownerCompany; // what company owns it
	String ownerPlayer; // what player owns it
	String status;
	String []statuses = {"INBAG", "INHAND", "ONBOARD", "QUARANTINED", "UNPLAYABLE"};

	boolean safe;

	public Tile(String row, int col) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
	}

	public Tile(String row, int col, String ownerCompany, String ownerPlayer,
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
	 * @return
	 */
	public String getRow() {
		return row;
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
	
	public String getStatus(Tile tile) {
		return tile.status;
	}

}