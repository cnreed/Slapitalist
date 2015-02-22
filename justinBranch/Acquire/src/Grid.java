
import java.util.LinkedList;
import java.util.Random;


//import static package.enum;
public class Grid {

	int x_size;
	int y_size;
	String[] stringArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };
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
	}

	/**
	 * Initializes the board on start up.
	 */
	public void initialize() {

		System.out.println();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				String row = stringArray[i];
				Tile tile = new Tile(row, j, null, null, 0);
				grid[i][j] = tile;
				randArray[i][j] = tile;
				inBag[i][j] = true;
			}
		}
		System.out.println("Board initialized");

	}

	/**
	 * Prints the board.
	 */
	public void print() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j].row + grid[i][j].col + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * Prints a specific Tile array.
	 * 
	 * @param array
	 */
	private void print(Tile[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j].row + "" + array[i][j].col + ", ");
			}
			System.out.println();
		}
	}

	/**
	 * Randomly selects a tile from the grid.
	 * 
	 * @return
	 */
	public Tile draw() {
		Random rand = new Random();
		Tile tile = null;
		int x = rand.nextInt(9);
		int y = rand.nextInt(12);
		if (!inBag[x][y]) {
			return null;
		}
		tile = grid[x][y];
		inBag[x][y] = false;
		checkBoundaries(tile, x, y);
		return tile;
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
	 * TODO: Actually make a stack or queue of the bag
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
		// print(randArray);

	}

	public void initBag() {

		for (int i = 0; i < randArray.length; i++) {
			for (int j = 0; j < randArray[i].length; j++) {
				Tile tile = randArray[i][j];
				bag.add(tile);
			}
		}

	}

	public Tile bagPop() {
		Tile tile = bag.pop();
		tile.statusUpdate(1);
		return tile;
	}

	/**
	 * This is a testing Main.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Grid board = new Grid(9, 12);
		board.initialize();
		board.print();
		Tile tile = board.draw();
		System.out.println("Row: " + tile.row + " Col: " + tile.col);
		Tile tile1 = board.draw();
		System.out.println("Row: " + tile1.row + " Col: " + tile1.col);
		Tile tile2 = board.draw();
		System.out.println("Row: " + tile2.row + " Col: " + tile2.col);
		Tile tile3 = board.draw();
		System.out.println("Row: " + tile3.row + " Col: " + tile3.col);
		board.printBoundaries(tile);
		board.printBoundaries(tile1);
		board.printBoundaries(tile2);
		board.printBoundaries(tile3);

		board.randomizeGrid();
		board.initBag();
	}

	/*
	 * TODO: Possibly instead of making a true/false 2D array would it be better
	 * to make a stack instead? We can just push on the tile that is now in use.
	 * Then check to see if its in the stack when removing another tile from the
	 * grid. Just a suggestion. -Carolyn
	 */

}
