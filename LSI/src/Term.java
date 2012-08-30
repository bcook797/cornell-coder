import java.util.Vector;

/*Class Term in the trees comparing each term */
public class Term {
	private String term;
	private double docFreq= 0; // The document frequency of the term
	private double idf; //The inverse document frequency of the term
	private Vector<PostingListNode> postings = new Vector<PostingListNode>();	
	
	
	public Term(String term, Document filename, int l) {
		this.term= term;		
		docFreq++;
		postings.add(new PostingListNode(filename, l));		
	}	
	
	public Term(String term) {
		this.term= term;
	}
	
	public Vector<PostingListNode> getPostings() {
		return postings;
	}	
	
	/* Sets the inverse document frequency of the term */
	public void setIdfWeight() {
		double a= (double) (30/docFreq);
		idf= Math.log(a);				
	}
	
	/* Gets the inverse document frequency of the term */
	public double getIdfWeight() {
		setIdfWeight();
		return idf;
	}
	
	public String getTerm() {
		return term;
	}	
	
	/* Gets the tf.idf weight of a specific post */
	public double getWeight(PostingListNode post) {
		return post.getTFWeight() * this.getIdfWeight();
	}
	
	/* Updates terms that show up in multiple documents and locations */
	public void update(Term t, Document doc, int l) {				
		for(int i= 0; i < postings.size(); i++) {
			int a= doc.getDocID();
			int b= t.postings.get(i).getDoc().getDocID();
			if ((a == b)) {							
				t.postings.get(i).plusTermFreq();
				t.postings.get(i).addLoc(l);				
			} else {
				t.docFreq++;					
				t.postings.add(new PostingListNode(doc, l));
				break;
			}
		}		
	}	
	public String toString() {
		String s= "";
		for (int i= 0; i < postings.size(); i++){
			double w= getWeight(postings.get(i));
			s= s + postings.get(i).toString() + " tf.idf: " + w + "  ";
		}
		return term + " " + s;
	}
	
	public void print() {
		for (int i= 0; i < postings.size(); i++){
			double w= getWeight(postings.get(i));
			System.out.println(postings.get(i).toString() + " tf.idf: " + w);
		}
	}
	
	public boolean equals(Object t) {		
		if (t instanceof Term) {
			Term a= (Term) t;
			if (this.term.equals(a.term)) return true;
		}
		return false;
	}
	
	public int compareTo(Object t) {
		return this.getTerm().compareTo(((Term) t).getTerm());
	}	
}
