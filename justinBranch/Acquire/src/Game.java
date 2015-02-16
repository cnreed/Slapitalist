import java.util.Scanner;

public class Game {

	private static int numPlayers;

	public static void main(String[] args) {
		getPlayers();
		Grid board = new Grid(12, 9);
		board.initialize();
		board.print();
	}

	private static void getPlayers() {

		Scanner scan = new Scanner(System.in);

		boolean gate = false;
		while (!gate) {
			System.out.print("Enter the number of Players (Max 6): ");
			numPlayers = scan.nextInt();
			if (numPlayers > 1 && numPlayers < 7) {
				gate = true;
			}
		}

		Player[] players = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			System.out.print("Name of player " + i + ": ");
			String name = scan.next();
			players[i] = new Player(name);
		}
		for (Player person : players) {
			System.out.println(person.name + " Player object created");
		}
	}

}
