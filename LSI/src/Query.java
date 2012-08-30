import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;
import Jama.Matrix;

public class Query {	
	
	private Matrix tdMatrix;
	private Vector<Document> documents;
	private Vector<Term> terms;	
	
	public Query(Vector<Term> terms, Vector<Document> documents, Matrix tdMatrix) {
		this.terms= terms;
		this.documents= documents;
		this.tdMatrix= tdMatrix;		
	}	
	
	/* Search function that returns a vector of query results */
	public Vector<QueryResult> search(String query) {
		String[] s= parser(query);
		Vector<Term> q= new Vector<Term>();
		for(int i= 0; i < s.length; i++) {
			Term t= new Term(s[i]);
			q.add(t);
		}
		Matrix queryMatrix= getQueryMatrix(q);
		final Map<Document,Double> simMap= new HashMap<Document,Double>();
		for(int i= 0; i < tdMatrix.getColumnDimension(); i++) {
			double sim= computeSimilarity(queryMatrix, tdMatrix.getMatrix(0, tdMatrix.getRowDimension() -1, i, i));
			if (sim > 0.0D) {
				simMap.put(documents.get(i), sim);
				//System.out.println(sim);
				//System.out.println(documents.get(i).toString());
			}
		}
		return sortByScore(simMap);
	}
	
	/* Transforms the query vector to the LSI space */
	private Matrix getQueryMatrix(Vector<Term> query) {		
		Matrix queryMatrix= new Matrix(terms.size(), 1, 0.0D);
		for(int i= 0; i < query.size(); i++) {
			int termIndex= 0;
			for(int j= 0; j < terms.size(); j++) {
				if(query.get(i).compareTo(terms.get(j)) == 0) {
					queryMatrix.set(termIndex, 0, 1.0D);
				}
				termIndex++;
			}
		}
		queryMatrix= queryMatrix.times(1 / queryMatrix.norm1());
		return queryMatrix;
	}
	
	/* Sorts the scores of each document for the query and stores them in a query result vector */
	private Vector<QueryResult> sortByScore(final Map<Document,Double> simMap) {
		Vector<QueryResult> results= new Vector<QueryResult>();
		Vector<Document> docs= new Vector<Document>();
		docs.addAll(simMap.keySet());
		Collections.sort(docs, new Comparator<Document>() {
			public int compare(Document d1, Document d2) {
				return simMap.get(d2).compareTo(simMap.get(d1));
			}
		});
		Vector<String> docNames= new Vector<String>();
		double score;
		for(int i= 0; i < docs.size(); i ++) {
			String s= docs.get(i).toString();
			docNames.add(s);
		}
		for(int j= 0; j < docNames.size(); j ++) {			
			score= simMap.get(docs.get(j));				
			if (score < 0.0001D) {				
				continue;
			}	
			//System.out.println(score);
			results.add(new QueryResult(docNames.get(j), score, docs.get(j)));	
		}
		return results;
	}		
	
	/* Computes the cosine similarity measure to rank relevant documents */
	public double computeSimilarity(Matrix sourceDoc, Matrix targetDoc) {
		double dotProduct= sourceDoc.arrayTimes(targetDoc).norm1();
		double eucledianDist= sourceDoc.normF() * targetDoc.normF();
		return dotProduct / eucledianDist;
	}
	
	public String[] parser(String line) {
		String[] words = line.split(" ");
		for(int i= 0; i < words.length; i++) {
			String t= words[i].toLowerCase();
			words[i]= t;
		}
		return words;
	}	
}


