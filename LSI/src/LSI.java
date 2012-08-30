import Jama.*;


public class LSI {	
	private Matrix tdMatrix; // Term-Document Matrix after LSI transformation	
	
	public LSI(Matrix matrix) {		
		setTdMatrix(transform(matrix));
	}
	
	/* Creates an LSI representation of a specific matrix */
	public Matrix transform(Matrix matrix) {
		SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
	    Matrix wordVector = svd.getU();
	    Matrix sigma = svd.getS();
	    Matrix documentVector = svd.getV();
	    // compute the value of k (ie where to truncate)
	    int k = (int) Math.floor(Math.sqrt(matrix.getColumnDimension()));
	    System.out.println("Singular Values: k= " + k);
	    Matrix reducedWordVector = wordVector.getMatrix(
	      0, wordVector.getRowDimension() - 1, 0, k - 1);
	    Matrix reducedSigma = sigma.getMatrix(0, k - 1, 0, k - 1);
	    Matrix reducedDocumentVector = documentVector.getMatrix(
	      0, documentVector.getRowDimension() - 1, 0, k - 1);	   
	    Matrix weights = reducedWordVector.times(
	      reducedSigma).times(reducedDocumentVector.transpose());
	    // Phase 2: normalize the word scores for a single document
	    for (int j = 0; j < weights.getColumnDimension(); j++) {
	      double sum = sum(weights.getMatrix(
	        0, weights.getRowDimension() - 1, j, j));
	      for (int i = 0; i < weights.getRowDimension(); i++) {
	        weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
	      }
	    }
	    return weights;
	  }

	  private double sum(Matrix colMatrix) {
	    double sum = 0.0D;
	    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
	      sum += colMatrix.get(i, 0);
	    }
	    return sum;
	  }
	
	public void setTdMatrix(Matrix tdMatrix) {
		this.tdMatrix = tdMatrix;
	}

	public Matrix getTdMatrix() {
		return tdMatrix;
	}	
	
}
