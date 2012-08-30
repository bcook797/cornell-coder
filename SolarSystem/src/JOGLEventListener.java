/*
 * JOGLEventListener.java
 * 
 * Created on: Sept 4, 2010
 * 		Author: Colin Ponce
 */
import java.awt.event.*;
import java.awt.*;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

public class JOGLEventListener implements KeyListener, MouseListener, MouseMotionListener, ActionListener {
	private GraphicsApp ga;
	private Point lastMousePos;
	private boolean isLeftButtonDown;
	private boolean isPaused;
	private final double CAMERA_MOVING_STEP = 0.5;
	private final double MOUSE_SENSITIVITY_FACTOR = 0.25;
	
	public JOGLEventListener(GraphicsApp ga) {
		this.ga = ga;
		isLeftButtonDown = false;
		isPaused= false;
	}
	
	public void keyReleased(KeyEvent e) { }

	public void keyPressed(KeyEvent e) {		
		int iKey = e.getKeyCode();
		switch (iKey) {
			case KeyEvent.VK_UP:	ga.cameraFreeMove(0, 0, CAMERA_MOVING_STEP); break;
			case KeyEvent.VK_DOWN:	ga.cameraFreeMove(0, 0, -CAMERA_MOVING_STEP); break;
			case KeyEvent.VK_LEFT:	ga.cameraFreeMove(CAMERA_MOVING_STEP, 0, 0); break;
			case KeyEvent.VK_RIGHT:	ga.cameraFreeMove(-CAMERA_MOVING_STEP, 0, 0); break;
			case KeyEvent.VK_U:	ga.cameraFreeMove(0, CAMERA_MOVING_STEP, 0); break;
			case KeyEvent.VK_D:	ga.cameraFreeMove(0, -CAMERA_MOVING_STEP, 0); break;							
			case KeyEvent.VK_R: ga.toggleFrameExporter(); break;
			case KeyEvent.VK_P: 
				if (isPaused) { //Check if the animation is paused
					ga.initializeAnimation(); //Start Animation if paused
					isPaused= false;
					break;
			} 	else {
				ga.stopAnimation(); //Stop Animation if not paused
				isPaused= true;
				break;
			}
			}
	}

	public void keyTyped(KeyEvent e) { 	}
	
	public void mouseMoved(MouseEvent e) { }

	public void mouseDragged(MouseEvent e) {
		if (isLeftButtonDown) {
			Point newMousePos = e.getPoint();

			int dx = newMousePos.x - lastMousePos.x;
			int dy = newMousePos.y - lastMousePos.y;

			if (dx != 0) ga.cameraRotateY(-(float)dx * MOUSE_SENSITIVITY_FACTOR);
			if (dy != 0 ) ga.cameraRotateX((float)dy * MOUSE_SENSITIVITY_FACTOR);

			lastMousePos = newMousePos;
		}
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isLeftButtonDown = true;
			lastMousePos = e.getPoint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			isLeftButtonDown = false;
		}
	}

	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s == "Exit") {
			System.exit(0);
		}
		if (s == "New Solar System") {			
			GLProfile.initSingleton();
			GLProfile glp = GLProfile.getDefault();
			GLCapabilities caps = new GLCapabilities(glp);
			GLCanvas canvas = new GLCanvas(caps);

			GraphicsApp ga = new GraphicsApp(canvas, glp);
			ga.initializeAnimation();			
			
		}
	}
}