import java.util.ArrayList;

public class Main {

	Grid board = new Grid(9, 12);
	static int playercount = 0;
	private static int safeSize = 11;
	private static ArrayList<Player> Players = new ArrayList<>();

	public static void main(String[] args) {

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