package logic;

public class NeuralNode {

	double output;
	private double value;
	private double gain;
	private double timeConstant;
	private double bias;
	private double[] parentWeights;
	private double[] sameWeights;
	private NeuralNode[] parentNodes;
	private NeuralNode[] sameLevelNodes;

	public NeuralNode(byte[] weights, NeuralNode[] parentNodes, NeuralNode[] sameLevelNodes){
		if(weights != null){
			gain = (weights[0] & 255) * 4.0 / 255.0 + 1.0 ;
			timeConstant = (weights[1] & 255) * 1.0 / 255.0 + 1.0;
			bias = (weights[2] & 255) * -10.0 / 255.0;
			parentWeights = new double[parentNodes.length ];
			for(int i = 0; i < parentNodes.length; i++){
				parentWeights[i] = (weights[3 + i] & 255) * 10.0 / 255.0 - 5.0;
			}
			sameWeights = new double[sameLevelNodes.length];
			for(int i = 0; i < this.sameWeights.length; i++){
				sameWeights[i] = (weights[3 + parentWeights.length + i] & 255) * 10.0 / 255.0 - 5.0;
			}
			this.parentNodes = parentNodes;
			this.sameLevelNodes = sameLevelNodes;

		}
		value = 0.0;
		output = 0.0;
	}
	public void setValue(double v){
		output = v;
	}
	public double getValue(){
		return output;
	}
	public void activation(){
		double oldValue = value;
		double s = 0.0;
		for(int i = 0; i < parentNodes.length; i++){
			s += parentNodes[i].getValue() * parentWeights[i];

		}
		for(int i = 0; i < sameLevelNodes.length; i++){
			s += sameLevelNodes[i].getValue() * sameWeights[i];
		}
		double diff = (s - oldValue + bias) / timeConstant;
		value += diff;
		output = 1.0 / (1 + Math.exp( -value * gain));
	}
}
