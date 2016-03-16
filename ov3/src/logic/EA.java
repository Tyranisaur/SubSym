package logic;

import java.util.ArrayList;
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
	double totalScore;
	GenoTypeComparator comp;
	public boolean running;
	Genotype best = null;

	public EA(){
		comp = new GenoTypeComparator();
		random = new Random();
		scores = new double[Parameters.adults];
		generation = 0;
		childList = new ArrayList<Genotype>();
		adultList = new ArrayList<Genotype>();
		for(int i = 0; i < Parameters.adults; i++){

			adultList.add(new Genotype());

			Fitness.function(adultList.get(i));
		}
	}
	public void stop(){
		running = false;
	}

	public void run(){
		running = true;

		while(running && generation < 60){
			Fitness.nextGeneration();

			log();

			if(bestScore == 1.0){
				break;
			}
			sigmaScaling();
			selectAdults();

			generation++;
		}
		running = false;
	}

	private void selectAdults() {
		ArrayList<Genotype> tempAdults = new ArrayList<Genotype>();
		for(Genotype child: childList){
			Fitness.function(child);
			tempAdults.add(child);
		}
		for(Genotype adult: adultList){
			Fitness.function(adult);
			tempAdults.add(adult);
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



	

	private void sigmaScaling() {
		double[] sigmaValues = new double[adultList.size()];
		double total = 0.0;
		for(int i = 0; i < sigmaValues.length; i++){
			sigmaValues[i] = 1.0 + (adultList.get(i).fitness - averageScore) / ( 2* std);
			total += sigmaValues[i];
		}
		Genotype mother = null, father = null, child;
		double value;
		double collector;
		while(childList.size() < Parameters.adults){
			mother = null; 
			father = null;
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < sigmaValues.length; i++){
				collector += sigmaValues[i];
				if(collector >= value){
					mother = adultList.get(i);
					break;
				}
			}
			if(mother == null){
				mother = adultList.get(adultList.size() - 1);
			}
			value = random.nextDouble() * total;
			collector = 0.0;
			for(int i = 0; i < sigmaValues.length; i++){
				collector += sigmaValues[i];
				if(collector >= value){
					father = adultList.get(i);
					break;
				}
			}
			if(father == null){
				father = adultList.get(adultList.size() - 1);
			}
			child = mother.crossOver(father);
			childList.add(child.mutate());
		}


	}

	

	private void log(){
		best = null;
		averageScore = 0.0;
		totalScore = 0.0;
		bestScore = 0.0;
		std = 0.0;
		for(int i = 0; i < adultList.size(); i++){
			scores[i] = adultList.get(i).fitness;
			totalScore += scores[i];
			if(scores[i] >= bestScore){
				bestScore = scores[i];
				best = adultList.get(i);
			}
		}
		averageScore = totalScore / adultList.size();
		for(int i = 0; i < scores.length; i++){
			std += Math.pow(scores[i] - averageScore, 2);
		}
		std /= adultList.size();
		std = Math.sqrt(std);
		System.out.println(generation + "\t" + bestScore + "\t" + averageScore + "\t" + std + "\t" + best);
	}
}
