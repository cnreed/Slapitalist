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

	public enum tileState {
		INBAG, INHAND, ONBOARD, QUARANTINED, UNPLAYABLE
	}

	public tileState thisTileState;

	boolean safe;

	public Tile(String row, int col) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
	}

	public Tile(String row, int col, String ownerCompany, String ownerPlayer,
			tileState thisTileState) {
		this.row = row;
		this.col = col;
		top = bottom = left = right = true;
		this.ownerCompany = ownerCompany;
		this.ownerPlayer = ownerPlayer;
		this.thisTileState = thisTileState;
	}
	
	public String getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

}