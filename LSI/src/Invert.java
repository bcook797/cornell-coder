import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import Jama.Matrix;


public class Invert {	
	private int numDocs; // number of documents in the index
	private int numTerms; // number of terms in the index
	private Vector<Document> documents= new Vector<Document>();
	private String[] stopword;
	private Vector<Term> index;
	private static Matrix tdMatrix;	
	
	
	public Invert() {		
		index= new Vector<Term>();		
		createStopList("test/stoplist.txt");
	}
	
	/* Creates the index from a certain document */
	public void createIndex(String filename) {		
		BufferedReader br = null;			
		Document doc= new Document(filename, numDocs);
		documents.add(doc);		
		try {			
			br = new BufferedReader(new FileReader(filename));
			String line= null;
			while ((line = br.readLine()) != null) {
				String[] terms= parser(line);
				for(int i= 0; i < terms.length; i++){					
					Term t= new Term(terms[i], doc, i);					
					if ((index.contains(t))) {
						int a= index.lastIndexOf(t);
						t.update(index.get(a), doc, i);						
					} else if (isStopWord(t) == 0) 	{
						 index.add(t);
						 numTerms++;
					}					
				}				
				line= br.readLine();
				numDocs++;
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
	
	/* Creates the stop list */
	public void createStopList(String filename) {
		BufferedReader br= null;
		
		try {
			br= new BufferedReader(new FileReader(filename));
			String line= null;				
			stopword= new String[40];			
			int i= 0;
			while ((line = br.readLine()) != null) {					
					stopword[i]= line.trim();
					i++;
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
	
	/* Returns 1 if the term is a stopword and 0 if it is not a stopword */
	public int isStopWord(Term t) {
		String s= t.toString();
		int a= 0;
		for(int i= 0; i < stopword.length; i++) {			
			if (s.equalsIgnoreCase(stopword[i])) {
				a= 1;					
			}
		}
		return a;
	}	
	
	/* Parses each term out of the document */
	public String[] parser(String line) {
		String[] terms = line.split(" ");
		for(int i= 0; i < terms.length; i++) {
			String t= terms[i].toLowerCase();
			terms[i]= t;
		}
		return terms;
	}	
	
	/* Creates the term-document matrix of the index */
	public void createTDMatrix() {		
		double[][] data= new double[numTerms][numDocs];
		for(int i=0; i < index.size(); i++) {
			Vector<PostingListNode> posting= index.get(i).getPostings();
			for(int j= 0; j < posting.size(); j++) {				
				int a= posting.get(j).getDoc().getDocID();				
				double b= index.get(i).getWeight(posting.get(j));				
				data[i][a]= b;
			}
		}					
		Matrix tdMatrix= new Matrix(data);
		//tdMatrix.print(1, 2);
		setTDMatrix(tdMatrix);
	}
	
	/* Sets the term-document matrix of the index used during transformations */
	public static void setTDMatrix(Matrix matrix) {
		tdMatrix= matrix;
	}
	
	/* Runs the search function for the index */
	public void Test(Invert a) {
		System.out.println("Search for term:");
		String term= null;
		while(!("ZZZ".equals(term))) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
		try {
			term= br.readLine();			
		} catch(IOException e) {
			System.out.println("Input error while attempting to find your search");
			System.exit(1);
		}
		Query q= new Query(index, documents, tdMatrix);
		Vector<QueryResult> results= q.search(term); 
		for(int i= 0; i < 3; i++) {
			if (!(results.isEmpty())) {
			System.out.println(results.get(i).toString());
			results.get(i).getDoc().print();
			} else {
				System.out.println("No results for search: " + term);
				break;
			}
		}		
		}
	}	
	
	public static void main(String[] args) {				
		Invert a = new Invert();
		for(int i= 1; i <= 30; i++) {
			if (i < 10) {
				a.createIndex("test/file0" + i + ".txt");
			} else {
				a.createIndex("test/file" + i + ".txt");
			}		
		}		
		a.createTDMatrix();
		LSI b= new LSI(tdMatrix);		
		setTDMatrix(b.getTdMatrix());
		//tdMatrix.print(1, 4);
		//a.index.print();
		a.Test(a);
		
	}

}
