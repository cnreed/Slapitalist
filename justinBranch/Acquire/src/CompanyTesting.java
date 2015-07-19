import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompanyTesting {
	Grid board = new Grid(9, 12);
	Grid lBoard = new Grid(9, 12);
	Grid unplayBoard = new Grid(9, 12);
	Game game = new Game(9, 12, true);
	Company comp = new Company("First", 0, null, 0, 0);
	Company test = new Company("Test", 0, null, 3, 0);
	Company loop = new Company("Loop", 0, null, 0, 0);
	Company Ltest = new Company("Ltest", 0, null, 0, 0);
	Company sameSize1 = new Company("SameSize1", 0, null, 2, 0);
	Company sameSize2 = new Company("SameSize2", 0, null, 2, 0);
	Company diffSame1 = new Company("diffSame1", 0, null, 4, 0);
	Company different = new Company("different", 0, null, 3, 0);
	Company diffSame2 = new Company("diffSame2", 0, null, 4, 0);
	Company largeComp1 = new Company("largeComp1", 0, null, 0, 0);
	Company largeComp2 = new Company("largeComp2", 0, null, 0, 0);

	// @Test
	// public void testSetCompany() {
	// // board.print();
	// assertTrue("Company names is actually " + comp.getCompanyName(), comp
	// .getCompanyName().equals("First"));
	//
	// }
	//
	// @Test
	// public void addCompany() {
	// board.print();
	// Tile tile = board.getTile(0, 0);
	// comp.addTile(tile);
	//
	// assertEquals("Company size is actually: " + comp.size(), comp.size(), 1);
	// }
	//
	// @Test
	// public void setSafe() {
	// comp.isSafe = true;
	// assertTrue("The company is safe", comp.isSafe);
	// }
	//
	// @Test
	// public void onBoard() {
	// System.out.println();
	// board.print();
	// System.out.println();
	// Tile tile = board.getTile(0, 0);
	// comp.addTile(tile);
	// tile.statusUpdate(2);
	// board.print();
	// assertTrue("The tile status is: " + tile.getStatus(), tile.getStatus()
	// .equals("ONBOARD"));
	// }
	//
	// @Test
	// public void testSetUnplayable() {
	//
	// Tile tile = null;
	// System.out.println("Testing setUnplayable: \n");
	// for (int i = 0; i < 11; i++) {
	// tile = board.getTile(0, i);
	// test.addTile(tile);
	// tile.statusUpdate(2);
	// tile.subStatusUpdate(5);
	// tile.ownerCompany = test;
	// }
	// board.print();
	// game.setUnplayable(board);
	// // assertTrue(test.isSafe);
	// tile = board.getTile(1, 0);
	// String status = tile.getSubStatus();
	// System.out.println("Next testing: \n");
	// assertTrue(status.equals("QUARANTINED"));
	// for (int i = 0; i < 11; i++) {
	// tile = board.getTile(2, i);
	// loop.addTile(tile);
	// tile.statusUpdate(2);
	// tile.subStatusUpdate(5);
	// tile.ownerCompany = loop;
	// }
	// game.setUnplayable(board, test);
	// board.print();
	// }

	// @Test
	// public void testLShapeSetUnplayable() {
	// System.out.println("L Shaped test Comany: \n");
	//
	// Tile tile;
	// System.out.println("Long ways: \n");
	// for (int i = 0; i < 11; i++) {
	// tile = lBoard.getTile(0, i);
	// Ltest.addTile(tile);
	// tile.statusUpdate(2);
	// tile.subStatusUpdate(5);
	// tile.ownerCompany = Ltest;
	// }
	// lBoard.print();
	// System.out.println("Short ways: \n");
	// for (int i = 0; i < 4; i++) {
	// tile = lBoard.getTile(i, 0);
	// tile.statusUpdate(2);
	// tile.subStatusUpdate(5);
	// tile.ownerCompany = Ltest;
	// }
	// lBoard.print();
	// System.out.println("L Shaped Company set: ");
	// game.setUnplayable(lBoard);
	// tile = lBoard.getTile(1, 1);
	// System.out.println("Tile substatus is: " + tile.getSubStatus());
	// game.logPrintTileStatus(lBoard);
	// assertTrue(tile.getSubStatus().equals("QUARANTINED"));
	// }
	//
	// @Test
	// public void testMergeCompsOfSameSize() {
	// ArrayList<Company> companyList = new ArrayList<Company>();
	// companyList.add(sameSize1);
	// companyList.add(sameSize2);
	// Company largest = game.findLargest(companyList);
	// assertTrue(largest.equals(sameSize2));
	// }
	//
	// @Test
	// public void testMergeCompsofDuplicateSizes() {
	// ArrayList<Company> companyList = new ArrayList<Company>();
	// companyList.add(sameSize1);
	// companyList.add(sameSize2);
	// companyList.add(diffSame1);
	// companyList.add(different);
	// companyList.add(diffSame2);
	// Company largest = game.findLargest(companyList);
	// assertTrue(largest.equals(diffSame1));
	//
	// }

	@Test
	public void testAllTilesUnplayable() {

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 12; j++) {
				Tile tile = unplayBoard.getTile(i, j);
				largeComp1.addTile(tile);
				tile.statusUpdate(2);
				tile.subStatusUpdate(5);
				tile.ownerCompany = largeComp1;
				game.tilesLeftOnBoard--;

			}
		}
		System.out.println("First Company Done!");
		unplayBoard.print();
		System.out.println();
		for (int x = 2; x < 9; x++) {
			for (int y = 0; y < 12; y++) {
				Tile tile = unplayBoard.getTile(x, y);
				largeComp2.addTile(tile);
				tile.statusUpdate(2);
				tile.subStatusUpdate(5);
				tile.ownerCompany = largeComp2;
				game.tilesLeftOnBoard--;
			}
		}

		int count = game.tilesLeftOnBoard--;
		unplayBoard.print();
		game.setUnplayable(unplayBoard, largeComp1);
		// game.logPrintTileStatus(]un);
		System.out.println("Tile: " + board.getTile(1, 0).toString());
		System.out.println("Tile: " + board.getTile(3, 1).toString());
		assertTrue("Tile's substatus is" + board.getTile(1, 0).getStatus(),
				unplayBoard.getTile(1, 0).getSubStatus().equals("UNPLAYABLE"));
		boolean bool = game.allTilesUnplayable(unplayBoard, count);
		assertTrue(bool);

	}
}
