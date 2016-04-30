package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class InputReader {

	
	public static void readInput(){
		String distanceFile = ".\\.\\resources\\distance.txt";
		String costFile = ".\\.\\resources\\cost.txt";
		Scanner distanceScanner;
		Scanner costScanner;
		Parameters.costLists = new ArrayList<int[]>();
		Parameters.distanceLists = new ArrayList<int[]>();
		try {
			distanceScanner = new Scanner(new FileReader(distanceFile));
			costScanner = new Scanner(new FileReader( costFile));
			String[] rawLine;
			int[] numberLine;
			while (distanceScanner.hasNext()){
				rawLine = distanceScanner.nextLine().split("\t");
				numberLine = new int[rawLine.length];
				for(int i = 0; i < rawLine.length; i++){
					numberLine[i] = Integer.parseInt(rawLine[i]);
				}
				Parameters.distanceLists.add(numberLine);
			}
			Parameters.length = Parameters.distanceLists.size();
			while(costScanner.hasNext()){
				rawLine = costScanner.nextLine().split("\t");
				numberLine = new int[rawLine.length];
				for(int i = 0; i < rawLine.length; i++){
					numberLine[i] = Integer.parseInt(rawLine[i]);
					
				}
				Parameters.costLists.add(numberLine);
			}
			distanceScanner.close();
			costScanner.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
