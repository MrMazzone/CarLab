public class Perceptron {


	public static final double LEARNING_RATE = 0.05;
	
	
	public double calculateWeightedSum(int[] data, double[] weights) {
		double weightedSum = 0;
		for(int x = 0; x < data.length; x++) {
			weightedSum += data[x] * weights[x]; 
		}
		return weightedSum;
	}
	
	public int applyActivationFunction(double weightedSum) {
		if(weightedSum > 1)
			return 1;
		return 0; 
	}
	
	public double[] adjustWeights(int[] data, double[] weights, double error) {
		double[] adjustedWeights = new double[weights.length];
		for(int x = 0; x < weights.length;x++) {
			adjustedWeights[x] = LEARNING_RATE * error * data[x] + weights[x];
		}
		return adjustedWeights;
	}
}
