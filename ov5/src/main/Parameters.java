package main;

import java.util.ArrayList;

public class Parameters {

	public static ArrayList<int[]> costLists;
	public static ArrayList<int[]> distanceLists;
	public static int length;
	public static double mutationProbability = 0.09;
	public static double crossOverProbaility = 0.05;
	public static int populationSize = 1300;
	public static int generations = 400;
	public static int tournamentSize = generations / 10;
	public static double tournamentPValue = 0.05;
}
