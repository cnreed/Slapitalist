import java.util.ArrayList;


public class Main {
	
	static int playercount = 0;
	private static int safeSize = 11;
	private static ArrayList<Player> Players = new ArrayList<>();
	
	
	public static void main(String[] args) {
		
		
	
	
		Player carolyn = new Player("Carolyn");
		playercount++;
		Player matt = new Player("Matt");
		playercount++;
		Player justin = new Player("Justin");
		
		Players.add(carolyn);
		Players.add(matt);
		Players.add(justin);
		
	
		
		
		
		
	}
	public static int getSafeSize() {
		return safeSize;
	}

	public static Player getPlayer(String name){
		for(Player player : Players){
			if(player.name.equals(name)){
				return player;
			}
		}
		return null;
	}

}