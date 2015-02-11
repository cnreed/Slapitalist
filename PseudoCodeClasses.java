class Company {

	int Cid; //company number ID;
	String company_Name; //name of company intialized 
	int company_Size;    //company_tiles.length(), can't be 1
	Color company_Color; //specific color associated with the company
	Tile[] company_Tiles; //all tiles owned by the company

	//Player ownership
	playerNode LinkedList share_Holders // Head: largest Tail: smallest

		Node playerNode{
			String playerName;
			int shareCount;
			playerNode next;
			playerNode prev;
		}

	int company_Tier;

	//board state
	bool is_safe;    	//start false
	bool game_endable; 	//start false
	bool on_board; 		//start false

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