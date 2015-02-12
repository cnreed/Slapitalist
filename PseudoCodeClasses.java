class Company {

	public static int Cid; //company number ID;
	private static final String company_Name; //name of company intialized 
	private static int company_Size = company_tiles.size();    //company_tiles.length(), can't be 1, always starts as 2 or not on board
	private static Color company_Color; //specific color associated with the company
	private static ArrayList<Tile> company_tiles; //all tiles owned by the company
	private static int safe_size;
	//Player ownership
	private static playerNode LinkedList share_Holders // Head: largest Tail: smallest

		private class Node playerNode{
			String playerName;
			int shareCount;
			playerNode next;
			playerNode prev;

			//implement getters and setters for Linked List here. I don't want to.
		}

	private static final int company_Tier;

	//board state
	bool is_safe;    	//start false
	bool game_endable; 	//start false
	bool on_board; 		//start false

	//METHODS
	int get_Cid(){
		return this.Cid;
	}

	String get_Name(){
		return this.company_Name;
	}

	int get_size(){
		return this.company_Size;
	}

	void increment_size(){
		this.company_Size++;
		if(company_Size==safe_size){is_safe=true;} //makes company safe if safe_size is achieved
	}


	int get_Color(){
		return this.company_Color; // this will be a hex value but it can be returned as an int i think
	}

	String get_Majority(){
		return this.shareHolders_get(0).playerName; // this will just the String from the node at position 0
	}

	String get_Minority(){
		return this.shareHolders_get(1).playerName;
	}

	void set_safe(){
		this.is_safe=true;
	}

	void set_endable(){
		this.game_endable = true;
	}

	void set_onboard(){
		this.on_board = true;
	}

	void dissolve(){ 			// drops all backend values to 0, which cascades to prices, etc.
		this.on_board = false;  // doesn't affect people that have stock 
		this.is_safe = false;
		this.game_endable = false;
		this.company_Size = 2;

	}


}

class Player {

	int pid;  			// game session unique id
	String player_Name;
	Tile [] hand;
	int companies_started;
	int companies_merged;
	int number_of_turns;
	int money;
	int [6] shares;

	bool my_turn;
	bool my_merge_turn;

}

class Board {

	int x_size;
	int y_size;

	Tile[x_size][y_size] board; //actual board entity;
	int turns; // total turns across all players
	int merges; // total merges across all players
	int companies_started;  //total for game - started and re-started

}

class Tile {

	String ownerCompany; // what company owns it
	String ownerPlayer;  // what player owns it

	boolean top; 	// free above?
	boolean bottom; // free below?
	boolean left;   // free left?
	boolean right;  // free right?

	public enum tileState {
    	INBAG, INHAND, ONBOARD, QUARANTINED, UNPLAYABLE
	}

	boolean safe = getCompany(ownerCompany).is_safe; // so the whole company's tiles will have the same response

}