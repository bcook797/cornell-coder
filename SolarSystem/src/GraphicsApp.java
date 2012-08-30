/* GraphicsApp.java
 * 
 * Created on: Sept 4, 2010
 * 		Author: Colin Ponce
 */
import java.io.*;
import java.nio.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.glu.gl2.*;
import javax.media.opengl.awt.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO; 
import javax.vecmath.*;


public class GraphicsApp implements GLEventListener{
	private Vector3f cameraPos, cameraForward, cameraUp, cameraLeft;
	private GLProfile glp;
	private double PlanetAngle, cameraX;
	private int  width, height, boxTexture, solar, stars;
	private final int numStars= 2000; //number of stars in the solar system
	private FPSAnimator fpsAnim;
	private GLCanvas canvas;
	private JFrame frame;
	private FrameExporter frameExporter;
	private JOGLEventListener listener;
	private Texture sunTexture;
	private Random random = new Random();
	private Random randStars= new Random();
	
	public GraphicsApp(GLCanvas canvas, GLProfile glp) {
		this.glp = glp;
		PlanetAngle = 0;
		cameraX = 0;
		this.canvas = canvas;

		cameraPos = new Vector3f(0, 0, 10);
		cameraForward = new Vector3f(0, 0, -1);
		cameraUp = new Vector3f(0, 1, 0);

		cameraLeft = new Vector3f();
		cameraLeft.cross(cameraUp, cameraForward);

		this.width = 600;
		this.height = 600;
		listener = new JOGLEventListener(this);
		setupWindow();

		canvas.addGLEventListener(this);
		canvas.addKeyListener(listener);
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);
		canvas.requestFocus();
	}
	
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();		
		GLUT glut= new GLUT();
		gl.glClearColor(0, 0, 0, 1);
		
		// Load Sun Texture
		sunTexture= loadTexture("suntexture.png");			
		
		//Light Parameters 
		float[] lightAmbient = { 0.1f, 0.1f, 0.1f, 1.0f };		
		float[] lightSpecular = { 0.4f, 0.4f, 0.4f, 1.0f };
		float[] lightDiffuse= { 0.5f, 0.5f, 0.5f, 1.0f };
		float[] lightPos = { 0, 15, 0, 1.0f }; //Light Position coming from the sun at the origin

		//Set Light Parameters
		gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_AMBIENT, lightAmbient, 0);		
		gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_SPECULAR, lightSpecular, 0);
		gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_DIFFUSE, lightDiffuse, 0);		
		gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_POSITION, lightPos, 0);
		
		//Enable Lighting
		gl.glEnable(GLLightingFunc.GL_LIGHTING);
		gl.glEnable(GLLightingFunc.GL_LIGHT1);			
		
		gl.glShadeModel(GL2.GL_SMOOTH);

		// Depth Buffer Setup
		gl.glClearDepth(1);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		gl.glEnable(GL2.GL_CULL_FACE);

		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		//Create Randomly Generated Star Field
		createStars(gl, glut);
		
		//Create Randomly Generated Solar System
		createSolar(gl, glut);
		

		// Set initial position for the camera
		cameraFreeMove(3, 1, -30);
		cameraRotateY(-10);
		cameraRotateX(-5);
	}
	
	private Texture loadTexture(String file) {
		Texture tex= null;
		try {
			tex= TextureIO.newTexture(new File(file), false);
			tex.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			tex.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tex;
	}	
    
	
	public void display(GLAutoDrawable drawable) {
		update(); 
		render(drawable);
		
		if(frameExporter != null) {
		    frameExporter.writeFrame();
		}
	}
	
	private void update() {		
		
		PlanetAngle += 0.7;
		if (PlanetAngle > 360) {
			PlanetAngle = 0;
		}
	}
	
	private void render(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();		
		GLUgl2 glu = new GLUgl2();		

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();		
		
		
		// Position the camera
		glu.gluLookAt(cameraPos.x, cameraPos.y, cameraPos.z,
			cameraPos.x + cameraForward.x,
			cameraPos.y + cameraForward.y,
			cameraPos.z + cameraForward.z,
			cameraUp.x, cameraUp.y, cameraUp.z);	
		
		//Display the Star Field
		gl.glCallList(stars);		

		//Rotate and Revolve the planets
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glTranslatef(5.0f, 0.0f, 2.0f);
		gl.glRotatef((float)PlanetAngle, 0.0f, 1.0f, 0.0f);		
		
		//Display the created Solar System
		gl.glCallList(solar);		
		

		gl.glFlush(); // Send the commands to the OpenGL state machine
	}		
	
	/* Create the Solar System into a pre-compiled display list */
	public void createSolar(GL2 gl, GLUT glut) {		
		solar= gl.glGenLists(1); //Create Solar Display List
		gl.glNewList(solar, GL2.GL_COMPILE);		
		new Solar(gl, glut, sunTexture, random);	
		gl.glEndList();		
	}
	
	/* Create the Star Field into a pre-compiled display list */
	public void createStars(GL2 gl, GLUT glut) {
		stars= gl.glGenLists(1);
		gl.glNewList(stars, GL2.GL_COMPILE);
		for(int i= 0; i < numStars; i++) {
			float x= randStars.nextInt(400) - 200;
			float y= randStars.nextInt(400) - 200;
			float z= randStars.nextInt(400) - 200;			
			new Star(gl, glut, x, y, z);
		}
		gl.glEndList();
	}	
	
	
	/**** Don't worry about stuff below this line ****/

	public void cameraFreeMove(double dx, double dy, double dz) {
		if (dx == 0.0 && dy == 0.0 && dz == 0.0) return;

		cameraPos.x += (float)(dx * cameraLeft.x + dy * cameraUp.x + dz * cameraForward.x);
		cameraPos.y += (float)(dx * cameraLeft.y + dy * cameraUp.y + dz * cameraForward.y);
		cameraPos.z += (float)(dx * cameraLeft.z + dy * cameraUp.z + dz * cameraForward.z);
	}

	public void cameraRotateX(double theta) {
		double angle = (theta * Math.PI) / 180.0;

		Vector3f u = new Vector3f();
		u.scale((float)-Math.sin(angle), cameraUp);
		Vector3f v = new Vector3f();
		v.scale((float)Math.cos(angle), cameraForward);

		cameraForward.add(u, v);
		cameraForward.normalize();

		cameraLeft.cross(cameraUp, cameraForward);
		cameraLeft.normalize();
	}

	public void cameraRotateY(double theta) {
		double angle = (theta * Math.PI) / 180.0;

		Vector3f u = new Vector3f();
		u.scale((float)Math.sin(angle), cameraLeft);
		Vector3f v = new Vector3f();
		v.scale((float)Math.cos(angle), cameraForward);

		cameraForward.add(u, v);
		cameraForward.normalize();

		cameraLeft.cross(cameraUp, cameraForward);
		cameraLeft.normalize();
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		GLUgl2 glu = new GLUgl2();

		if (height == 0) { // prevent a divide by 0
			height = 1;
		}

		this.width = width;
		this.height = height;
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
	public void initializeAnimation() {
		fpsAnim = new FPSAnimator(this.canvas, 60);
		fpsAnim.start();
	}	
	
	public void stopAnimation() {		
		fpsAnim.stop();
	}

	public void toggleFrameExporter() {
		if (frameExporter == null){
			frameExporter = new FrameExporter();
			System.out.println("Beginning capture...");
		}
		else {
			frameExporter = null;
			System.out.println("Ending capture");
		}
	}

	private void setupWindow() {
		this.frame = new JFrame("Solar System");
		frame.setSize(width, height);
		frame.add(canvas);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem newItem= new JMenuItem("New Solar System");
		newItem.addActionListener(listener);
		menu.add(newItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(listener);
		menu.add(exitItem);
		frame.setJMenuBar(menuBar);
	}
	
	public void dispose(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glDeleteLists(solar, 1);
		gl.glDeleteLists(stars, 1);
	}
	
	private static int exportId = -1;
	private class FrameExporter
    {
		private int nFrames  = 0;

		FrameExporter()  { 
			exportId += 1;
		}

		void writeFrame()
		{ 
			long   timeNS   = -System.nanoTime();
			String number   = String.format("%05d", nFrames); //Utils.getPaddedNumber(nFrames, 5, "0");
			String filename = "export"+exportId+"-"+number+".jpg";/// BUG: DIRECTORY MUST EXIST!

			try{  
				System.out.println(filename);
				java.io.File   file     = new java.io.File(filename);
				if(file.exists()) System.out.println("WARNING: OVERWRITING PREVIOUS FILE: "+filename);

				/// WRITE IMAGE: ( :P Screenshot asks for width/height --> cache in GLEventListener.reshape() impl)
				com.jogamp.opengl.util.awt.Screenshot.writeToFile(file, width, height);

				timeNS += System.nanoTime();
				System.out.println((timeNS/1000000)+"ms:  Wrote image: "+filename);
		
			}catch(Exception e) { 
				e.printStackTrace();
				System.out.println("OOPS: "+e); 
			} 

			nFrames += 1;
		}
    	}
	
	public static void main(String[] args)	{
		GLProfile.initSingleton();
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);

		GraphicsApp ga = new GraphicsApp(canvas, glp);
		ga.initializeAnimation();
	}

	
}

