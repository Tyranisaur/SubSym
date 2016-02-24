package logic;

import java.util.ArrayList;

public class Fitness {


	public static double function(Genotype genotype){
		double ret = 0.0;
		switch(Parameters.problem){
		case LOLZ:
			ret = lolzScoring(genotype);
			break;
		case ONEMAX:
			ret = oneMaxScoring(genotype);
			break;
		case LOCALSEQ:
			ret = localSurpSeqScoring((SurpSeqGene) genotype);
			break;
		case GLOBALSEQ:
			ret = globalSurpSeqScoring((SurpSeqGene) genotype);
			break;
		default:
			ret = Math.random();

		}
		genotype.fitness = ret;
		return ret;
	}

	private static double globalSurpSeqScoring(SurpSeqGene genotype) {
		double score = 1.0;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < Parameters.length - 1; i++){
			table.add(new ArrayList<String>());
		}
		String s;
		int space;
		outer:
			for(int stop = 1; stop < Parameters.length; stop++){
				for(int start = 0; start < stop; start++){
					space = stop - start - 1;
					s = "" + genotype.getSequence().get(start) + genotype.getSequence().get(stop);
					if(table.get(space).contains(s)){
						break outer;
					}
					table.get(space).add(s);


				}
				score++;
			}

		return score/Parameters.length;
	}
	private static double localSurpSeqScoring(SurpSeqGene genotype){
		double score = 1.0;
		ArrayList<String> list = new ArrayList<String>();
		String s;
		for(int i = 0; i < Parameters.length - 1; i++){
			s = "" + genotype.getSequence().get(i) + genotype.getSequence().get(i + 1);
			if(list.contains(s)){
				break;
			}
			list.add(s);
			score++;
		}
		return score/Parameters.length;
	}

	private static double lolzScoring(Genotype genotype) {
		double score = 0.0;
		String s = genotype.toString();
		char leading = s.charAt(0);
		for(int i = 0; i < s.length(); i++){
			if(Parameters.lolzCap > -1){
				if(leading == '0'){
					if(score == Parameters.lolzCap){
						break;
					}
				}

			}
			if(s.charAt(i) == leading){
				score++;
			}
			else{
				break;
			}
		}
		return score/genotype.toString().length();
	}

	private static double oneMaxScoring(Genotype genotype){
		double score = 0.0;
		if(Parameters.oneMaxTarget == null){
			for(int i = 0; i < genotype.toString().length(); i++){
				if(genotype.toString().charAt(i) == '1'){
					score++;
				}
			}
		}
		else{
			for(int i = 0; i < genotype.toString().length(); i++){
				if(genotype.toString().charAt(i) == Parameters.oneMaxTarget.charAt(i)){
					score++;
				}
			}
		}
		return score/genotype.toString().length();
	}
}
