package logic;

public class NeuralNetwork {

	NeuralNode[] inputLayer;
	NeuralNode[] layer1;
	NeuralNode[] layer2;
	NeuralNode[] outputLayer;

	public NeuralNetwork(Genotype gene){
		inputLayer = new NeuralNode[9];
		layer1 = new NeuralNode[6];
		layer2 = new NeuralNode[6];
		outputLayer = new NeuralNode[3];
		int[] weightList = gene.getWeights();
		int[] longlist = new int[9];
		int[] sublist = new int[6];
		for(int i = 0; i < 9; i++){
			inputLayer[i] = new NeuralNode(null, null);
		}
		for(int i = 0; i < 6; i++){
			System.arraycopy(weightList, i * 9, longlist, 0, 9);
			layer1[i] = new NeuralNode(longlist, inputLayer);
		}

		for(int i = 0; i < 6; i++){
			System.arraycopy(weightList, i * 6 + 54, sublist, 0, 6);
			layer2[i] = new NeuralNode(sublist, layer1);
		}
		for(int i = 0; i < 3; i++){
			System.arraycopy(weightList, i * 6 + 90, sublist, 0, 6);
			outputLayer[i] = new NeuralNode(sublist, layer2);
		}
	}

	public Direction caluculateMove(CellType[] sensorInput){
		inputLayer[0].setValue(sensorInput[0] == CellType.FOOD 		? 1.0 : 0.0);
		inputLayer[1].setValue(sensorInput[0] == CellType.POISON 	? 1.0 : 0.0);
		inputLayer[2].setValue(sensorInput[0] == CellType.EMPTY 	? 1.0 : 0.0);
		inputLayer[3].setValue(sensorInput[1] == CellType.FOOD 		? 1.0 : 0.0);
		inputLayer[4].setValue(sensorInput[1] == CellType.POISON 	? 1.0 : 0.0);
		inputLayer[5].setValue(sensorInput[1] == CellType.EMPTY 	? 1.0 : 0.0);
		inputLayer[6].setValue(sensorInput[2] == CellType.FOOD 		? 1.0 : 0.0);
		inputLayer[7].setValue(sensorInput[2] == CellType.POISON 	? 1.0 : 0.0);
		inputLayer[8].setValue(sensorInput[2] == CellType.EMPTY 	? 1.0 : 0.0);

		if(outputLayer[0].activation()){
			resetNetwork();
			return Direction.LEFT;
		}
		else if(outputLayer[1].activation()){
			resetNetwork();
			return Direction.UP;
		}
		else if(outputLayer[2].activation()){
			resetNetwork();
			return Direction.RIGHT;
		}
		resetNetwork();
		return Direction.NONE;


	}
	private void resetNetwork(){
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i].setValue(-1.0);
		}
		for(int i = 0; i < layer1.length; i++){
			layer1[i].setValue(-1.0);
			layer2[i].setValue(-1.0);
		}
		for(int i = 0; i < outputLayer.length; i++){
			outputLayer[i].setValue(-1.0);
		}
	}
}
