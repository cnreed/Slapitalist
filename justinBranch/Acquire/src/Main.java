import java.util.ArrayList;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class Main {

	Grid board = new Grid(9, 12);
	static int playercount = 0;
	private static int safeSize = 11;
	private static ArrayList<Player> Players = new ArrayList<>();

	public static void main(String[] args) {
		
		if(!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {

			PatternLayout consoleLayout = new PatternLayout("[%p] %t: %m%n");
			ConsoleAppender consoleAppender = new ConsoleAppender(consoleLayout);
			consoleAppender.setThreshold(Level.DEBUG);
			Logger.getRootLogger().addAppender(consoleAppender);

		}

		Logger.getRootLogger().setLevel(Level.DEBUG); 
		Logger log = Logger.getLogger(java.sql.Driver.class);

		
		log.debug("Starting a new Game!");
		Game game = new Game(9, 12);
		// Grid board = new Grid(9, 12);
		// board.initialize();
		// board.randomizeGrid();
		// board.initBag();
		//

		// Players.add(carolyn);
		// Players.add(matt);
		// Players.add(justin);
		// board.print();
		// System.out.println(carolyn.getName() + " "
		// + carolyn.printTile(carolyn.getInitTile()));
		// System.out.println(justin.getName() + " "
		// + justin.printTile(justin.getInitTile()));
		// System.out.println(matt.getName() + " "
		// + matt.printTile(matt.getInitTile()));
		//

		//
		// for (int i = 0; i <= 7; i++) {
		// // System.out.println("Hello i:" + i);
		// Tile tile = board.bagPop();
		// carolyn.addHand(tile);
		// }

	}

	public static int getSafeSize() {
		return safeSize;
	}

	public static Player getPlayer(String name) {
		for (Player player : Players) {
			if (player.name.equals(name)) {
				return player;
			}
		}
		return null;
	}

}