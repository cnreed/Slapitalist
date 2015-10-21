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
		Board board = new Board(9, 12);
		WeightBoard weight = new WeightBoard(board, 0.1);
		weight.printBoard();
	}
}
