package mars.rover;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MarsRover {
	
	public MarsRover() {		
		run();		
	}
	
	/*
	 * Runs the Mars Rover Test
	 */
	public void run() {
		//Reads input file through user key input
		System.out.println("Input File:");		
		String line= null;
		while(!("ZZZ".equals(line))) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));			
			try {
				line = br.readLine();
				if(!("ZZZ".equals(line))) {
					String[] files = line.trim().split(" ");
					for(int i = 0; i < files.length; i++) {
						System.out.println();
						System.out.println(files[i] + " Expected Output:");					
						String file = "test/" + files[i];
						createPlateau(file);
						System.out.println("==========");					
					}		
				}
			} catch(IOException e) {
				System.out.println("Input error while attempting to find your file(s)");
				System.exit(1);
			}
		}
	}
	
	/*
	 * Creates Plateau and Rovers based on input file
	 */
	public void createPlateau(String file) {
		//Read input file	
		BufferedReader br = null;
		Plateau p = null;
		try {			
			br = new BufferedReader(new FileReader(file));
			String line= br.readLine();
			
			//Create Plateau
			String[] rect = line.trim().split(" ");
			p = new Plateau(Integer.parseInt(rect[0]),Integer.parseInt(rect[1]));
			
			//Create Rovers and them to Plateau
			while(line != null) {
				line = br.readLine();
				if(line != null) {
					String[] coordinates = line.split(" ");
					Rover r = new Rover(Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1]),coordinates[2]);
					line = br.readLine();
					r.setMovements(line);
					p.addRover(r);
				}
			}						
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		createOutput(p);
	}
	
	/*
	 * Creates the expected output of the given plateau
	 */
	public void createOutput(Plateau p) {
		p.moveRovers();
		ArrayList<Rover> rovers = p.getRovers();
		for(int i = 0; i < rovers.size(); i++) {
			Rover rov = rovers.get(i);
			System.out.println(rov.getX() + " " + rov.getY() + " " + rov.getHeading());			
		}
	}	
	
	public static void main(String[] args) {
		MarsRover a = new MarsRover();
	}
}
