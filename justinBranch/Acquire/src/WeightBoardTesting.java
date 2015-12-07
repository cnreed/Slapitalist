import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.Test;

/**
 * 
 */

/**
 * @author scout
 *
 */
public class WeightBoardTesting {

	@Test
	public void test() {

		if (!Logger.getRootLogger().getAllAppenders().hasMoreElements()) {

			PatternLayout consoleLayout = new PatternLayout("[%p] %t: %m%n");
			ConsoleAppender consoleAppender = new ConsoleAppender(consoleLayout);
			consoleAppender.setThreshold(Level.DEBUG);
			Logger.getRootLogger().addAppender(consoleAppender);

		}

		Logger.getRootLogger().setLevel(Level.DEBUG);
		Logger log = Logger.getLogger(java.sql.Driver.class);
		log.debug("Starting Tests");
		Board board = new Board(3, 6);
		float num = 0.1f;
		WeightBoard weight = new WeightBoard(board, num);
		weight.printBoard();

		Tile tile = board.getTile(0, 0);
		System.out.println(tile.toString());
		tile.statusUpdate(2);
		weight.recursiveRecalculate(board, tile, tile);
		// weight.recalculateSurroundings(board, tile);
		weight.printBoard();
		tile = board.getTile(0, 1);
		tile.statusUpdate(2);
		System.out.println(tile.toString());
		// weight.recalculate(board);
		// // weight.recalculateSurroundings(board, tile);
		weight.printBoard();
		System.out.println("Wiping Board Clean: ");
		weight.boardWipe();
		System.out.println("Clean Board");
		weight.printBoard();
		weight.recursiveRecalculate(board, tile, tile);
		System.out.println();
		weight.printBoard();
		tile = board.getTile(1, 1);
		System.out.println(tile.toString());
		weight.boardWipe();
		tile.statusUpdate(2);
		weight.recursiveRecalculate(board, tile, tile);
		weight.printBoard();
		board.print();
	}
}
