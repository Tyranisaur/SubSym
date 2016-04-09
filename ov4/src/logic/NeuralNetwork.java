package logic;

public class NeuralNetwork {

	NeuralNode[] inputLayer;
	NeuralNode[] layer1;
	NeuralNode[] layer2;
	NeuralNode[] outputLayer;

	public NeuralNetwork(Genotype gene){
		inputLayer = new NeuralNode[5];
		layer1 = new NeuralNode[2];
		outputLayer = new NeuralNode[2];
		byte[] weightList = gene.getWeights();
		byte[] longlist = new byte[10];
		byte[] sublist = new byte[7];
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i] = new NeuralNode(null, null, null);
		}
		for(int i = 0; i < layer1.length; i++){
			System.arraycopy(weightList, i * 10, longlist, 0, 10);
			layer1[i] = new NeuralNode(longlist, inputLayer, layer1);
		}

		for(int i = 0; i < outputLayer.length; i++){
			System.arraycopy(weightList, i * 7 + 20, sublist, 0, 7);
			outputLayer[i] = new NeuralNode(longlist, layer1, outputLayer);
		}
	}

	public int caluculateMove(CellType[] sensorInput){
		inputLayer[0].setValue(sensorInput[0] == CellType.OBJECT 		? 1.0 : 0.0);
		inputLayer[1].setValue(sensorInput[1] == CellType.OBJECT 		? 1.0 : 0.0);
		inputLayer[2].setValue(sensorInput[2] == CellType.OBJECT 		? 1.0 : 0.0);
		inputLayer[3].setValue(sensorInput[3] == CellType.OBJECT 		? 1.0 : 0.0);
		inputLayer[4].setValue(sensorInput[4] == CellType.OBJECT 		? 1.0 : 0.0);


		layer1[0].activation();
		layer1[1].activation();
		outputLayer[0].activation();
		outputLayer[1].activation();
		int result = (int) Math.signum(outputLayer[0].getValue() - 0.5);
		double number = outputLayer[1].getValue();
		if(number < Parameters.thresholds[0]){
			result *= 0;
		}
		else if(number < Parameters.thresholds[1]){
 
		}
		else if(number < Parameters.thresholds[2]){
			 result *= 2;
		}
		else if(number < Parameters.thresholds[3]){
			 result *= 3;
		}
		else{
			result *= 4;
		}
		return result;

	}

}
