import org.apache.log4j.Logger;

public class WeightBoard {

	public static Logger log = Logger.getLogger(WeightBoard.class);
	Board board;
	WeightTile[][] weightBoard;
	private static float weight;
	int x;
	int y;

	public WeightBoard(Board board, float weight) {
		this.board = board;
		this.weight = weight;
		this.weightBoard = new WeightTile[board.x_size][board.y_size];
		this.x = board.x_size;
		this.y = board.y_size;
		initialize();

	}

	private void initialize() {
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				Tile tile = board.getTile(i, j);
				WeightTile wTile = new WeightTile(tile, i, j);
				weightBoard[i][j] = wTile;
			}
		}
	}

	public void printBoard() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.print(weightBoard[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public void recalculate(Board board) {

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (board.getTile(i, j).status.equals("ONBOARD")) {
					weightBoard[i][j].removeWeight();
					recalculateSurroundings(board, board.getTile(i, j));
				}

			}
		}

	}

	public void recursiveRecalculate(Board board, Tile tile, Tile orig) {

		tile.visited = true;
		int row = tile.getRow();
		int col = tile.getCol();
		if (tile.getTop()) {
			Tile top = board.getTile(row - 1, col);
			log.debug("Top Tile: " + top.toString());
			if (top.getStatus().equals("ONBOARD") && !top.visited) {
				recursiveRecalculate(board, top, orig);
			}
		}
		if (tile.getLeft()) {
			Tile left = board.getTile(row, col - 1);
			log.debug("Left Tile: " + left.toString());
			if (left.getStatus().equals("ONBOARD") && !left.visited) {
				recursiveRecalculate(board, left, orig);
			}
		}
		if (tile.getRight()) {
			Tile right = board.getTile(row, col + 1);
			log.debug("Right Tile: " + right.toString());
			if (right.getStatus().equals("ONBOARD") && !right.visited) {
				recursiveRecalculate(board, right, orig);
			}

		}
		if (tile.getBottom()) {
			Tile bottom = board.getTile(row + 1, col);
			if (bottom.getStatus().equals("ONBOARD") && !bottom.visited) {
				recursiveRecalculate(board, bottom, orig);
			}
		}
		tile.visited = false;
		// if (tile.equals(orig)) {
		recalculateSurroundings(board, tile);
		// }

	}

	private boolean surroundedByOnBoard(Tile tile, Board board) {

		int onboardCount = 0;
		int row = tile.getRow();
		int col = tile.getCol();
		Tile top, right, left, bottom;
		if (tile.getTop()) {
			top = board.getTile(row - 1, col);
			if (top.getStatus().equals("ONBOARD"))
				;
		}
		return false;
	}

	private void centerTile(Tile tile, Board board) {
		int row = tile.getRow();
		int col = tile.getCol();
		double addedWeight = 0;
		Tile top, topRight, topLeft, left, right, bottom, bottomRight, bottomLeft;
		System.out.println("Tile: " + tile.toString());
		if (tile.getTop()) {
			log.debug("Top tile before getTile");
			top = board.getTile(row - 1, col);
			log.debug("Top tile: " + top.toString());
			if (top.getStatus().equals("ONBOARD")) {
				// System.out.println("Adding Weight");
				addedWeight += weight;
				// System.out.println("Added Weight: " + addedWeight);
			}
			if (tile.getRight()) {
				log.debug("topRight tile before getTile");
				topRight = board.getTile(row - 1, col + 1);
				log.debug("topRight tile: " + topRight.toString());
				if (topRight.getStatus().equals("ONBOARD")) {
					addedWeight += weight;
				}
			}
			if (tile.getLeft()) {
				log.debug("topLeft tile before getTile");
				topLeft = board.getTile(row - 1, col - 1);
				log.debug("topLeft tile: " + topLeft.toString());
				if (topLeft.getStatus().equals("ONBOARD")) {
					addedWeight += weight;
				}
			}
		}
		if (tile.getLeft()) {
			log.debug("left tile before getTile");
			left = board.getTile(row, col - 1);
			log.debug("left tile: " + left.toString());
			if (left.getStatus().equals("ONBOARD")) {
				addedWeight += weight;
			}
		}
		if (tile.getRight()) {
			log.debug("right tile before getTile");
			right = board.getTile(row, col + 1);
			log.debug("right tile: " + right.toString());
			if (right.getStatus().equals("ONBOARD")) {
				addedWeight += weight;
			}

		}
		if (tile.getBottom()) {
			log.debug("tile.getBottom(): " + tile.getBottom());
			log.debug("bottom tile before getTile");
			bottom = board.getTile(row + 1, col);
			log.debug("bottom tile : " + bottom.toString());
			if (bottom.getStatus().equals("ONBOARD")) {
				addedWeight += weight;
			}
			if (tile.getRight()) {
				log.debug("bottomRight tile before getTile");
				bottomRight = board.getTile(row + 1, col + 1);
				log.debug("bottomRight tile: " + bottomRight.toString());
				if (bottomRight.getStatus().equals("ONBOARD")) {
					addedWeight += weight;
				}
			}
			if (tile.getLeft()) {
				log.debug("bottomLeft tile before getTile");
				bottomLeft = board.getTile(row + 1, col - 1);
				log.debug("bottomLeft tile: " + bottomLeft.toString());
				if (bottomLeft.getStatus().equals("ONBOARD")) {
					addedWeight += weight;
				}

			}
		}
		System.out.println("addedWeight: " + addedWeight);
		System.out.println("Weight: " + weight);
		weightBoard[row][col].addWeight(addedWeight);

	}

	public void centerTileStatus(Tile tile, Board board) {

		if (tile.getTop()) {
			Tile top = board.getTile(tile.getRow() - 1, tile.getCol());
			System.out.println("Top: " + top.toString());
			if (tile.getRight()) {
				Tile topRight = board.getTile(tile.getRow() - 1,
						tile.getCol() + 1);
				System.out.println("topRight: " + topRight.toString());
			}
		}
		if (tile.getLeft()) {
			Tile left = board.getTile(tile.getRow(), tile.getCol() - 1);
			System.out.println("Left: " + left.toString());
		}
		if (tile.getRight()) {
			Tile right = board.getTile(tile.getRow(), tile.getCol() + 1);
			System.out.println("Right: " + right.toString());

		}
		if (tile.getBottom()) {
			Tile bottom = board.getTile(tile.getRow() + 1, tile.getCol());
			System.out.println("Bottom: " + bottom.toString());
		}
	}

	public void recalculateSurroundings(Board board, Tile tile) {

		// TODO For the "outskirt" tiles make sure that from that position you
		// can get the diagonals to get the most accurate weight for that tile,
		// there is a problem with a company that is in the shape of an L, it
		// may not calculate correctly.

		// What you could do is grab an "outskirt" tile and "center" it. and
		// then calculate the weight from that position, which will give me the
		// proper weight without effecting the recursion that I already have. I
		// will have to make a separate function to do this.
		int row = tile.getRow();
		int col = tile.getCol();
		if (tile.getTop()
				&& canCalculate(board.getTile(row - 1, col).getStatus())) {
			centerTile(board.getTile(row - 1, col), board);
		}
		if (tile.getLeft()
				&& canCalculate(board.getTile(row, col - 1).getStatus())) {
			centerTile(board.getTile(row, col - 1), board);
		}
		if (tile.getBottom()
				&& canCalculate(board.getTile(row + 1, col).getStatus())) {
			centerTile(board.getTile(row + 1, col), board);
		}
		if (tile.getRight()
				&& canCalculate(board.getTile(row, col + 1).getStatus())) {
			centerTile(board.getTile(row, col + 1), board);
		}

	}

	public void boardWipe() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				weightBoard[i][j].removeWeight();

			}
		}
	}

	private boolean canCalculate(String status) {

		return (!status.equals("ONBOARD"));

	}
}
