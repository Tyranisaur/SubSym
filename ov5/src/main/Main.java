package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		
		String distanceFile = ".\\.\\resources\\distance.txt";
		String costFile = ".\\.\\resources\\cost.txt";
		Scanner distanceScanner;
		Scanner costScanner;
		ArrayList<int[]> costLists = new ArrayList<int[]>();
		ArrayList<int[]> distanceLists = new ArrayList<int[]>();
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
				distanceLists.add(numberLine);
			}
			while(costScanner.hasNext()){
				rawLine = costScanner.nextLine().split("\t");
				numberLine = new int[rawLine.length];
				for(int i = 0; i < rawLine.length; i++){
					numberLine[i] = Integer.parseInt(rawLine[i]);
					
				}
				costLists.add(numberLine);
			}
			distanceScanner.close();
			costScanner.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//TODO use inputs
	}
}
