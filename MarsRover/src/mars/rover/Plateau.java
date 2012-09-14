package mars.rover;

import java.util.ArrayList;

// Representation of Mars Plateau
public class Plateau implements Directions {
	private int width;
	private int height;
	private ArrayList<Rover> rovers = new ArrayList<Rover>();
	
	/*
	 * Constructor: Creates an instance of plateau with a width and height
	 */
	public Plateau(int w, int h) {
		width = w;
		height = h;
	}
	
	/*
	 * Moves all of the rovers on the plateau sequentially
	 */
	public void moveRovers() {
		ArrayList<Rover> rovers = this.getRovers();
		for(int i = 0; i < rovers.size(); i++) {
			//Retrieve current rover and its instructed movements
			Rover rov = rovers.get(i);
			String moves = rov.getMovements();
			for(int j = 0; j < moves.length(); j++) {
				//Perform current instructed move
				String currentMove = "F";
				if(j+1 >= moves.length()) {
					currentMove = moves.substring(j);
				} else {
					currentMove = moves.substring(j, j+1);
				}
				if(currentMove.equals("M")) {
					detect(rov,i,rovers);
				} else {
					rov.rotate(currentMove);
				}
			}
		}
	}
	
	/*
	 * Detects if rover will collide with another rove if not performs move
	 */
	public void detect(Rover r, int i, ArrayList<Rover> rovers) {
		boolean collision = false;
		//Retrieve current coordinates
		int currX = r.getX();
		int currY = r.getY();
		//Retrieve heading and potential move
		String head = r.getHeading();
		int[] move = Plateau.MOVE.get(head);
		//Calculate potential new move
		int newX = 0;
		int newY = 0;
		if(move != null) {
			newX = currX + move[0];
			newY = currY + move[1];
		}
		//Determine if the potential move causes a collision
		for(int k = 0; k < rovers.size(); k++) {
			if(k != i) {
				Rover rov = rovers.get(k);
				if(newX == rov.getX() && newY == rov.getY()) {
					System.out.println("Collision Detected: Move Aborted");
					collision = true;
				}
			}
		}
		//If no collision is found move the current rover
		if(!collision) {
			r.setX(newX, this.getWidth());
			r.setY(newY, this.getHeight());
		}
	}
	
	/*
	 * Add a rover to the plateau
	 */
	public void addRover(Rover r) {
		this.getRovers().add(r);
	}
	
	/*
	 * Return all of the rovers on the plateau
	 */
	public ArrayList<Rover> getRovers() {
		return rovers;
	}
	
	/*
	 * Return the width of the plateau
	 */
	public int getWidth() {
		return width;
	}
	
	/*
	 * Return the height of the plateau
	 */
	public int getHeight() {
		return height;
	}	
	
}
