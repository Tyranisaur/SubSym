package logic;

import java.util.Arrays;

public class NeuralNode {

	private double value;
	private double[] weights;
	private NeuralNode[] inputNodes;
	private double weightSum;

	public NeuralNode(double[] weights, NeuralNode[] inputNodes){
		weightSum = 0.0;
		if(weights != null){
			value = -10.0;
			this.weights = weights.clone();
			this.inputNodes = inputNodes;
			for(int i = 0; i < weights.length; i++){
				weightSum += weights[i];
			}
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
		value = Math.sin(value * Math.PI/2.0);
	}
}
