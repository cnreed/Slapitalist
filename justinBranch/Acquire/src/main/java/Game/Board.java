package Game;

import Enums.TileStatus;
import Game.GameInterface.IBoard;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Random;

public class Board implements IBoard {

	public static Logger log = Logger.getLogger(Board.class);
	private int x_size;
	private int y_size;
	private Tile[][] grid;
	private boolean[][] inBag;
	private LinkedList<Tile> bag = new LinkedList<Tile>();
	private Tile[][] randArray;

	int turns; // total turns across all players
	int merges; // total merges across all players
	int companies_started; // total for game - started and re-started

	public Board(int x_size, int y_size) {
		this.x_size = x_size;
		this.y_size = y_size;
		grid = new Tile[x_size][y_size];
		inBag = new boolean[x_size][y_size];
		randArray = new Tile[x_size][y_size];
		initialize();
		randomizeGrid();
		// randPrint();
		initBag();

	}

	public int getX() {
		return x_size;
	}

	public int getY() {
		return y_size;
	}

	/**
	 * Initializes the board on start up.
	 */
	public void initialize() {

		for (int i = 0; i < x_size; i++) {
			for (int j = 0; j < y_size; j++) {
				Tile tile = new Tile(i, j, null, null, TileStatus.InBag);
				checkBoundaries(tile, i, j);
				grid[i][j] = tile;
				randArray[i][j] = tile;
			}
		}

		// System.out.println("Board initialized");

	}

	/**
	 * Prints the board.
	 */
	public void print() {

		String statusIndicator = "";
		for (int i = 0; i < x_size; i++) {
			for (int j = 0; j < y_size; j++) {
				Tile thisTile = grid[i][j];
				if (thisTile.getStatus().equals("ONBOARD")) {
					System.out.print("[" + ((char) (grid[i][j].getRow() + 65))
							+ (grid[i][j].getCol() + 1) + "]\t");
				} else {
					System.out.print("" + ((char) (grid[i][j].getRow() + 65))
							+ (grid[i][j].getCol() + 1) + "\t");
				}
			}
			statusIndicator = "";
			System.out.println();
		}
	}

//	public void randPrint() {
//
//		String statusIndicator = "";
//		for (int i = 0; i < x_size; i++) {
//			for (int j = 0; j < y_size; j++) {
//				Tile thisTile = randArray[i][j];
//				if (thisTile.getStatus().equals("ONBOARD")) {
//					System.out.print("[" + ((char) (randArray[i][j].getRow() + 65))
//							+ (randArray[i][j].getCol() + 1) + "]\t");
//				} else {
//					System.out.print("" + ((char) (randArray[i][j].getRow() + 65))
//							+ (randArray[i][j].getCol() + 1) + "\t");
//				}
//			}
//			statusIndicator = "";
//			System.out.println();
//		}
//	}

	/**
	 * Checks to see if the tile is on the edge of the board. If it is, then set
	 * corresponding possible moves to false.
	 * 
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void checkBoundaries(Tile tile, int x, int y) {
		// log.debug("Tile: " + tile.toString() + " x_size: " + x_size + " x: "
		// + x);
		// log.debug("x_size: " + x_size + );
		if (x <= 0) {
			tile.setTop(false);
		}
		if (y <= 0) {
			tile.setLeft(false);
		}
		if (x >= x_size - 1) {
			tile.setBottom(false);
		}
		if (y >= y_size - 1) {
			tile.setRight(false);
		}
	}

	public Tile getTile(int x, int y) {
		Tile tile = grid[x][y];
		return tile;
	}

	/**
	 * Prints if the corresponding move is possible according to the limits of
	 * the board.
	 *
	 * @param tile
	 */
	public void printBoundaries(Tile tile) {
		System.out.print("Top: " + tile.getTop() + " ");
		System.out.print("Right: " + tile.getRight() + " ");
		System.out.print("Bottom: " + tile.getBottom() + " ");
		System.out.println("Left: " + tile.getLeft());
	}

	/**
	 * Randomizes the values in the array. I think this can be combined with
	 * with init bag, later on.
	 * 
	 */
	private void randomizeGrid() {

		Random rand = new Random();
		int i, j;
		for (i = 0; i < grid.length; i++) {
			for (j = 0; j < grid[i].length; j++) {
				int x = rand.nextInt(i + 1);
				int y = rand.nextInt(j + 1);

				Tile temp = randArray[i][j];
				randArray[i][j] = randArray[x][y];
				randArray[x][y] = temp;
			}
		}
		// System.out.println("random Array initialized");

	}

	public void initBag() {

		for (int i = 0; i < randArray.length; i++) {
			for (int j = 0; j < randArray[i].length; j++) {
				Tile tile = randArray[i][j];
				bag.add(tile);
			}
		}
		// System.out.println("bag initialized sized: " + bag.size());

	}

	/**
	 * Pops a Tile off the top of the bag.
	 * 
	 * @return
	 */
	public Tile bagPop() {
		Tile tile = bag.pop();
		tile.statusUpdate(TileStatus.InHand);
		return tile;
	}

	/**
	 * TODO: Test this function.
	 * 
	 * @param company
	 */
	public void setUnplayable(Company company) {
		Tile change;
		for (int i = 0; i < company.size(); i++) {
			Tile tile = company.getTile(i);
			int x = tile.getRow();
			int y = tile.getCol();
			if (tile.getTop()) {
				change = grid[x][y + 1];
				if (!company.containsTile(change)) {
					change.subStatusUpdate(TileStatus.Unplayable);
				}
			}
			if (tile.getRight()) {
				change = grid[x + 1][y];
				if (!company.containsTile(change)) {
					change.subStatusUpdate(TileStatus.Unplayable);
				}
			}
			if (tile.getBottom()) {
				change = grid[x][y - 1];
				if (!company.containsTile(change)) {
					change.subStatusUpdate(TileStatus.Unplayable);
				}
			}
			if (tile.getLeft()) {
				change = grid[x - 1][y];
				if (!company.containsTile(change)) {
					change.subStatusUpdate(TileStatus.Unplayable);
				}
			}
		}
	}

}
