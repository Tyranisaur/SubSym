package logic;

public class NeuralNode {

	private double value;
	private int[] weights;
	private NeuralNode[] inputNodes;
	private int weightSum;

	public NeuralNode(int[] weights, NeuralNode[] inputNodes){
		value = -1.0;
		this.weights = weights;
		this.inputNodes = inputNodes;
		weightSum = 0;
		if(weights != null){
			for(int i = 0; i < weights.length; i++){
				weightSum += weights[i];
			}
		}
	}
	public void setValue(double v){
		value = v;
	}
	public boolean activation(){
		value = 0.0;
		for(int i = 0; i < weights.length; i++){
			if(inputNodes[i].value < 0.0){
				inputNodes[i].activation();
			}
			value += inputNodes[i].value * weights[i];
		}
		value /= weightSum;

		return value > Parameters.treshhold;
	}
}
