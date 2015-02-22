


public class Player {

	int totalCash;
	String name;
	
	int pid;  				// game session unique id
	Tile [] hand;
	Tile initTile;
	int companies_started;
	int companies_merged;
	int number_of_turns;
	int money;
	int [] shares;
	static int numHand = 0; //The number of Tiles in the hand.

	boolean my_turn;
	boolean my_merge_turn;
	
	public Player(String name) {
		this.name = name;
		this.totalCash = 6000;
	}
	
	public Player(String name, Tile initTile) {
		this.name = name;
		this.totalCash = 6000;
		this.initTile = initTile;
		this.hand = new Tile[6];
	}
	
	public void addHand(Tile tile) {
		if(numHand >= 6) {
			System.out.println("Cheating is prohibited");
			return;
		}
		hand[numHand] = tile;
		numHand++;
	}
	
	public int getCash() {
		
		return totalCash;
	}
	
	public String getName() {
		return name;
	}
	
	public Tile getInitTile() {
		return initTile;
	}
	
	public String printTile(Tile tile) {
		return tile.row + "" + tile.col;
	}
	
	/**
	 * Do something with the board.
	 * @param loc - the location in the hand.
	 */
	public void placeTile(int loc) {
		
	}
	
	public void removeTile(int loc) {
		hand[loc] = null;
		numHand--;
	}
	
	/**
	 * Returns -1 if tile1 is smaller. Returns 1 if tile 1 is bigger.
	 * @param tile1
	 * @param tile2
	 * @return
	 */
	public int compareTo(Tile tile1, Tile tile2) {
		
		if(tile1.getCol() < tile2.getCol()) {
			return -1;
		}
		else if(tile1.getCol() == tile2.getCol()) {
			if(tile1.getRow().compareTo(tile2.getRow()) < 0) {
				return -1;
			}
			else {
				return 1;
			}
		}
		else {/*(tile1.getCol() > tile2.getCol())  */
			return 1;
		}
		
	}
	
	public void stockAquisition() {
		int numStock = 0;
		while(numStock != 3) {
			buyStock();
		}
	}
	
	/**
	 * TODO: have a switch statement of stocks.
	 * @return
	 */
	public int buyStock() {
		return 0;
	}
	
	
	

}