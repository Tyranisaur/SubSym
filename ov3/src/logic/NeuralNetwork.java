package logic;

public class NeuralNetwork {

	NeuralNode[] inputLayer;
	NeuralNode[] layer1;
	NeuralNode[] layer2;
	NeuralNode[] outputLayer;

	public NeuralNetwork(Genotype gene){
		inputLayer = new NeuralNode[6];
		layer1 = new NeuralNode[6];
		layer2 = new NeuralNode[6];
		outputLayer = new NeuralNode[3];
		double[] weightList = gene.getWeights();
		double[] longlist = new double[6];
		double[] sublist = new double[6];
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i] = new NeuralNode(null, null);
		}
		for(int i = 0; i < 6; i++){
			System.arraycopy(weightList, i * 6, longlist, 0, 6);
			layer1[i] = new NeuralNode(longlist, inputLayer);
		}

		for(int i = 0; i < 6; i++){
			System.arraycopy(weightList, i * 6 + 36, sublist, 0, 6);
			layer2[i] = new NeuralNode(sublist, layer1);
		}
		for(int i = 0; i < 3; i++){
			System.arraycopy(weightList, i * 6 + 72, sublist, 0, 6);
			outputLayer[i] = new NeuralNode(sublist, layer2);
		}
	}

	public Direction caluculateMove(CellType[] sensorInput){
		inputLayer[0].setValue(sensorInput[0] == CellType.FOOD 		? 1.0 : -1.0);
		inputLayer[1].setValue(sensorInput[0] == CellType.POISON 	? 1.0 : -1.0);
		inputLayer[2].setValue(sensorInput[1] == CellType.FOOD 		? 1.0 : -1.0);
		inputLayer[3].setValue(sensorInput[1] == CellType.POISON 	? 1.0 : -1.0);
		inputLayer[4].setValue(sensorInput[2] == CellType.FOOD 		? 1.0 : -1.0);
		inputLayer[5].setValue(sensorInput[2] == CellType.POISON 	? 1.0 : -1.0);


		int highestIndex = 0;
		double highestValue = -10.0;
		Direction ret = Direction.NONE;
		for(int i = 0; i < outputLayer.length; i++){
			outputLayer[i].activation();
			if(outputLayer[i].getValue() > highestValue){
				highestValue = outputLayer[i].getValue();
				highestIndex = i;
			}
		}
		if(highestValue > Parameters.treshhold){
			switch(highestIndex){
			case 0:
				ret = Direction.LEFT;
				break;
			case 1:
				ret = Direction.UP;
				break;
			case 2:
				ret = Direction.RIGHT;
			}
		}
		resetNetwork();
		return ret;


	}
	private void resetNetwork(){
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i].setValue(-10.0);
		}
		for(int i = 0; i < layer1.length; i++){
			layer1[i].setValue(-10.0);
			layer2[i].setValue(-10.0);
		}
		for(int i = 0; i < outputLayer.length; i++){
			outputLayer[i].setValue(-10.0);
		}
	}
}
