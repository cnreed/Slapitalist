//package Testing;
//
//import ArtificialIntelligence.WeightBoard;
//import Game.Game;
//import org.apache.log4j.ConsoleAppender;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PatternLayout;
//import org.junit.Test;
//
///**
// *
// */
//
///**
// * @author scout
// *
// */
//public class WeightBoardTesting {
//
//	/**
//	 * I know I'm doing this wrong. Make actual unit tests
//	 */
//	@Test
//	public void test() {
//
//		if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
//
//			PatternLayout consoleLayout = new PatternLayout("[%p] %t: %m%n");
//			ConsoleAppender consoleAppender = new ConsoleAppender(consoleLayout);
//			consoleAppender.setThreshold(Level.DEBUG);
//			Logger.getRootLogger().addAppender(consoleAppender);
//
//		}
//
//		Logger.getRootLogger().setLevel(Level.DEBUG);
//		Logger log = Logger.getLogger(java.sql.Driver.class);
//		log.debug("Starting Tests");
//		Game.Board board = new Game.Board(4, 7);
//		float num = 0.1f;
//		WeightBoard weight = new WeightBoard(board, num);
//		weight.printBoard();
//
//		Game.Tile tile = board.getTile(0, 0); // A1
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(0, 1); // A2
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		System.out.println(tile.toString());
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(1, 1); // B2
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 5); // C6
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 3); // C4
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(1, 2); // B3
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 1); // C2
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 2); // C3
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 0); // C1
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//
//		tile = board.getTile(2, 4); // C4
//		System.out.println(tile.toString());
//		tile.statusUpdate(2);
//		weight.tilePlaced(tile);
//		weight.printSurroundCountBoard();
//		board.print();
//		System.out.println("Weight");
//		weight.printBoard();
//	}
//}
