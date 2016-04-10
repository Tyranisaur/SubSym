package logic;

public class Fitness {

	private static Board defaultBoard;
	private static Board activeBoard;
	private static NeuralNetwork network;
	private static SensorType[] sensorData;
	private static Impact output;
	private static Genotype best;

	
	public static void setBest(Genotype b){
		best = b;
	}


	


	public static void function(Genotype gene){
		double value = 0.0;
			activeBoard = defaultBoard.clone();
			int move;
			network = new NeuralNetwork(gene);
			for(int step = 0; step < Parameters.stepsPerGeneration; step++){
				sensorData = activeBoard.sensorInput();
				move = network.caluculateMove(sensorData);
				output = activeBoard.move(move);
				if(output == Impact.VOID){
					value += 0.0;
				}
				else if(output == Impact.AVOIDBIG){
					value += 20.0;
				}
				else if(output == Impact.AVOIDSMALL){
					value -= 100.0;
				}
				else if(output == Impact.CATCH){
					value += 20.0;
				}
				else if(output == Impact.HIT){
					value -= 100.0;
				}
				else if(output == Impact.SMALLPART){
					value += 5.0;
				}
				else if(output == Impact.BIGPART){
					value -= 20.0;
				}

			}
		

		gene.fitness = value;

	}


	public static void setNewBoard(){
		defaultBoard = new Board();
	}


	public static Genotype getBest() {
		return best;
	}





	public static Board getBoard() {
		return defaultBoard.clone();
	}

}