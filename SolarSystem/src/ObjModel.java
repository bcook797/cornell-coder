 /*
 * ObjModel.java
 * Author: Colin Ponce
 * Written: 9/12/2010
 *
 * With code taken from http://www.openprocessing.org/visuals/?visualID=191#
*/

import java.io.*;
import javax.vecmath.*;
import java.util.*;
 
class ObjModel{
	private String fileName;
	private ArrayList<Point3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<TexCoord2f> textures;
	private ArrayList<Polygon> polygons;
 
 
	ObjModel(String fn){
		fileName = fn;
		vertices = new ArrayList<Point3f>();
		normals = new ArrayList<Vector3f>();
		textures = new ArrayList<TexCoord2f>();
		polygons = new ArrayList<Polygon>();
		
		readFile();
	}
	
	public Polygon getPolygon(int index) { return polygons.get(index); }
	public int numPolygons() { return polygons.size(); }
	
	private Point3f getVertex(int index)
	{
		if (index >= 0) return vertices.get(index);
		else return vertices.get(vertices.size() + index);
	}
	
	private Vector3f getNormal(int index)
	{
		if (index >= 0) return normals.get(index);
		else return normals.get(normals.size() + index);
	}
	
	private TexCoord2f getTexture(int index)
	{
		if (index >= 0) return textures.get(index);
		else return textures.get(textures.size() + index);
	}

	private void readFile(){
		try{
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			try{
				String newLine = null;
				while((newLine = input.readLine()) != null){
					int ind = newLine.indexOf("vn ");
					if(ind != -1){
						readNormal(newLine);
						continue;
					}

					ind = newLine.indexOf("v ");
					if(ind != -1){
						readVertex(newLine);
						continue;
					}

					ind = newLine.indexOf("f ");
					if(ind != -1) {
						readPolygon(newLine);
						continue;
					} 

					ind = newLine.indexOf("vt ");
					if(ind != -1){
						readTexture(newLine);
						continue;
					}
				}
			}
			finally{
				input.close();
			}
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	private void readVertex(String newLine){
		String pieces[] = newLine.split("\\s+");
		Point3f vertex = new Point3f(Float.parseFloat(pieces[1]), Float.parseFloat(pieces[2]), Float.parseFloat(pieces[3]));
		vertices.add(vertex);
	}

	private void readNormal(String newLine){
		String pieces[] = newLine.split("\\s+");
		Vector3f norm = new Vector3f(Float.parseFloat(pieces[1]), Float.parseFloat(pieces[2]), Float.parseFloat(pieces[3]));
		normals.add(norm);
	}

	private void readTexture(String newLine){
		String pieces[] = newLine.split("\\s+");
		TexCoord2f tex = new TexCoord2f(Float.parseFloat(pieces[1]), Float.parseFloat(pieces[2]));
		textures.add(tex);
	}

	private void readPolygon(String newLine) throws IOException {  // assumes we're reading a triangle
		String pieces[] = newLine.split("\\s+");
		Polygon poly = new Polygon();
		for(int i = 1; i < pieces.length; i++){
			String smallerPieces[] = pieces[i].split("//");
			boolean hasTexture = false;
			if (smallerPieces.length == 1) {
				smallerPieces = pieces[i].split("/");
				hasTexture = true;
			}
			
			if(smallerPieces.length == 3){ // we have verts/texture/normals
				poly.normals.add(getNormal(Integer.parseInt(smallerPieces[2])-1));
				poly.textures.add(getTexture(Integer.parseInt(smallerPieces[1])-1));
			}
			else if (hasTexture && smallerPieces.length == 2) { // only vertex/texture
				poly.textures.add(getTexture(Integer.parseInt(smallerPieces[1])-1));
			}
			else if (smallerPieces.length == 2) poly.normals.add(getNormal(Integer.parseInt(smallerPieces[1])-1)); // only vertex/normal

			poly.vertices.add(getVertex(Integer.parseInt(smallerPieces[0])-1));
		}
		polygons.add(poly);
	}
}