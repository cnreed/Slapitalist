
public class Player {

	int totalCash;
	String name;
	
	int pid;  			// game session unique id
	Tile [] hand;
	int companies_started;
	int companies_merged;
	int number_of_turns;
	int money;
	int [] shares;

	boolean my_turn;
	boolean my_merge_turn;
	
	public Player(String name) {
		this.name = name;
		this.totalCash = 6000;
	}
	
	public int getCash() {
		
		return totalCash;
	}
	
	
	

}