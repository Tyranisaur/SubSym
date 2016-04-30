package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;



public class EA {

	private ArrayList<Genotype> population;
	private ArrayList<Genotype> childList;
	private ArrayList<ArrayList<Genotype>> fronts;
	private int generation;
	private Random random;

	public EA(){
		random = new Random();

		population = new ArrayList<Genotype>();
		for(int i = 0; i < Parameters.populationSize * 2; i++){
			Genotype gene = new Genotype();
			Fitness.evaluate(gene);
			population.add(gene);
		}
		generation = 0;
		childList = new ArrayList<Genotype>();
	}

	public void run(){

		while(generation < Parameters.generations){
			calculateCrowding(population);
			trimPopulation();

			tournamentSelection();
			population.addAll(childList);
			childList.clear();
			generation++;
		}
		calculateCrowding(population);
		trimPopulation();
		calculateCrowding(population);
		printResults();
	}
	private void printResults() {
		for(Genotype gene: fronts.get(0)){
			System.out.println(gene.distanceScore + "\t" + gene.costScore + "\t"+ gene.distanceScore + "\t" + gene.costScore);
		}
		for(Genotype gene: population){
			if(!fronts.get(0).contains(gene)){
				System.out.println(gene.distanceScore + "\t" + gene.costScore);
			}
		}

	}

	private void trimPopulation() {
		ArrayList<Genotype> newPopulation = new ArrayList<Genotype>();
		int currentFront = 0;
		while(fronts.get(currentFront).size() + newPopulation.size() <= Parameters.populationSize){
			newPopulation.addAll(fronts.get(currentFront));
			currentFront++;
		}
		int deficit = Parameters.populationSize - newPopulation.size();
		if(deficit < 0){
			throw new IllegalStateException("poplulation size error");
		}
		else if(deficit == 0){
			return;
		}
		int indexOfBest = 0;
		double bestDistance = 0.0;
		while(newPopulation.size() < Parameters.populationSize){
			for(int i = 0; i < fronts.get(currentFront).size(); i++){
				if(fronts.get(currentFront).get(i).crowdingDistance > bestDistance){
					indexOfBest = i;
					bestDistance = fronts.get(currentFront).get(i).crowdingDistance;
				}
			}
			newPopulation.add(fronts.get(currentFront).remove(indexOfBest));
			bestDistance = 0.0;
			indexOfBest = 0;
		}
		population = newPopulation;

	}

	public void tournamentSelection(){
		HashSet<Genotype> group = new HashSet<Genotype>();
		Genotype[] array = new Genotype[Parameters.tournamentSize];
		Genotype mother;
		Genotype father;
		Genotype child;
		int indexOfBest;

		while(childList.size() < Parameters.populationSize){
			while(group.size() < Parameters.tournamentSize){
				group.add(population.get(random.nextInt(population.size())));
			}
			group.toArray(array);
			indexOfBest = 0;
			for(int i = 0; i < array.length; i++){
				if(array[i].domination(array[indexOfBest]) > 0){
					indexOfBest = i;
				}
			}
			if(random.nextDouble() > Parameters.tournamentPValue){
				mother = array[indexOfBest];
			}
			else{
				int randomIndex = random.nextInt(array.length - 1);
				mother = array[ randomIndex >= indexOfBest ? randomIndex + 1 : randomIndex];
			}
			group.clear();
			while(group.size() < Parameters.tournamentSize){
				group.add(population.get(random.nextInt(population.size())));

			}
			group.toArray(array);
			indexOfBest = 0;
			for(int i = 0; i < array.length; i++){
				if(array[i].domination(array[indexOfBest]) > 1){
					indexOfBest = i;
				}
			}
			if(random.nextDouble() > Parameters.tournamentPValue){
				father = array[indexOfBest];
			}
			else{
				int randomIndex = random.nextInt(array.length - 1);
				father = array[ randomIndex >= indexOfBest ? randomIndex + 1 : randomIndex];
			}
			group.clear();
			child = mother.crossOver(father).mutate();
			Fitness.evaluate(child);
			childList.add(child);
		}
	}
	private void calculateCrowding(ArrayList<Genotype> population) {

		@SuppressWarnings("unchecked")
		ArrayList<Genotype> workingList = (ArrayList<Genotype>) population.clone();
		HashMap<Genotype, Integer> dominationAmounts = new HashMap<Genotype, Integer>();
		HashMap<Genotype, ArrayList<Genotype>> dominationSets = new HashMap<Genotype, ArrayList<Genotype>>();
		fronts = new ArrayList<ArrayList<Genotype>>();
		for(Genotype g: workingList){
			dominationAmounts.put(g, 0);
			dominationSets.put(g, new ArrayList<Genotype>());
		}
		Genotype p;
		while(workingList.size() > 0){
			p = workingList.remove(0);
			for(Genotype other: population){
				if(p == other){
					continue;
				}

				if( p.domination(other) > 0){
					dominationSets.get(p).add(other);
				}
				else if( p.domination(other) < 0){
					dominationAmounts.replace(p, dominationAmounts.get(p) + 1);
				}
			}
			if(dominationAmounts.get(p) == 0){
				p.rank = 0;
				if(fronts.size() == 0){
					fronts.add(new ArrayList<Genotype>());
				}
				fronts.get(0).add(p);
			}
		}
		int currentFront = 0;
		ArrayList<Genotype> newFront;
		while( fronts.size() > currentFront){
			newFront = new ArrayList<Genotype>();
			for(Genotype frontItem: fronts.get(currentFront)){
				for(Genotype dominatedItem: dominationSets.get(frontItem)){
					dominationAmounts.replace(dominatedItem, dominationAmounts.get(dominatedItem) - 1);
					if(dominationAmounts.get(dominatedItem)== 0){
						dominatedItem.rank = currentFront + 1;
						newFront.add(dominatedItem);
					}
				}
			}
			if(newFront.size() > 0){
				fronts.add(newFront);
			}
			currentFront++;

		}
		for(ArrayList<Genotype> front: fronts){
			calculateCD(front);
		}


	}

	public void calculateCD(ArrayList<Genotype> front){
		Collections.sort(front);
		front.get(0).crowdingDistance = Double.MAX_VALUE;
		front.get(front.size() - 1).crowdingDistance = Double.MAX_VALUE;
		for(int i = 1; i < front.size() - 1; i++){
			try{
				front.get(i).crowdingDistance += (front.get(i+1).costScore - front.get(i-1).costScore)/(front.get(front.size() -1).costScore - front.get(0).costScore);
			}
			catch(ArithmeticException e){

			}
			try{
				front.get(i).crowdingDistance += (front.get(i+1).distanceScore - front.get(i-1).distanceScore)/(front.get(front.size() -1).distanceScore - front.get(0).distanceScore);

			}
			catch(ArithmeticException e){
			}
		}
	}

	public int crowdindOperator(Genotype first, Genotype second){
		if(first.rank < second.rank){
			return 1;
		}
		if(first.rank > second.rank){
			return -1;
		}
		if(first.crowdingDistance > second.crowdingDistance){
			return 1;
		}
		if(first.crowdingDistance < second.crowdingDistance){
			return -1;
		}
		return 0;
	}
}