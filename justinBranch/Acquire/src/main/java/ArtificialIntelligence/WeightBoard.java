package ArtificialIntelligence;

import ArtificialIntelligence.ArtficialIntelligenceInterface.IWeightBoard;
import Game.Board;
import Game.Tile;
import org.apache.log4j.Logger;

public class WeightBoard implements IWeightBoard {

	public static Logger log = Logger.getLogger(WeightBoard.class);
	Board board;
	WeightTile[][] weightBoard;
	private static float weight;
	int x;
	int y;

	public WeightBoard(Board board, float weight) {
		this.board = board;
		this.weight = weight;
		this.weightBoard = new WeightTile[board.getX()][board.getY()];
		this.x = board.getX();
		this.y = board.getY();
		initialize();

	}

	private void initialize() {
		for (int i = 0; i < board.getX(); i++) {
			for (int j = 0; j < board.getY(); j++) {
				Tile tile = board.getTile(i, j);
				WeightTile wTile = new WeightTile(tile, i, j);
				weightBoard[i][j] = wTile;
			}
		}
		// printTileWithCoordinates();
	}

	public void printBoard() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.printf("%.2f\t", weightBoard[i][j].getWeight());
			}
			System.out.println();
		}
	}

	public void printTileWithCoordinates() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				Tile tile = weightBoard[i][j].getTile();
				System.out.print(tile + " (" + tile.getRow() + ", "
						+ tile.getCol() + ")\t");
			}
			System.out.println();
		}
	}

	public void printSurroundCountBoard() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				System.out.printf(weightBoard[i][j].getSurroundCount() + "\t");
			}
			System.out.println();
		}
	}

	private int centerTile(Tile tile, Board board) {
		int row = tile.getRow();
		int col = tile.getCol();
		int count = 0;
		Tile top, left, right, bottom, topLeft, topRight, bottomRight, bottomLeft;
		// System.out.println("function centTile - Tile: " + tile.toString());
		if (tile.getTop()) {
			log.debug("Top tile before getTile");
			top = board.getTile(row - 1, col);
			log.debug("Top tile: " + top.toString());
			if (top.getStatus().equals("ONBOARD")) {
				count++;
			}

		}
		if (tile.getLeft()) {
			log.debug("left tile before getTile");
			left = board.getTile(row, col - 1);

			if (left.getStatus().equals("ONBOARD")) {
				count++;
			}
		}
		if (tile.getRight()) {
			log.debug("right tile before getTile");
			right = board.getTile(row, col + 1);
			log.debug("right tile: " + right.toString());
			if (right.getStatus().equals("ONBOARD")) {
				count++;
			}

		}
		if (tile.getBottom()) {
			log.debug("tile.getBottom(): " + tile.getBottom());
			log.debug("bottom tile before getTile");
			bottom = board.getTile(row + 1, col);
			log.debug("bottom tile : " + bottom.toString());
			if (bottom.getStatus().equals("ONBOARD")) {
				count++;
			}

		}

		if (tile.getLeft() && tile.getTop()) {
			topLeft = board.getTile(row - 1, col - 1);
			if (topLeft.getStatus().equals("ONBOARD")) {
				count++;
			}
		}
		if (tile.getRight() && tile.getTop()) {
			topRight = board.getTile(row - 1, col + 1);
			if (topRight.getStatus().equals("ONBOARD")) {
				count++;
			}
		}
		if (tile.getLeft() && tile.getBottom()) {
			bottomLeft = board.getTile(row + 1, col - 1);
			if (bottomLeft.getStatus().equals("ONBOARD")) {
				count++;
			}
		}
		if (tile.getRight() && tile.getBottom()) {
			bottomRight = board.getTile(row + 1, col + 1);
			if (bottomRight.getStatus().equals("ONBOARD")) {
				count++;
			}
		}

		return count;

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

	/**
	 * TODO: Third iteration of trying to figure out how to add weight. THis is
	 * much simplier than the tries above. When a tile is placed add one to
	 * top/bottom/left/right. Conditional Requirements: top/left top/right
	 * bottom/left bottom/right
	 * 
	 * @param tile
	 */
	public void tilePlaced(Tile tile) {

		int row = tile.getRow();
		int col = tile.getCol();
		Tile left = null, right = null, bottom = null, top = null;
		Tile topLeft = null, topRight = null, bottomLeft = null, bottomRight = null;
		System.out.println("row: " + row + " col: " + col);
		if (tile.getStatus().equals("ONBOARD")) {
			weightBoard[row][col].removeSurroundCount();
		}
		if (tile.getLeft()) {
			// TODO think about adding the four tiles to the weightTile.
			left = board.getTile(row, col - 1);
			if (left.getStatus().equals("ONBOARD")) {
				weightBoard[row][col - 1].removeSurroundCount();
				weightBoard[row][col - 1].removeWeight();
			} else {
				weightBoard[row][col - 1].addWeight(weight);
				weightBoard[row][col - 1].updateSurroundCount();
			}
		}
		if (tile.getRight()) {
			right = board.getTile(row, col + 1);
			if (right.getStatus().equals("ONBOARD")) {
				weightBoard[row][col + 1].removeSurroundCount();
				weightBoard[row][col + 1].removeWeight();
			} else {
				weightBoard[row][col + 1].addWeight(weight);
				weightBoard[row][col + 1].updateSurroundCount();
			}
		}
		if (tile.getTop()) {

			top = board.getTile(row - 1, col);
			if (top.getStatus().equals("ONBOARD")) {
				weightBoard[row - 1][col].removeSurroundCount();
				weightBoard[row - 1][col].removeWeight();
			} else {
				weightBoard[row - 1][col].addWeight(weight);
				weightBoard[row - 1][col].updateSurroundCount();
				if (tile.getLeft()) {
					bottomLeft = board.getTile(top.getRow() + 1,
							top.getCol() - 1);
					if (bottomLeft.getStatus().equals("ONBOARD")) {
						weightBoard[row - 1][col].addWeight(weight);
						weightBoard[row - 1][col].updateSurroundCount();

					}
				}
				if (tile.getRight()) {
					bottomRight = board.getTile(top.getRow() + 1,
							top.getCol() + 1);
					if (bottomRight.getStatus().equals("ONBOARD")) {
						weightBoard[row - 1][col].addWeight(weight);
						weightBoard[row - 1][col].updateSurroundCount();

					}
				}
			}
			System.out.println("Top: " + top.toString());
			int count = centerTile(top, board);
			System.out.println("Hello World");
			System.out.println("Count: " + count);
			System.out.println("weightBoard[row-1][col]: "
					+ weightBoard[row - 1][col].getSurroundCount());
			if (count < weightBoard[row - 1][col].getSurroundCount()
					&& !top.getStatus().equals("ONBOARD")) {
				int subtract = weightBoard[row - 1][col].getSurroundCount()
						- count;
				for (int i = 0; i < subtract; i++) {
					// TODO Write functions for these two
					weightBoard[row - 1][col].surroundCount -= 1;
					weightBoard[row - 1][col].weight -= weight;
				}
			}
		}
		if (tile.getBottom()) {
			bottom = board.getTile(row + 1, col);
			if (bottom.getStatus().equals("ONBOARD")) {
				weightBoard[row + 1][col].removeSurroundCount();
			} else {
				weightBoard[row + 1][col].addWeight(weight);
				weightBoard[row + 1][col].updateSurroundCount();
				if (tile.getLeft()) {
					topLeft = board.getTile(bottom.getRow() - 1,
							bottom.getCol() - 1);
					if (topLeft.getStatus().equals("ONBOARD")) {
						weightBoard[row + 1][col].addWeight(weight);
						weightBoard[row + 1][col].updateSurroundCount();
						System.out.println("topLeft: "
								+ centerTile(topLeft, board));

					}
				}
				if (tile.getRight()) {
					topRight = board.getTile(bottom.getRow() - 1,
							bottom.getCol() + 1);
					if (topRight.getStatus().equals("ONBOARD")) {
						weightBoard[row + 1][col].addWeight(weight);
						weightBoard[row + 1][col].updateSurroundCount();
						System.out.println("topRight: "
								+ centerTile(topRight, board));

					}
				}
			}

			System.out.println("Bottom: " + bottom.toString());
			int count = centerTile(bottom, board);
			System.out.println("Count: " + count);
			System.out.println("weightBoard[row+1][col]: "
					+ weightBoard[row + 1][col].getSurroundCount());
			if (count < weightBoard[row + 1][col].getSurroundCount()
					&& !bottom.getStatus().equals("ONBOARD")) {
				System.out.println("HELLO");
				int subtract = weightBoard[row + 1][col].getSurroundCount()
						- count;
				for (int i = 0; i < subtract; i++) {
					weightBoard[row + 1][col].surroundCount -= 1;
					weightBoard[row + 1][col].weight -= weight;
				}
			}
		}

		if (tile.getLeft() && tile.getTop()) {
			topLeft = board.getTile(row - 1, col - 1);
			if (topLeft.getStatus().equals("ONBOARD")) {
				weightBoard[row - 1][col - 1].removeSurroundCount();
				weightBoard[row - 1][col - 1].removeWeight();

			} else if (centerTile(topLeft, board) >= 1) {
				weightBoard[row - 1][col - 1].addWeight(weight);
				weightBoard[row - 1][col - 1].updateSurroundCount();
			}
		}
		if (tile.getRight() && tile.getTop()) {

			topRight = board.getTile(row - 1, col + 1);
			if (topRight.getStatus().equals("ONBOARD")) {
				weightBoard[row - 1][col + 1].removeSurroundCount();
				weightBoard[row - 1][col + 1].removeWeight();
			} else if (centerTile(topRight, board) >= 1) {
				weightBoard[row - 1][col + 1].addWeight(weight);
				weightBoard[row - 1][col + 1].updateSurroundCount();
			}

		}
		if (tile.getLeft() && tile.getBottom()) {
			bottomLeft = board.getTile(row + 1, col - 1);
			if (bottomLeft.getStatus().equals("ONBOARD")) {
				weightBoard[row + 1][col - 1].removeSurroundCount();
				weightBoard[row + 1][col - 1].removeWeight();
			} else if (centerTile(bottomLeft, board) >= 1) {
				weightBoard[row + 1][col - 1].updateSurroundCount();
				weightBoard[row + 1][col - 1].addWeight(weight);
			}
		}
		if (tile.getRight() && tile.getBottom()) {
			bottomRight = board.getTile(row + 1, col + 1);

			if (bottomRight.getStatus().equals("ONBOARD")) {
				weightBoard[row + 1][col + 1].removeSurroundCount();
				weightBoard[row + 1][col + 1].removeWeight();
			} else if (centerTile(bottomRight, board) >= 1) {
				weightBoard[row + 1][col + 1].updateSurroundCount();
				weightBoard[row + 1][col + 1].addWeight(weight);
			}
		}

	}

	public void boardWipe() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				weightBoard[i][j].removeWeight();
				weightBoard[i][j].removeSurroundCount();

			}
		}
	}

	private boolean canCalculate(String status) {

		return (!status.equals("ONBOARD"));

	}
}
