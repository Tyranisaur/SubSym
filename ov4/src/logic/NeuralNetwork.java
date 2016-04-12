package logic;

public class NeuralNetwork {

	NeuralNode[] inputLayer;
	NeuralNode[] layer1;
	NeuralNode[] outputLayer;

	public NeuralNetwork(Genotype gene){
		inputLayer = new NeuralNode[Parameters.gametype == GameType.NOWRAP ? 7 : 5];
		layer1 = new NeuralNode[2];
		outputLayer = new NeuralNode[Parameters.gametype == GameType.PULL ? 3 : 2];
		byte[] weightList = gene.getWeights();
		byte[] longlist = new byte[Parameters.gametype == GameType.NOWRAP ? 12 : 10];
		byte[] sublist = new byte[Parameters.gametype == GameType.PULL ? 8 : 7];
		for(int i = 0; i < inputLayer.length; i++){
			inputLayer[i] = new NeuralNode(null, null, null);
		}
		for(int i = 0; i < layer1.length; i++){
			System.arraycopy(weightList, i * longlist.length, longlist, 0, longlist.length);
			layer1[i] = new NeuralNode(longlist, inputLayer, layer1);
		}

		for(int i = 0; i < outputLayer.length; i++){
			System.arraycopy(weightList, i * sublist.length + longlist.length * 2, sublist, 0, sublist.length);
			outputLayer[i] = new NeuralNode(sublist, layer1, outputLayer);
		}
	}

	public int caluculateMove(SensorType[] sensorInput){
		inputLayer[0].setValue(sensorInput[0] == SensorType.OBJECT 		? 1.0 : 0.0);
		inputLayer[1].setValue(sensorInput[1] == SensorType.OBJECT 		? 1.0 : 0.0);
		inputLayer[2].setValue(sensorInput[2] == SensorType.OBJECT 		? 1.0 : 0.0);
		inputLayer[3].setValue(sensorInput[3] == SensorType.OBJECT 		? 1.0 : 0.0);
		inputLayer[4].setValue(sensorInput[4] == SensorType.OBJECT 		? 1.0 : 0.0);
		if(inputLayer.length == 7){
			inputLayer[3].setValue(sensorInput[5] == SensorType.WALL 		? 1.0 : 0.0);
			inputLayer[4].setValue(sensorInput[6] == SensorType.WALL 		? 1.0 : 0.0);
		}


		layer1[0].activation();
		layer1[1].activation();
		outputLayer[0].activation();
		outputLayer[1].activation();
		if(outputLayer.length == 3){
			outputLayer[2].activation();
			if(outputLayer[2].getValue() > Parameters.pull){
				return 42;
			}
		}
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
