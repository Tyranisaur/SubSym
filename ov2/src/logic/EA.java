package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class EA {

	ArrayList<Genotype> childList;
	ArrayList<Genotype> adultList;
	int generation;
	double[] scores;
	double bestScore;
	Random random;
	double std;
	double averageScore;
	GenoTypeComparator comp;
	
	public EA(){
		comp = new GenoTypeComparator();
		random = new Random();
		scores = new double[Parameters.adults];
		generation = 0;
		childList = new ArrayList<Genotype>();
		adultList = new ArrayList<Genotype>();
		for(int i = 0; i < Parameters.adults; i++){
			if(Parameters.problem == Problem.GLOBALSEQ || Parameters.problem == Problem.LOCALSEQ){
				adultList.add(new SurpSeqGene());
			}
			else{
				adultList.add(new Genotype());
			}
			Fitness.function(adultList.get(i));
		}
	}
	
	public void run(){
		
		while(true){
			
			log();
			if(bestScore == 1.0){
				break;
			}
			breed();
			selectAdults();
			
			generation++;
		}
	}
	
	private void selectAdults() {
		ArrayList<Genotype> tempAdults = new ArrayList<Genotype>();
		for(Genotype child: childList){
			Fitness.function(child);
			tempAdults.add(child);
		}
		if(Parameters.adultsSurvive){
			tempAdults.addAll(adultList);
		}
		int surplus = tempAdults.size() - Parameters.adults;
		if(surplus > 0){
			tempAdults.sort(comp);
		}
		adultList.clear();
		while(adultList.size() < Parameters.adults){
			adultList.add(tempAdults.remove(tempAdults.size() - 1 ));
		}
		
	}

	private void breed() {
		childList.clear();
		switch(Parameters.selectionMode){
		case FITNESS:
			fitnessScaling();
			break;
		case SIGMA:
			sigmaScaling();
			break;
		case TOURNAMENT:
			tournamentSelection();
			break;
		case UNIFORM:
			uniformSelection();
			break;
		default:
			break;
		
		}
		
	}

	private void uniformSelection() {
		Genotype father;
		Genotype mother;
		Genotype child;
		while(childList.size() < Parameters.birthRate){
			father = adultList.get(random.nextInt(adultList.size()));
			mother = adultList.get(random.nextInt(adultList.size()));
			child = mother.crossOver(father);
			childList.add(child.mutate());
		}
		
	}

	private void tournamentSelection() {
		HashSet<Genotype> group = new HashSet<Genotype>();
		Genotype[] array = new Genotype[Parameters.tournamentSize];
		Genotype mother;
		Genotype father;
		Genotype child;
		int indexOfBest;
		double bestScore;
		while(childList.size() < Parameters.birthRate){
			while(group.size() < Parameters.tournamentSize){
				group.add(adultList.get(random.nextInt(adultList.size())));
			}
			group.toArray(array);
			indexOfBest = 0;
			bestScore = 0.0;
			for(int i = 0; i < array.length; i++){
				if(array[i].fitness > bestScore){
					indexOfBest = i;
					bestScore = array[i].fitness;
				}
			}
			if(random.nextDouble() < Parameters.tournamentPValue){
				mother = array[indexOfBest];
			}
			else{
				int randomIndex = random.nextInt(array.length - 1);
				mother = array[ randomIndex >= indexOfBest ? randomIndex + 1 : randomIndex];
			}
			group.clear();
			while(group.size() < Parameters.tournamentSize){
				group.add(adultList.get(random.nextInt(adultList.size())));
			
			}
			group.toArray(array);
			indexOfBest = 0;
			bestScore = 0.0;
			for(int i = 0; i < array.length; i++){
				if(array[i].fitness > bestScore){
					indexOfBest = i;
					bestScore = array[i].fitness;
				}
			}
			if(random.nextDouble() < Parameters.tournamentPValue){
				father = array[indexOfBest];
			}
			else{
				int randomIndex = random.nextInt(array.length - 1);
				father = array[ randomIndex >= indexOfBest ? randomIndex + 1 : randomIndex];
			}
			group.clear();
			child = mother.crossOver(father);
			childList.add(child.mutate());
		}
		
		
	}

	private void sigmaScaling() {
		double[] sigmaValues = new double[adultList.size()];
		double total = 0.0;
		for(int i = 0; i < sigmaValues.length; i++){
			sigmaValues[i] = 1. + (adultList.get(i).fitness - averageScore)/(2*std);
			total += sigmaValues[i];
		}
		Genotype mother = null, father = null, child;
		double value;
		double collector;
		while(childList.size() < Parameters.birthRate){
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < sigmaValues.length; i++){
				collector += sigmaValues[i];
				if(collector > value){
					mother = adultList.get(i);
					break;
				}
			}
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < sigmaValues.length; i++){
				collector += sigmaValues[i];
				if(collector > value){
					father = adultList.get(i);
					break;
				}
			}
			child = mother.crossOver(father);
			childList.add(child.mutate());
		}
		
		
	}

	private void fitnessScaling() {
		double[] values = new double[adultList.size()];
		double total = 0.0;
		for(int i = 0; i < values.length; i++){
			values[i] = adultList.get(i).fitness/averageScore;
			total += values[i];
		}
		Genotype mother = null, father = null, child;
		double value;
		double collector;
		while(childList.size() < Parameters.birthRate){
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < values.length; i++){
				collector += values[i];
				if(collector > value){
					mother = adultList.get(i);
					break;
				}
			}
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < values.length; i++){
				collector += values[i];
				if(collector > value){
					father = adultList.get(i);
					break;
				}
			}
			child = mother.crossOver(father);
			childList.add(child.mutate());
		}
	}

	private void log(){
		Genotype best = null;
		averageScore = 0.0;
		bestScore = 0.0;
		std = 0.0;
		for(int i = 0; i < adultList.size(); i++){
			scores[i] = adultList.get(i).fitness;
			averageScore += scores[i];
			if(scores[i] >= bestScore){
				bestScore = scores[i];
				best = adultList.get(i);
			}
		}
		averageScore /= adultList.size();
		for(int i = 0; i < scores.length; i++){
			std += Math.pow(scores[i] - averageScore, 2);
		}
		std /= adultList.size();
		std = Math.sqrt(std);
		System.out.println(generation + "\t" + bestScore + "\t" + averageScore + "\t" + std + "\t" + best);
	}
}
