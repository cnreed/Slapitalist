import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {

	private static Scanner scan;
	private static int numPlayers;
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;
	private static Grid board;
	private static int x, y;

	private static Player[] players;
	private static int whosTurn;

	private static boolean gameInPlay = true;

	public Game(int x, int y) {

		scan = new Scanner(System.in);

		/* initialize board */
		Grid board = new Grid(x, y);

		/* initialize players */
		players = getPlayers();

		/* figure out who goes first */
		int firstPlayer = whoIsFirst(board);
		whosTurn = firstPlayer;
		System.out.println(players[firstPlayer].getName() + " goes first!");

		/* Rearrange turn sequence so first player is first in array */
		players = rearrangePlayerSequence(firstPlayer);

		/* draw tiles for each player */
		drawStartingTiles(board);

		/* initialize and build payCliff for companies */
		initCompanies();

		while (gameInPlay) {

			getMove(whosTurn, board);
			whosTurn++;
			whosTurn = whosTurn % numPlayers;
		}

		scan.close();
	}

	private boolean getMove(int playerIndex, Grid board) {
		Player player = players[playerIndex];
		board.print();
		System.out.println("Player " + player.getName());
		System.out.println("\nWhat tile would you like to place?");
		player.showHand();

		int choice = scan.nextInt() - 1;

		boolean playable = playTile(player.hand[choice]);
		System.out.println("Playing " + player.hand[choice].toString());

		if (playable) {
			System.out.println(player.getName()
					+ " successfully placed down tile "
					+ player.hand[choice].toString());
			Tile newTile = board.bagPop();
			player.hand[choice] = newTile;
		}
		return true;
	}

	private boolean playTile(Tile tileInPlay) {
		/*
		 * this casting back and forth between string and index for
		 * setting/getting status is a little clunky, we should consider
		 * revising.
		 */

		if (tileInPlay.getStatus().equals("INHAND")) {
			tileInPlay.setOwnerPlayer(players[whosTurn]);
			tileInPlay.statusUpdate(2);
		}
		return true;
	}

	private Player[] rearrangePlayerSequence(int first) {
		Player[] tempPlayers = new Player[players.length];

		for (int i = 0; i < players.length; i++) {
			tempPlayers[i] = players[(first + i) % players.length];
		}

		return tempPlayers;
	}

	private void drawStartingTiles(Grid board) {
		for (Player player : players) {
			Player.numHand = 0;
			for (int i = 0; i < player.handSize; i++) {
				Tile tempTile = board.bagPop();
				player.addHand(tempTile);
			}
		}
	}

	private int whoIsFirst(Grid board) {
		int pLength = players.length;
		Tile tempTile;
		ArrayList<Double> distances = new ArrayList<Double>();

		for (int i = 0; i < pLength; i++) {
			tempTile = board.bagPop();
			playTile(tempTile);

			double pythag = Math.sqrt(tempTile.col * tempTile.col
					+ tempTile.row * tempTile.row)
					+ tempTile.col;
			distances.add(pythag);

			// System.out.print(players[i].name);
			// System.out.printf(" %.2f \n", distances.get(i));
		}
		return distances.indexOf(Collections.min(distances));
	}

	private static Player[] getPlayers() {

		boolean gate = false;
		while (!gate) {
			System.out.print("Enter the number of Players (Max 6): ");
			numPlayers = scan.nextInt();
			if (numPlayers > 1 && numPlayers < 7) {
				gate = true;
			}
		}

		players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Name of player " + (i + 1) + ": ");
			String name = scan.next();
			players[i] = new Player(name, board);
		}

		return players;
	}

	static void initCompanies() {

		/* Tier 0 */
		Rahoi = new Company("Rahoi", 0, "\u001B[34m", 0, 0);
		Tower = new Company("Tower", 0, "\u001B[32m", 0, 1);
		/* Tier 1 */
		American = new Company("American", 1, "\u001B[36m", 0, 2);
		Worldwide = new Company("Worldwide", 1, "\u001B[35m", 0, 3);
		Festival = new Company("Festival", 1, "\u001B[31m", 0, 4);
		/* Tier 2 */
		Continental = new Company("Continental", 2, "\u001B[33m", 0, 5);
		Imperial = new Company("Imperial", 2, "\u001B[37m", 0, 6);

		System.out.println("All Companies Initialized");
	}
}
