import java.util.LinkedList;
import java.util.Random;

import org.apache.log4j.Logger;

public class Grid {

	public static Logger log = Logger.getLogger(Grid.class);
	int x_size;
	int y_size;
	Tile[][] grid;
	boolean[][] inBag;
	LinkedList<Tile> bag = new LinkedList<Tile>();
	Tile[][] randArray;

	
	int turns; // total turns across all players
	int merges; // total merges across all players
	int companies_started; // total for game - started and re-started

	public Grid(int x_size, int y_size) {
		this.x_size = x_size;
		this.y_size = y_size;
		grid = new Tile[x_size][y_size];
		inBag = new boolean[x_size][y_size];
		randArray = new Tile[x_size][y_size];
		initialize();
		randomizeGrid();
		initBag();

	}

	/**
	 * Initializes the board on start up.
	 */
	public void initialize() {

		for (int i = 0; i < x_size; i++) {
			for (int j = 0; j < y_size; j++) {
				Tile tile = new Tile(i, j, null, null, 0);

				grid[i][j] = tile;
				randArray[i][j] = tile;
				inBag[i][j] = true;
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
					System.out.print("[" + ((char) (grid[i][j].row + 65))
							+ (grid[i][j].col + 1) + "]\t");
				} else {
					System.out.print("" + ((char) (grid[i][j].row + 65))
							+ (grid[i][j].col + 1) + "\t");
				}
			}
			statusIndicator = "";
			System.out.println();
		}
	}

	/**
	 * Checks to see if the tile is on the edge of the board. If it is, then set
	 * corresponding possible moves to false.
	 * 
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void checkBoundaries(Tile tile, int x, int y) {
		if (x <= 0) {
			tile.top = false;
		}
		if (y <= 0) {
			tile.left = false;
		}
		if (x >= 11) {
			tile.bottom = false;
		}
		if (y >= 11) {
			tile.right = false;
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
		System.out.print("Top: " + tile.top + " ");
		System.out.print("Right: " + tile.right + " ");
		System.out.print("Bottom: " + tile.bottom + " ");
		System.out.println("Left: " + tile.left);
	}

	/**
	 * Randomizes the values in the array. I think this can be combined with
	 * with init bag, later on.
	 * 
	 */
	public void randomizeGrid() {

		Random rand = new Random();
		int i, j;
		for (i = 0; i < grid.length - 1; i++) {
			for (j = 0; j < grid[i].length - 1; j++) {
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
	 * @return
	 */
	public Tile bagPop() {
		Tile tile = bag.pop();
		tile.statusUpdate(1);
		return tile;
	}

	/**
	 * TODO: Test this function.
	 * 
	 * @param company
	 */
	public void setUnplayable(Company company) {
		Tile change;
		for (int i = 0; i < company.companyTiles.size(); i++) {
			Tile tile = company.companyTiles.get(i);
			int x = tile.getRow();
			int y = tile.getCol();
			if (tile.getTop()) {
				change = grid[x][y + 1];
				if (!company.companyTiles.contains(change)) {
					change.subStatusUpdate(4);
				}
			}
			if (tile.getRight()) {
				change = grid[x + 1][y];
				if (!company.companyTiles.contains(change)) {
					change.subStatusUpdate(4);
				}
			}
			if (tile.getBottom()) {
				change = grid[x][y - 1];
				if (!company.companyTiles.contains(change)) {
					change.subStatusUpdate(4);
				}
			}
			if (tile.getLeft()) {
				change = grid[x - 1][y];
				if (!company.companyTiles.contains(change)) {
					change.subStatusUpdate(4);
				}
			}
		}
	}

}
