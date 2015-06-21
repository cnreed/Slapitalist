import static org.junit.Assert.*;

import org.junit.Test;


public class CompanyTesting {
	Grid board = new Grid(9, 12);
	Grid lBoard = new Grid(9, 12);
	Game game= new Game(9, 12, true);
	Company comp = new Company("First", 0, null, 0, 0);
	Company test = new Company("Test", 0, null, 3, 0);
	Company loop = new Company("Loop", 0, null, 0, 0);
	Company Ltest = new Company("Ltest", 0, null, 0, 0);
	Company sameSize1 = new Company("SameSize1", 0, null, 2, 0);
	Company sameSize2 = new Company("SameSize2", 0, null, 2, 0);

	@Test
	public void testSetCompany() {
//		board.print();
		assertTrue("Company names is actually " + comp.getCompanyName(), 
				comp.getCompanyName().equals("First"));
		
		
	}
	@Test
	public void addCompany() {
		board.print();
		Tile tile = board.getTile(0,0);
		comp.addTile(tile);
		
		assertEquals("Company size is actually: " + comp.size(), comp.size(), 1);
	}
	@Test
	public void setSafe() {
		comp.isSafe = true;
		assertTrue("The company is safe", comp.isSafe);
	}
	
	@Test
	public void onBoard() {
		System.out.println();
		board.print();
		System.out.println();
		Tile tile = board.getTile(0,0);
		comp.addTile(tile);
		tile.statusUpdate(2);
		board.print();
		assertTrue("The tile status is: " + tile.getStatus(),
				tile.getStatus().equals("ONBOARD"));
	}
	
	@Test
	public void testSetUnplayable() {
		
		Tile tile = null;
		System.out.println("Testing setUnplayable: \n");
		for(int i = 0; i < 11; i++) {
			tile = board.getTile(0, i);
			test.addTile(tile);
			tile.statusUpdate(2);
			tile.subStatusUpdate(5);
			tile.ownerCompany = test;
		}
		board.print();
		game.setUnplayable(board);
//		assertTrue(test.isSafe);
		tile = board.getTile(1,0);
		String status= tile.getSubStatus();
		System.out.println("Next testing: \n");
		assertTrue(status.equals("QUARANTINED"));
		for(int i = 0; i < 11; i++) {
			tile = board.getTile(2, i);
			loop.addTile(tile);
			tile.statusUpdate(2);
			tile.subStatusUpdate(5);
			tile.ownerCompany = loop;
		}
		game.setUnplayable(board);
		board.print();
	}
	
	@Test
	public void testLShapeSetUnplayable() {
		System.out.println("L Shaped test Comany: \n");
		
		Tile tile;
		System.out.println("Long ways: \n");
		for(int i = 0; i < 11; i++) {
			tile = lBoard.getTile(0, i);
			Ltest.addTile(tile);
			tile.statusUpdate(2);
			tile.subStatusUpdate(5);
			tile.ownerCompany = Ltest;
		}
		lBoard.print();
		System.out.println("Short ways: \n");
		for(int  i = 0; i < 4; i++) {
			tile = lBoard.getTile(i, 0);
			tile.statusUpdate(2);
			tile.subStatusUpdate(5);
			tile.ownerCompany = Ltest;
		}
		lBoard.print();
		System.out.println("L Shaped Company set: ");
		game.setUnplayable(lBoard);
		tile = lBoard.getTile(1, 1);
		System.out.println("Tile substatus is: " + tile.getSubStatus());
		game.logPrintTileStatus(lBoard);
		assertTrue(tile.getSubStatus().equals("QUARANTINED"));
	}
	
	@Test
	public void testMergeCompsOfSameSize() {
		
		
	}
	
	

}
