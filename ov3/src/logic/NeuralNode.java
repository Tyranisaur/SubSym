package logic;

public class NeuralNode {

	private double value;
	private double[] weights;
	private NeuralNode[] inputNodes;

	public NeuralNode(double[] weights, NeuralNode[] inputNodes){
		if(weights != null){
			value = -10.0;
			this.weights = weights.clone();
			this.inputNodes = inputNodes;
			
		}
	}
	public void setValue(double v){
		value = v;
	}
	public double getValue(){
		return value;
	}
	public void activation(){
		value = 0.0;
		for(int i = 0; i < weights.length; i++){
			if(inputNodes[i].value < -9.0){
				inputNodes[i].activation();
			}
			value += inputNodes[i].value * weights[i];
			
		}
		value /= weights.length;
		value = Math.abs(value) >= 0.5 ? Math.signum(value) : Math.sin(value * Math.PI);
	}
}
