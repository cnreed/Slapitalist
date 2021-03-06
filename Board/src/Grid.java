import java.util.LinkedList;
import java.util.Random;


public class Grid {
	
	String [] stringArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	Tile [][] grid = new Tile[9][12];
	boolean[][] inBag = new boolean[9][12];
	LinkedList<Tile>bag = new LinkedList<Tile>();
	Tile[][] randArray= grid;

	
	
	public class Company {
		
		String name;
		int size;
		
		public Company (String name) {
			this.name = name;
			this.size = 2;
		}	
		
		public int updateSize () {
			size++;
			
			return size;
		}
		
		public int getSize() {
			return size;
		}
	}
	
//	/**
//	 * Individual Tiles for the game board.
//	 * @author Carolyn
//	 *
//	 */
//	public class Tile {
//		String row;
//		int col;
//		boolean top;
//		boolean bottom;
//		boolean left;
//		boolean right;
//		
//		public Tile(String row, int col) {
//			this.row = row;
//			this.col = col;
//			top = bottom = left = right =true;
//		}
//	}
	
	/**
	 * Initializes the board on start up.
	 */
	public void initialize() {
		
		System.out.println();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j <grid[i].length; j++) {
				String row = stringArray[i];
				Tile tile = new Tile(row, j);
				grid[i][j] = tile;
				inBag[i][j] = true;
			}
		}
	}
	/**
	 * Prints the board.
	 */
	public void print() {
		for(int i = 0; i <grid.length; i++) {
			for(int j = 0; j <grid[i].length; j++) {
				System.out.print(grid[i][j].row + "" + grid[i][j].col + ", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Prints a specific Tile array
	 * @param array
	 */
	private void print(Tile[][] array) {
		for(int i = 0; i <array.length; i++) {
			for(int j = 0; j <array[i].length; j++) {
				System.out.print(array[i][j].row + "" + array[i][j].col + ", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Randomly selects a tile from the grid.
	 * @return
	 */
	public Tile draw() {
		Random rand = new Random();
		Tile tile = null;
		int x = rand.nextInt(9);
		int y = rand.nextInt(12);
		if(!inBag[x][y]) {
			return null;
		}
		tile = grid[x][y];
		inBag[x][y] = false;
		checkBoundaries(tile, x, y);
		return tile;
	}
	
	/**
	 * Checks to see if the tile is on the edge of the board. If it is,
	 * then set corresponding possible moves to false.
	 * @param tile
	 * @param x
	 * @param y
	 */
	public void checkBoundaries(Tile tile, int x, int y) {
		if(x <= 0) {
			tile.top = false;
		}
		if(y <= 0) {
			tile.left = false;
		}
		if(x >= 11) {
			tile.bottom = false;
		}
		if(y >= 11) {
			tile.right = false;
		}
	}
	
	/**
	 * Prints if the corresponding move is possible according to the limits of the
	 * board.
	 * @param tile
	 */
	public void printBoundaries(Tile tile) {
		System.out.print("Top: " + tile.top + " ");
		System.out.print("Right: " + tile.right + " ");
		System.out.print("Bottom: " + tile.bottom + " ");
		System.out.println("Left: " + tile.left);
	}
	
	/**TODO: Actually make a stack or queue of the bag
	 * 
	 */
	public void randomizeGrid() {
		
		Random rand = new Random();
		int i, j;
		for(i = 0; i < grid.length-1; i++) {
			for(j = 0; j < grid[i].length-1; j++) {
				int x = rand.nextInt(i+1);
				int y = rand.nextInt(j+1);
				
				Tile temp = randArray[i][j];
				randArray[i][j] = randArray[x][y];
				randArray[x][y] = temp;
			}
		}
//		print(randArray);
		
	}
	
	public void initBag() {
		
		for(int i = 0; i < randArray.length; i++) {
			for(int j = 0; j < randArray[i].length;j++) {
				Tile tile = randArray[i][j];
				bag.add(tile);
			}
		}
		
	}
	
	/**
	 * This is a testing Main.
	 * @param args
	 */
	public static void main (String [] args) {
		Grid board = new Grid();
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
	 * TODO: Possibly instead of making a true/false 2D array would it be better to make a
	 * stack instead? We can just push on the tile that is now in use. Then check to see
	 * if its in the stack when removing another tile from the grid. Just a suggestion.
	 * -Carolyn
	 */
	
}

