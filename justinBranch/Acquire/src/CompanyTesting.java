import static org.junit.Assert.*;

import org.junit.Test;


public class CompanyTesting {
	Grid board = new Grid(6, 8);
	
	Company comp = new Company("First", 0, null, 0, 0);
	

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
	
	

}
