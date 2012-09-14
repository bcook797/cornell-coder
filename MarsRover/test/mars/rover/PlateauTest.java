package mars.rover;

import junit.framework.TestCase;

public class PlateauTest extends TestCase {
	
	Plateau a = new Plateau(5,5);
	Rover b = new Rover(1,2,"N");
	Rover c = new Rover(3,3,"E");
	
	public void testPlateau() {
		assertNotNull(a);		
	}		
	
	public void testGetWidth() {
		assertEquals("Expected: 5 Actual: " + a.getWidth(), 5, a.getWidth());
	}
	
	public void testGetHeight() {
		assertEquals("Expected: 5 Actual: " + a.getHeight(), 5, a.getHeight());
	}
	
	public void testAddRover() {
		assertEquals(a.getRovers().size(),0);
		a.addRover(b);
		assertEquals("Expected: " + b + "Actual: " + a.getRovers().get(0), b, a.getRovers().get(0));
		a.addRover(c);
		assertEquals("Expected: " + c + "Actual: " + a.getRovers().get(1), c, a.getRovers().get(1));
		assertEquals("Expected: 2 Actual: " + a.getRovers().size(), 2, a.getRovers().size());
	}
	
	public void testMoveRovers() {
		b.setMovements("LMLMLMLMM");
		c.setMovements("MMRMMRMRRM");
		a.getRovers().add(b);
		a.getRovers().add(c);
		a.moveRovers();		
		assertEquals("Expected: N Actual: " + b.getHeading(), "N", b.getHeading());
		assertEquals("Expected: E Acutal: " + c.getHeading(), "E", c.getHeading());
		assertEquals("Expected: 1 Actual: " + b.getX(), 1, b.getX());
		assertEquals("Expected: 5 Actual: " + c.getX(), 5, c.getX());
		assertEquals("Expected: 3 Actual: " + b.getY(), 3, b.getY());
		assertEquals("Expected: 1 Actual: " + c.getY(), 1, c.getY());
	}

}
