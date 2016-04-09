package logic;

public class Parameters {

	//10 + 4 + 4 + 4 edge weights
	//4 bias weights
	//4 gains
	//4 time constants
	public static int length = 34;
	public static double crossOverRate = .05;
	public static double mutationRate = 0.05;
	public static double[] thresholds = {0.2, 0.4, 0.6, 0.8};
	public static int adults = 800;
	public static int stepsPerGeneration = 600;
	public static int generationsPerRun = 100;
	public static int millisPerVisualiztionStep = 100;
	public static double tournamentPValue = 0.02;
	public static int tournamentSize = 80;

}
