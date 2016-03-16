package logic;

public class Fitness {

	private static Board defaultBoard = new Board();
	private static Board activeBoard;
	private static NeuralNetwork network;
	private static CellType[] sensorData;
	private static CellType output;
	private static Genotype best;
	
	
	public static void setBest(Genotype b){
		best = b;
	}
	
	public static Board getBoard(){
		return defaultBoard.clone();
	}
	
	public static Direction[] getMoves(){
		activeBoard = defaultBoard.clone();
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
		activeBoard = defaultBoard.clone();
		Direction move;
		network = new NeuralNetwork(gene);
		double value = 0.0;
		for(int step = 0; step < 60; step++){
			sensorData = activeBoard.sensorInput();
			move = network.caluculateMove(sensorData);
			System.out.println(""+ step + "\t" + move);
			output = activeBoard.move(move);
			if(output == CellType.FOOD){
				value += 1./60;
			}
			else if(output == CellType.POISON){
				value -= 1./60;
			}
			else if(output == CellType.EMPTY){
				value -= 1./200;
			}
			
		}
		gene.fitness = Math.max(0.0, value);
	
	}
	

	public static void nextGeneration() {
		if(Parameters.dynamicBoard){
			defaultBoard = new Board();
		}
		
	}
}
