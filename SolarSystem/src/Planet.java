import javax.media.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/* Creates a sphere representation of a planet */
public class Planet extends Sphere {	
	
	public Planet(GL2 gl, GLUT glut, float x, float y, float z, float r, float g, float b, double rad) {
		super(gl,glut,x,y,z,r,g,b,rad);		
	}	
}
