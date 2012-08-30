import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

/* Generates a Random Solar System with a Sun and 8 planets */
public class Solar {
	Sun sun;
	final int numPlanets= 8; //Number of Planets in the Solar System		
	private double PlanetAngle;	
	
	public Solar(GL2 gl, GLUT glut, Texture sunTexture, Random rand) {				
		
		//Apply Sun Texture
		TextureCoords t= sunTexture.getImageTexCoords();
		gl.glPushMatrix();
		gl.glTranslated(0.0f, 0.0f, 0.0f);
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glTexEnvf(GL.GL_TEXTURE_2D, GL2ES1.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);		
		
		sunTexture.bind();	
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(t.left(), t.bottom()); gl.glVertex3f( -3.0f, -3.0f, 0);
		gl.glTexCoord2f(t.right(), t.bottom()); gl.glVertex3f( 3.0f, -3.0f, 0);
		gl.glTexCoord2f(t.right(), t.top()); gl.glVertex3f( 3.0f, 3.0f, 0);
		gl.glTexCoord2f(t.left(), t.top()); gl.glVertex3f( -3.0f, 3.0f, 0);
		
		gl.glEnd();				

		gl.glPopMatrix();
		
		//Create the sun at the center of the solar system
		sun= new Sun(gl, glut);			
		
		//Disable Sun Texture
		sunTexture.disable();
		gl.glDisable(GL.GL_TEXTURE_2D);		
		gl.glTexEnvi(GL2ES1.GL_TEXTURE_ENV, GL2ES1.GL_TEXTURE_ENV_MODE,	GL2ES1.GL_MODULATE);
		
		//Rotate and Revolve the Planets
		planetOrbit(gl);	    		   
	    
	    // Randomly place the planets around the Sun
	    createPlanets(gl,glut,rand);
	    
	    gl.glPopMatrix();		
		}
	
	public void planetOrbit(GL2 gl) {		
		gl.glPushMatrix();		
		
		PlanetAngle= (PlanetAngle + 1f) %360f;
		final float distance= 2.0f;
		final float x= (float) Math.sin(Math.toRadians(PlanetAngle)) * distance;
		final float y= (float) Math.cos(Math.toRadians(PlanetAngle)) * distance;
		final float z= 0;
		gl.glTranslatef(x,y,z);
	    gl.glScalef(0.5f, 0.5f, 0.5f);	
	}
	
	public void createPlanets(GL2 gl, GLUT glut, Random rand) {
		float rad= (float) sun.getSphereRadius(); //Get Sun radius
		
		//Create Random radius, x-coordinate, z-coordinate		
		float prad= rand.nextInt(3) + 2.0f; 
	    float a= (rad + rand.nextInt(5) + prad) + 5.0f; 
	    float c= (rad + rand.nextInt(5) + prad) + 5.0f; 
	    
		for(int i=0; i < numPlanets; i++) {	    
	    	//Random r,g,b color values
	    	float r= rand.nextFloat(); 
	    	float g= rand.nextFloat(); 
	    	float b= rand.nextFloat(); 
	    	//Create Planet
	    	new Planet(gl, glut, a, 0.0f, c, r, g, b, prad);
	    	//Create new random x, z coordinates
	    	a= a + rand.nextInt(25) + 5.5f + prad;
	    	c= c + rand.nextInt(25) + 5.5f + prad;	 
	    	prad= rand.nextInt(3) + 1.0f;
	    	if (rand.nextBoolean() == false) {
	    		a= -a;	    		
	    		c= -c;
	    	}
	    }
	}
		
		
}
	

