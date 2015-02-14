/**
	 * Individual Tiles for the game board.
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
		
		public Tile(String row, int col) {
			this.row = row;
			this.col = col;
			top = bottom = left = right =true;
		}
	}