import java.util.Vector;


public class PostingListNode {
	private Document document;	
	private int termFreq; //The term frequency
	private double tfWeight; // Term frequency weight	
	private Vector<Integer> locations= new Vector<Integer>(); //The locations of the term within the file
	
	public PostingListNode(Document doc, int loc) {
		document = doc;		
		locations.add(loc);
		termFreq++;
	}
	
	public Document getDoc() {
		return document;
	}
	
	/* Gets the term frequency weight for the post */
	public double getTFWeight() {
		setTFWeight();
		return tfWeight;
	}
	
	/* Sets the term frequency weight for the post */
	public void setTFWeight() {
		double a= termFreq;
		tfWeight= 1 + Math.log(a);
	}
	
	/* Adds new locations to the specific post */
	public void addLoc(int l) {
		locations.add(l);
	}
	
	/* Gets the locations vector of the post */
	public Vector<Integer> getLoc() {
		return locations;
	}
	
	/* Increments the term frequency */
	public void plusTermFreq() {
		termFreq++;		
	}
	
	public String toString() {
		String s= document.getDoc().substring(5,11);
		String a= "";
		for (int i= 0; i < locations.size(); i++){
			a= a + locations.get(i).toString() + " ";
		}		
		return  s + " " + "["+ termFreq + "]:" + " " + a;
	}
}
