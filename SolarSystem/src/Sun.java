import javax.media.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/* Creates a Sun at the origin (0,0,0) */
public class Sun extends Sphere {	
	
	public Sun(GL2 gl, GLUT glut) {
		super(gl, glut, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 6.0f);		
	}

		
	
}
