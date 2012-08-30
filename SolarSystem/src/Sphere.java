import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.fixedfunc.GLLightingFunc;

import com.jogamp.opengl.util.gl2.GLUT;

public abstract class Sphere {
	double sphereRadius;
	final int slices= 50;
	final int stacks= 50;
	float x; //x coordinate of the sphere
	float y; //y coordinate of the sphere
	float z; //z coordinate of the sphere
	
	/*Creates a sphere with coordinates x, y, z and color r,g,b with radius rad*/
	public Sphere(GL2 gl, GLUT glut, float x, float y, float z, float r, float g, float b, double rad) {
		
		sphereRadius= rad;
		this.x= x;
		this.y= y;
		this.z= z;
		
		// Set the lighting material properties of the sphere
		float[] rgba= {r, g, b};
		gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GLLightingFunc.GL_SHININESS, 0.5f);
        gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_DIFFUSE, rgba, 0);
        
        // Draw the sphere		
		gl.glPushMatrix();		
		gl.glTranslatef(x, y, z);
		glut.glutSolidSphere(sphereRadius, slices, stacks);
		gl.glPopMatrix();
	}
	
	/* Gets the x-coordinate of the Sphere */
	public float getX() {
		return x;
	}
	
	/*Gets the y-coordinate of the Sphere */
	public float getY() {
		return y;
	}
	
	/*Gets the z-coordinate of the Sphere */
	public float getZ() {
		return z;
	}	
	
	/*Gets the radius of the Sphere */
	public double getSphereRadius() {
		return sphereRadius;
	}
	
	

}
