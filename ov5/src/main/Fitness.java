package main;

public class Fitness {

	public static void evaluate(Genotype gene){
		int cost = 0;
		int distance = 0;
		
		int[] path = gene.getPath();
		int prevCity = path[path.length - 1];
		int currentCity;
		for(int i = 0; i < path.length; i++){
			currentCity = path[i];
			
			if(currentCity < prevCity){
				cost += Parameters.costLists.get(prevCity)[currentCity];
				distance += Parameters.distanceLists.get(prevCity)[currentCity];
			}
			else{
				cost += Parameters.costLists.get(currentCity)[prevCity];
				distance += Parameters.distanceLists.get(currentCity)[prevCity];
			}
			prevCity = currentCity;
		}
		gene.costScore = cost;
		gene.distanceScore = distance;
		
	}
}
