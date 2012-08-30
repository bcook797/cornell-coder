import javax.media.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/* Creates a white sphere representation of a star with radius 0.2 */ 
public class Star extends Sphere {
	
	public Star(GL2 gl, GLUT glut, float x, float y, float z) {
		super(gl, glut, x, y, z, 1.0f, 1.0f, 1.0f, 0.2f);		
	}

	
}
