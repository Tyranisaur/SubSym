package main;

import java.util.ArrayList;

public class Parameters {

	public static ArrayList<int[]> costLists;
	public static ArrayList<int[]> distanceLists;
	public static int length;
	public static double mutationProbability = 0.55;
	public static double crossOverProbaility = 0.4;
	public static int populationSize = 100;
	public static int generations = 5000;
	public static int tournamentSize = populationSize / 10;
	public static double tournamentPValue = 0.05;
}
