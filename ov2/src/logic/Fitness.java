package logic;

import java.util.ArrayList;

public class Fitness {

	public static String oneMaxTarget = null;
	public static int lolzCap = -1;
	public static SequenceType seqType = SequenceType.LOCAL;

	public static double function(Genotype genotype, Problem problem){
		switch(problem){
		case LOLZ:
			return lolzScoring(genotype);
		case ONEMAX:
			return oneMaxScoring(genotype);
		case SURPSEQ:
			if(seqType == SequenceType.LOCAL){
				return localSurpSeqScoring(genotype);
			}
			else{
				return globalSurpSeqScoring(genotype);
			}
		default:
			return Math.random();

		}
	}

	private static double globalSurpSeqScoring(Genotype genotype) {
		double score = 1.0;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < genotype.toString().length() - 1; i++){
			table.add(new ArrayList<String>());
		}
		String s;
		int space;
		outer:
			for(int stop = 1; stop < genotype.toString().length(); stop++){
				for(int start = 0; start < stop; start++){
					space = stop - start - 1;
					s = "" + genotype.toString().charAt(start) + genotype.toString().charAt(stop);
					if(table.get(space).contains(s)){
						break outer;
					}
					table.get(space).add(s);


				}
				score++;
			}

		return score/genotype.toString().length();
	}
	private static double localSurpSeqScoring(Genotype genotype){
		double score = 1.0;
		ArrayList<String> list = new ArrayList<String>();
		String s;
		for(int i = 0; i < genotype.toString().length() - 1; i++){
			s = "" + genotype.toString().charAt(i) + genotype.toString().charAt(i + 1);
			if(list.contains(s)){
				break;
			}
			list.add(s);
			score++;
		}
		return score/genotype.toString().length();
	}

	private static double lolzScoring(Genotype genotype) {
		double score = 0.0;
		char leading = genotype.toString().charAt(0);
		for(int i = 0; i < genotype.toString().length(); i++){
			if(lolzCap > -1){
				if(leading == '0'){
					if(score == lolzCap){
						break;
					}
				}

			}
			if(genotype.toString().charAt(i) == leading){
				score++;
			}
		}
		return score/genotype.toString().length();
	}

	private static double oneMaxScoring(Genotype genotype){
		double score = 0.0;
		if(oneMaxTarget == null){
			for(int i = 0; i < genotype.toString().length(); i++){
				if(genotype.toString().charAt(i) == '1'){
					score++;
				}
			}
		}
		else{
			for(int i = 0; i < genotype.toString().length(); i++){
				if(genotype.toString().charAt(i) == oneMaxTarget.charAt(i)){
					score++;
				}
			}
		}
		return score/genotype.toString().length();
	}
}
