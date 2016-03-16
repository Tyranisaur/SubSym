package logic;

import java.util.Random;

public class Genotype {

	static Random random = new Random();

	protected byte[] sequence;
	public double fitness;

	public Genotype(){

		sequence = new byte[Parameters.length];
		random.nextBytes(sequence);
	}

	public int[] getWeights(){
		int[] ret = new int[sequence.length];
		for(int i = 0; i < sequence.length; i++){
			ret[i] = sequence[i] & 255;
		}
		return ret;
	}

	public Genotype crossOver(Genotype other){
		Genotype child = new Genotype();
		int fraction = 1 + (random.nextInt(sequence.length - 1));
		if( Parameters.crossOverRate < Math.random()){
			fraction = 0;
		}
		for(int i = 0; i < fraction; i++){
			child.sequence[i] = sequence[i];
		}
		for(int i = fraction; i < sequence.length; i++){
			child.sequence[i] = other.sequence[i];
		}
		return child;
	}
	/*
	public Genotype mutate(){
		Genotype child = new Genotype();
		for(int i = 0; i < sequence.length; i++){
			if(random.nextDouble() < Parameters.mutationRate){
				if(random.nextBoolean()){
					child.sequence[i] = (byte) (sequence[i] + random.nextInt(100) + 1);
				}
				else{
					child.sequence[i] = (byte) (sequence[i] - random.nextInt(100) - 1);
				}
			}
		}
		return child;

	}
	*/
	public Genotype mutate(){
		Genotype child = new Genotype();
		int bitvalue;
		for(int i = 0; i < sequence.length; i++){
			child.sequence[i] = sequence[i];
			bitvalue = 1;
			for(int j = 0; j < 8; j++){
				if(random.nextDouble() < Parameters.mutationRate){
					child.sequence[i] = (byte) ((byte)child.sequence[i] ^  (byte)bitvalue);
				}
				bitvalue = bitvalue << 1;
			}
		}
		return child;
		
	}

}
