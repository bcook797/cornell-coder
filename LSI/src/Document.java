import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Document {
	String document; 
	int docID;	
	
	public Document(String filename, int id) {
		document= filename;
		docID= id;
	}
	
	public int getDocID() {
		return docID;
	}
	
	public String getDoc() {
		return document;
	}
	
	public void print() {
		BufferedReader br= null;
		
		try {
			br= new BufferedReader(new FileReader(document));
			String line= null;			
			while ((line = br.readLine()) != null) {	
					String[] s= line.split(" ");
					String t= " ";
					for(int i= 3; i < 20; i++) {
						if (!(isNum(s[i]))) {
							t= t + s[i] + " ";
						}
					}
					System.out.println(t + "...");
					line= br.readLine();
				}					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isNum(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public String toString() {
		String s= document.substring(5,11);
		return s;
	}
}
