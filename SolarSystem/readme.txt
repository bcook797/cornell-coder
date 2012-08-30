Solar System Program
Brandon Cook
Intro to Graphics
CS 4620

SPHERES:

The Sphere class is an abstract class that draws a sphere given a radius, x coordinate, y coordinate, and z coordinate. The sphere also is given a certain color using rgb values.  The constructor Sphere() creates the sphere given the parameters discussed before. The constructor uses the GLUT library to draw solid spheres using glutSolidSphere(). In the constructor the lighting material properties are also set. The materials established react to ambient, specular, and diffuse lighting. The sphere is also translated using glTranslatef() to its given x, y, z location. There are also several getter functions getX(), getY(), getZ(), getSphereRadius() to return x coordinate, y coordinate, z coordinate, and radius of the sphere. 

The Sphere class is extended by several other classes these include: Sun, Planet, and Star. The Sun class is a specific sphere located at the origin with radius 6.0. The Planet class does not change anything from the superclass Spheres. The Star class is a white sphere with radius of 0.2.  

SOLAR:

The Solar class generates a random solar system with 8 planets. It begins by applying the sun texture and then generating the sun at the origin. The sun is texturized and the texture is then disabled. Then the rotation and revolving of the planets is created by the planetOrbit() function. The planets are then created randomly by the createPlanets() function.

GRAPHICSAPP:

The GraphicsApp class creates the visual environment of the solar system. In the init() function several aspects of the environment are initialized. The sun texture is loaded using loadTexture(). Light parameters are created for ambient, specular, diffuse, and the position of the light. The light parameters are then set using glLightfv() and then the lighting is enabled. The randomly generated star field is created using createStars(), which places stars randomly in the environment and then pre-compiles them into a display list. The same thing is done for the solar system using createSolar(), which just creates a new Solar class.

In the update() function the planetAngle is changed, which causes the planets to orbit around the sun. The render() function the star field is displayed by calling the display list stars and then the solar system is displayed by calling its display list solar.



USER INTERFACE:

The user can interact with the environment by using a combination of the mouse and keys. U allows for the user to move up the y-axis of the environment and D allows for the user to move down the y-axis. The forward arrow allows for the user to zoom in and the back arrow allows the user to zoom out. The left and right arrows allow for the user to move left and right. R allows for the user to record images of the environment. Additional buttons include P, which allows the user to pause the animation in the environment using the stopAnimation() function found in the GraphicsApp class. Another addition is in the File menu the drop down choice of New Solar System creates a new solar system and allows the user to compare two randomly generated solar systems.




