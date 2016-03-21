package logic;

public class Fitness {

	private static Board[] defaultBoard;

	private static Board activeBoard;
	private static NeuralNetwork network;
	private static CellType[] sensorData;
	private static CellType output;
	private static Genotype best;

	public static void init(){
		defaultBoard = new Board[Parameters.scanarios];
		for(int i = 0; i < Parameters.scanarios; i++){
			defaultBoard[i] = new Board();
		}
	}
	public static void setBest(Genotype b){
		best = b;
	}

	public static Board getBoard(int i){
		return defaultBoard[i].clone();
	}

	public static Direction[] getMoves(int index){
		activeBoard = defaultBoard[index].clone();
		Direction[] moves = new Direction[60];
		network = new NeuralNetwork(best);
		for(int i = 0; i < 60; i++){
			sensorData = activeBoard.sensorInput();
			moves[i] = network.caluculateMove(sensorData);
			activeBoard.move(moves[i]);
		}



		return moves;
	}


	public static void function(Genotype gene){
		double value = 0.0;
		for(int i = 0; i < Parameters.scanarios; i++){
			activeBoard = defaultBoard[i].clone();
			Direction move;
			network = new NeuralNetwork(gene);
			for(int step = 0; step < Parameters.stepsPerGeneration; step++){
				sensorData = activeBoard.sensorInput();
				move = network.caluculateMove(sensorData);
				output = activeBoard.move(move);
				if(output == CellType.FOOD){
					value += 1.0;
				}
				else if(output == CellType.POISON){
					value -= 2.5;
				}
				else if(output == CellType.EMPTY){
					value -= 0.25;
				}

			}
		}

		gene.fitness = Math.max(0.0, value/Parameters.scanarios);

	}

	public static void doneTesting(){
		if(Parameters.dynamicBoard || Parameters.newVisualizationScenario){
			for(int i = 0; i < defaultBoard.length; i++){
				defaultBoard[i] = new Board();
			}
		}
	}

	public static void nextGeneration() {
		if(Parameters.dynamicBoard){
			for(int i = 0; i < Parameters.scanarios; i++){
				defaultBoard[i] = new Board();
			}

		}
	}
}