public class WeightBoard {

	Board board;
	double[][] weightBoard;
	private static double weight;
	int x;
	int y;

	public WeightBoard(Board board, double weight) {
		this.board = board;
		this.weight = weight;
		this.weightBoard = new double[board.x_size][board.y_size];
		this.x = board.x_size;
		this.y = board.y_size;
		initialize();

	}

	private void initialize() {
		for (int i = 0; i < board.x_size; i++) {
			for (int j = 0; j < board.y_size; j++) {
				weightBoard[i][j] = 0;
			}
		}
	}

	public void printBoard() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.print(weightBoard[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void recalculate(Board board) {

		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (board.getTile(i, j).status.equals("ONBOARD")) {
					recalculateSurroundings(board.getTile(i, j), weight);
				}

			}
		}

	}

	public void recalculateSurroundings(Tile tile, double weight) {

		int row = tile.getRow();
		int col = tile.getCol();
		if (tile.getTop()) {
			weightBoard[row + 1][col] += weight;
			if (tile.getLeft()) {
				weightBoard[row + 1][col - 1] += weight;
			}
			if (tile.getRight()) {
				weightBoard[row + 1][col + 1] += weight;
			}
		}
		if (tile.getLeft()) {
			weightBoard[row][col - 1] += weight;
		}
		if (tile.getBottom()) {
			weightBoard[row - 1][col] += weight;
			if (tile.getLeft()) {
				weightBoard[row - 1][col - 1] += weight;
			}
			if (tile.getRight()) {
				weightBoard[row - 1][col + 1] += weight;
			}
		}
		if (tile.getRight()) {
			weightBoard[row][col + 1] += weight;
		}

	}
}
