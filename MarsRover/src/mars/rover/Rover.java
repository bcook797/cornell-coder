package mars.rover;

// Representation of Mars Rover
public class Rover {
	private int x;
	private int y;
	private String heading;
	private String movements;	
	
	/*
	 * Constructor: Create an instance of rover with an x,y coordinates and heading
	 */
	public Rover(int x, int y, String heading) {
		this.x = x;
		this.y = y;
		this.heading = heading;
	}	
	
	/*
	 * Rotate rover left or right based on given direction 
	 */
	public void rotate(String direction) {
		if(!direction.equals("F")) {
			String key = this.getHeading() + direction;
			String head = Plateau.DIRECTIONS.get(key);
			this.setHeading(head);
		} else {
			System.out.println("Rotation Error: Move Aborted");
		}
	}
	
	/*
	 * Set the rover x-coordinate if value is within plateau borders
	 */
	public void setX(int x, int edge) {
		if(x >= 0 && x <= edge) {
			this.x = x;
		} else {
			System.out.println("X-Edge Detected: Move Aborted");
		}
	}
	
	/*
	 * Return the rover x-coordinate
	 */
	public int getX() {
		return x;
	}
	
	/*
	 * Set the rover y-coordinate if value is within plateau borders
	 */
	public void setY(int y, int edge) {
		if(y >= 0 && y <= edge) {
			this.y = y;
		} else {
			System.out.println("Y-Edge Detected: Move Aborted");
		}		
	}
	
	/*
	 * Return the rover y-coordinate
	 */
	public int getY() {
		return y;
	}
	
	/*
	 * Set the direction the rover is heading
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	/*
	 * Return the direction the rover is heading
	 */
	public String getHeading() {
		return heading;
	}
	
	/*
	 * Return the String of movements the rover is assigned to complete
	 */
	public String getMovements() {
		return movements;
	}
	
	/*
	 * Set the String of movements the rover is assigned to complete
	 */
	public void setMovements(String m) {
		this.movements = m;
	}

}
