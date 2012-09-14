Mars Rover Program
Brandon Cook
ThoughtWorks

DIRECTIONS

Directions.java is an interface that creates constants needed to manipulate the rovers. The DIRECTIONS constant handles all of the rotations using a HashMap. The HashMap uses a two letter String with the first letter being the rovers heading and the second being the given rotation direction for a key. The HashMap key is tied to a value of a String that returns the letter of the new heading after the rotation. The MOVE constant handles all of the movements based on direction using a HashMap. The key is the rovers current heading and the value is an array holding two integers the first being the change in the x-coordinate and the second being the change in the y-coordinate. 

PLATEAU

Plateau.java is a class that contains the width and height of the given plateau as well as an ArrayList of rovers. The Plateau class also implements the Directions interface, so it has access to the DIRECTIONS and MOVE constants. The moveRovers() method processes all of the rovers instructed movements on the plateau. The moveRovers() uses the detect() method in order to ensure that a movement is valid by determining if the potential next move would collide with another rover on the plateau. 

ROVER

Rover.java is a class that contains the x and y coordinates, heading, and instructed movements of a rover. The rotate() method handles all of the rotations of the rover using the DIRECTIONS constant. The setX() and setY() methods set the rovers new x and y coordinates, but determines if the rover has reached an edge of the plateau by passing a parameter called edge, which corresponds to the plateau's height and width.

MARSROVER

MarsRover.java is a class that runs the logic of the program and creates the output for the given input. The run() method reads the user's key input to determine what input files need to be read.  The createPlateau() method reads one file and creates the corresponding plateau and rovers. The createOutput() method calls the plateau moveRovers() method and outputs the final positions for all of the rovers on the plateau.

OTHER

PlateauTest and RoverTest are just jUnit test cases that were used that all of the necessary methods were function properly for the Plateau and Rover classes.

I assumed that the initial input was always correct and that no rover was initially placed in the same position or outside of the plateau.


INSTRUCTIONS

1. Open your command line and switch to the correct file directory
		Ex: cd \Desktop\MarsRover

2. Make sure that all input files are located in \MarsRover\test file directory

3. Type java `-jar MarsRover.jar` to begin the program

4. Type the filename of the input file. Multiple files can be submitted by seperating filenames with spaces
		Ex: Input File: input01 
				or
			 Input File: input01 input02

5. Type ZZZ to exit the program

TESTS

Given the initial input the following is the corresponding output

Input File:
input01

input01 Expected Output:
1 3 N
5 1 E
==========