package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genotype implements Comparable<Genotype>{
	private static  Random random = new Random();
	private byte[] sequence;
	public int costScore;
	public int distanceScore;
	public int rank;
	public double crowdingDistance;


	public Genotype(){
		sequence = new byte[Parameters.length];
		
		for(byte i = 0; i < sequence.length; i++){
			sequence[i] = i;
		}
		arrayShuffle(sequence);
	}
	public Genotype mutate(){
		
		Genotype ret = new Genotype();
		ret.sequence = this.sequence.clone();
		
		if(random.nextDouble() < Parameters.mutationProbability){
			int firstcut = random.nextInt(sequence.length);
			int secondcut = random.nextInt(sequence.length);
			ArrayList<Byte> reverse = new ArrayList<Byte>();
			for(int i = firstcut; i != secondcut; i = (i + 1) % sequence.length){
				reverse.add(0,sequence[i]);
			}
			for(int i = firstcut; i != secondcut; i = (i + 1) % sequence.length){
				ret.sequence[i] = reverse.remove(0);
			}
			
		}
		
		
		
		return ret;
	}

	public Genotype crossOver(Genotype other){
		Genotype ret = new Genotype();
		if(random.nextDouble() > Parameters.crossOverProbaility){
			ret.sequence = this.sequence.clone();
			return ret;
		}
		
		byte[] newSequence = new byte[sequence.length];
		Arrays.fill(newSequence, (byte)-1);
		int firstcut = random.nextInt(sequence.length);
		int secondcut = random.nextInt(sequence.length);
		for(int i = firstcut; i != secondcut; i = (i + 1) % sequence.length){
			newSequence[i] = this.sequence[i];
		}
		int index = (arrayFind(other.sequence, sequence[ Math.floorMod(secondcut - 1, sequence.length) ] ) + 1) % sequence.length;
		int arrayIndex = secondcut;
		while(arrayContains(newSequence, (byte)-1)){
			if(!arrayContains(newSequence, other.sequence[index])){
				newSequence[arrayIndex] = other.sequence[index];
				arrayIndex = (arrayIndex + 1) % sequence.length;
			}
			index = (index + 1) % sequence.length;
		}
		ret.sequence = newSequence;

		return ret;

	}
	public int[] getPath(){
		int[] ret = new int[sequence.length];
		for(int i = 0; i< sequence.length; i++){
			ret[i] = sequence[i];
		}
		return ret;
	

	}
	public int domination(Genotype other){
		int ret = 0;
		if(this.costScore < other.costScore){
			ret = 1;
		}
		else if(this.costScore > other.costScore){
			ret = -1;
		}
		if(this.distanceScore < other.distanceScore){
			ret++;
		}
		else if(this.distanceScore > other.distanceScore){
			ret--;
		}
		return ret;
	}

	private boolean arrayContains(byte[] array, byte element){
		for(int i = 0; i < array.length; i++){
			if(array[i] == element){
				return true;
			}
		}
		return false;
	}
	private static void arrayShuffle(byte[] array){
		byte temp;
		int index;
		for(int i = array.length - 1; i > 0; i--){
			index = random.nextInt(i + 1);
			temp = array[i];
			array[i] = array[index];
			array[index] = temp;

		}
	}
	private int arrayFind(byte[] array, byte element){
		for(int i = 0; i < array.length; i++){
			if(array[i] == element){
				return i;
			}
		}
		throw new IllegalStateException("looking for element not present");
	}
	@Override
	public int compareTo(Genotype other) {
		return this.distanceScore -  other.distanceScore;
	}
}
