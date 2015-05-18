import static org.junit.Assert.*;

import org.junit.Test;


public class CompanyTesting {
	Grid board = new Grid(9, 12);
	Company comp = new Company("First", 0, null, 0, 0);
	

	@Test
	public void testSetCompany() {
		
		assertTrue("Company names is actually " + comp.getCompanyName(), 
				comp.getCompanyName().equals("First"));
		
		
	}
	@Test
	public void addCompany() {
		Tile tile = board.getTile(0,0);
		comp.addTile(tile);
		
		assertEquals("Company size is actually: " + comp.size(), comp.size(), 1);
	}
	
	Game game = new Game(9, 12);
	
	@Test
	public void players() {
		
	}

}
