import java.io.IOException;

public class MatrixManipulation {
	
	public Double[][] invert(Double[][] original) throws IOException {
		
		//obtain the determinant
		Double determinant = getDeterminant(original);
		
		//declare a 2D Double array to hold the inverted matrix
		Double[][] inverted = new Double[original.length][original[0].length];

		int count = 1;
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[0].length; j++) {
				//declare a 2D array of Doubles
				Double[][] reducedXMatrix = new Double[original.length-1][original[0].length-1];
				
				int rowSkipped = 0;
				
				for (int k = 0; k < original.length; k++) {
					int columnSkipped = 0;
					for (int l = 0; l < original[0].length; l++) {
						if (j == l) {
							columnSkipped = 1;
							continue;
						}
						else if (i == k) {
							rowSkipped = 1;
							break;
						}
							
						//copy the array, removing the row and column of (i, j)
						reducedXMatrix[k-rowSkipped][l-columnSkipped] = original[k][l];
					}
				}
				
				//increment count to apply the checkerboard pattern (+, -, +, -)
				count++;
				
				//add a Double to inverted by calling the getDeterminant method
				inverted[i][j] = Math.pow(-1, count)*getDeterminant(reducedXMatrix);
			}
			
		}
		
		//transpose the matrix
		inverted = transpose(inverted);
		
		//divide each element by the original determinant
		for (int i = 0; i < inverted.length; i++) {
			for (int j = 0; j < inverted[0].length; j++)
				inverted[i][j] /= determinant;
		}
		
		return inverted;
	}
	
	public Double getDeterminant(Double[][] original) throws IOException {
		
		//declare a Double variable determinant and initialize it to 0.0
		Double determinant = 0.0;
		
		if (original.length == 2 && original[0].length == 2) {
			//base case (when a 2x2 matrix is passed in)
			return original[0][0]*original[1][1] - original[0][1]*original[1][0];
		}
		else if (original.length < 2 || original[0].length < 2) {
			
			throw new IOException("Dimensions of matrix must be at least 2x2");
		}
		else {
			//recursive case
			//declare an integer variable and initialize it to 1
			int count = 1;
			
			for (int i = 0; i < original[0].length; i++) {
				//declare a 2D array of Doubles
				Double[][] reducedXMatrix = new Double[original.length-1][original[0].length-1];
					
				for (int j = 1; j < original.length; j++) {
					int skipped = 0;
					for (int k = 0; k < original[0].length; k++) {
						if (i == k) {
							skipped = 1;
							continue;
						}
							
						//copy the array, removing the row and column of (0, i)
						reducedXMatrix[j-1][k-skipped] = original[j][k];
					}
				}
				//increment count
				count++;
				
				//recursive call
				determinant += (original[0][i])*Math.pow(-1, count)*getDeterminant(reducedXMatrix);
			}
		}
		
		return determinant;
	}

	
	public Double[][] transpose(Double[][] original) {
		//declare a new 2D Double array
		Double[][] inverted = new Double[original.length][original[0].length];
		
		//transpose the matrix and store each element in inverted
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[0].length; j++) {
				inverted[i][j] = original[j][i];
			}
		}
		
		return inverted;
	}
}