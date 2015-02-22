
import java.util.Scanner;
public class Game {

	private static int numPlayers;
	private static Company Rahoi, Imperial, Worldwide, Tower, American,
			Festival, Continental;

	public static void main(String[] args) {
		getPlayers();
		Grid board = new Grid(9, 12);
		board.initialize();
		initCompanies();
		board.print();
		System.out.println();

		/* Testing List of shareholders per company */
		Rahoi.insertShareHolder("Jon", 400);
		Rahoi.insertShareHolder("Carolyn", 700);
		Rahoi.insertShareHolder("Justin", 600);
		Rahoi.insertShareHolder("Matthew", 500);

		System.out.println("Majority: " + Rahoi.getMajority());
		System.out.println("Minority: " + Rahoi.getMinority());
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
			System.out.print("Name of player " + (i + 1) + ": ");
			String name = scan.next();
			players[i] = new Player(name);
		}
		for (Player person : players) {
			System.out.println(person.name + " Player object created");
		}
		scan.close();
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
