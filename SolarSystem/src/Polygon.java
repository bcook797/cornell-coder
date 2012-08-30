/*
* Polygon.java
* Author: Colin Ponce
* Written: 9/12/2010
*/

import java.util.*;
import javax.vecmath.*;
	
public class Polygon
{
	ArrayList<Point3f> vertices;
	ArrayList<Vector3f> normals;
	ArrayList<TexCoord2f> textures;
	
	public Polygon()
	{
		vertices = new ArrayList<Point3f>();
		normals = new ArrayList<Vector3f>();
		textures = new ArrayList<TexCoord2f>();
	}
}