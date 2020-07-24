public class Trainer {

		private int[][][] data ;
		private double [] weights;
		private Perceptron perceptron = new Perceptron(); 
		private int epochNumber = 0; 
		private boolean errorFlag = true;
		private double error = 0;
		private double[] adjustedWeights = null;
		
		public Trainer(int[][][] trainingData) {
			this.weights = setWeights(trainingData[0][0].length); 
			data = trainingData;
	 		while(errorFlag) {
			//	this.printHeading(epochNumber++);
				errorFlag = false;
				error = 0;
				for ( int i = 0; i < data.length; i ++) {
					double weightedSum = perceptron.calculateWeightedSum(data[i][0], weights);
					int result = perceptron.applyActivationFunction(weightedSum);
					error = data[i][1][0] - result; 
					if(error != 0 )
						errorFlag = true;
					adjustedWeights = perceptron.adjustWeights(data[i][0], weights, error);
			//		this.printVector(data[i], weights, result, error, weightedSum, adjustedWeights);
					weights = adjustedWeights; 
				}
				
			}
	 		
		}
		
		private double[] setWeights(int length) {
			double[] weights = new double[length];
			for(int x = 0; x < length; x++) {
				weights[x] = Math.random();
			}
			return weights;
		}

		public boolean useTrainer(int[] carOutput) {
	 		double weightedSum = perceptron.calculateWeightedSum(carOutput, weights);
	 		int result = perceptron.applyActivationFunction(weightedSum);
	 		return result > 0; 
		}
	
		public void printHeading(int epockNumber) {
			System.out.println("\n============================Epock # " + epockNumber + "============================================================================");
			System.out.println("   w1  |   w2  |   x1  |   x2  |Target Result|   Result   |  error  |  Weighted Sum  |  adjusted w1  |  adjusted w2");
			System.out.println("======================================================================================================================================");
		}
		
		public void printVector(int[][] data, double[] weights, int result, double error, double weightedSum, double[] adjustedWeights) {
			System.out.println("   " + String.format("%.2f", weights[0]) + "|  " + String.format("%.2f", weights[1] )   + " |    " +  data[0][0] + "  |    " +  data[0][1] + "  |       " +  data[1][0] + "     |      "  + result + "     |  " + error + "   |    " +  String.format("%.2f", weightedSum ) + "        |   " + String.format("%.2f", adjustedWeights[0] ) + "      |   " + String.format("%.2f", adjustedWeights[1] )  );
		}

	    public double getWeightOne()
	    {
	        return weights[0];
	    }

	    public double getWeightTwo()
	    {
	        return weights[1];
	    }

	    public double getWeightThree()
	    {
	        return weights[2];
	    }
	   
	    public double[] getWeights() {
	    	return this.weights;
	    }
	    
	}

