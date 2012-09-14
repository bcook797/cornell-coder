package mars.rover;

import junit.framework.TestCase;

public class RoverTest extends TestCase {
	Plateau a = new Plateau(5,5);
	Rover b = new Rover(1,2,"N");
	Rover c = new Rover(3,3,"E");
	
	public void testRovers() {
		assertNotNull(b);
		assertNotNull(c);
	}
	
	public void testGetX() {
		assertEquals("Expected: 1 Actual: " + b.getX(), 1, b.getX());
		assertEquals("Expected: 3 Actual: " + c.getX(), 3, c.getX());
	}
	
	public void testGetY() {
		assertEquals("Expected: 2 Actual: " + b.getY(), 2, b.getY());
		assertEquals("Expected: 3 Actual: " + c.getY(), 3, c.getY());
	}
	
	public void testSetX() {
		b.setX(2, a.getWidth());
		c.setX(4, a.getWidth());
		assertEquals("Expected: 2 Actual: " + b.getX(), 2, b.getX());
		assertEquals("Expected: 4 Actual: " + c.getX(), 4, c.getX());
		
		// Attempt to set rovers off of plateau
		b.setX(-1, a.getWidth());
		c.setX(6, a.getWidth());
		assertEquals("Expected: 2 Actual: " + b.getX(), 2, b.getX());
		assertEquals("Expected: 4 Actual: " + c.getX(), 4, c.getX());
		
		reset();
	}
	
	public void testSetY() {
		b.setY(2, a.getHeight());
		c.setY(4, a.getHeight());
		assertEquals("Expected: 2 Actual: " + b.getY(), 2, b.getY());
		assertEquals("Expected: 4 Actual: " + c.getY(), 4, c.getY());
		
		// Attempt to set rovers off of plateau
		b.setY(-1, a.getWidth());
		c.setY(6, a.getWidth());
		assertEquals("Expected: 2 Actual: " + b.getY(), 2, b.getY());
		assertEquals("Expected: 4 Actual: " + c.getY(), 4, c.getY());
		
		reset();
	}
	
	public void testGetHeading() {
		assertEquals("Expected: N Actual: " + b.getHeading(), "N", b.getHeading());
		assertEquals("Expected: E Acutal: " + c.getHeading(), "E", c.getHeading());
	}
	
	public void testSetHeading() {
		b.setHeading("W");
		c.setHeading("S");
		assertEquals("Expected: W Actual: " + b.getHeading(), "W", b.getHeading());
		assertEquals("Expected: S Acutal: " + c.getHeading(), "S", c.getHeading());
		
		reset();
	}
	
	public void testRotate() {
		b.rotate("R");
		assertEquals("Expected: E Actual: " + b.getHeading(), "E", b.getHeading());
		c.rotate("L");
		assertEquals("Expected: N Actual: " + c.getHeading(), "N", c.getHeading());
	}
	
	public void testMovements() {
		assertNull(b.getMovements());
		assertNull(c.getMovements());
		
		b.setMovements("LMLMLMLMM");
		c.setMovements("MMRMMRMRRM");
		assertEquals("Expected: LMLMLMLMM Actual: " + b.getMovements(),"LMLMLMLMM", b.getMovements());
		assertEquals("Expected: MMRMMRMRRM Actual: " + c.getMovements(),"MMRMMRMRRM", c.getMovements());		
	}
	
	/*
	 * Reset variables to initial values after manipulations
	 */
	public void reset() {
		b = new Rover(1,2,"N");
		c = new Rover(3,3,"E");
	}	
}
