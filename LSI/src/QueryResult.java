
public class QueryResult {
	
	public String title;
	public double score;
	public Document doc;
		
	public QueryResult(String title, double score, Document doc) {
		this.title= title;
		this.score= score;	
		this.doc= doc;
	}
		
	public String toString() {
		return title + " score: " + score;
	}	
	
	public Document getDoc() {
		return doc;
	}
}
