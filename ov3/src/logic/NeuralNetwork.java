package logic;

public class NeuralNetwork {

	NeuralNode[] inputLayer;
	NeuralNode[] layer1;
	NeuralNode[] layer2;
	NeuralNode[] outputLayer;

	public NeuralNetwork(Genotype gene){
		inputLayer = new NeuralNode[6];
		layer1 = new NeuralNode[5];
		//layer2 = new NeuralNode[9];
		outputLayer = new NeuralNode[3];
		double[] weightList = gene.getWeights();
		double[] longlist = new double[5];
		double[] sublist = new double[6];
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i] = new NeuralNode(null, null);
		}
		for(int i = 0; i < layer1.length; i++){
			System.arraycopy(weightList, i * inputLayer.length, sublist, 0, inputLayer.length);
			layer1[i] = new NeuralNode(sublist, inputLayer);
		}
/*
		for(int i = 0; i < layer2.length; i++){
			System.arraycopy(weightList, i * layer1.length + inputLayer.length * layer1.length, longlist, 0, layer1.length);
			layer2[i] = new NeuralNode(longlist, layer1);
		}
		for(int i = 0; i < outputLayer.length; i++){
			System.arraycopy(weightList, i * layer2.length + inputLayer.length * layer1.length + layer1.length * layer2.length, longlist, 0, layer2.length);
			outputLayer[i] = new NeuralNode(longlist, layer1);
		}
 */	
		for(int i = 0; i < outputLayer.length; i++){
			System.arraycopy(weightList, i * layer1.length + inputLayer.length * layer1.length, longlist, 0, layer1.length);
			outputLayer[i] = new NeuralNode(longlist, layer1);
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
		}
		/*
		for(int i = 0; i < layer2.length; i++){			
			layer2[i].setValue(-10.0);
		}
		*/
		for(int i = 0; i < outputLayer.length; i++){
			outputLayer[i].setValue(-10.0);
		}
	}
}
