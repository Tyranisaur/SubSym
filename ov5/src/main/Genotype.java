package main;

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
		for(int i = 0; i < sequence.length; i++){
			sequence[i] = (byte) i;
		}
		arrayShuffle(sequence);
	}
	public Genotype mutate(){
		Genotype ret = new Genotype();
		ret.sequence = this.sequence.clone();
		byte temp;
		for(int i = 0; i < sequence.length; i++){
			if(random.nextDouble() < Parameters.mutationProbability){
				temp = ret.sequence[i];
				ret.sequence[i] = ret.sequence[(i + 1) % sequence.length];
				ret.sequence[(i + 1) % sequence.length] = temp;
			}
		}
		
		return ret;
	}
	
	public Genotype crossOver(Genotype other){
		Genotype ret = new Genotype();
		if(random.nextDouble() < Parameters.crossOverProbaility){
			ret.sequence = this.sequence.clone();
			return ret;
		}
		int thisIndex = 0;
		int otherIndex = sequence.length - 1;
		int prefixlength = 0;
		int suffixlength = 0;
		byte[] newSequence = new byte[Parameters.length];
		Arrays.fill(newSequence, (byte)-1);
		while(prefixlength + suffixlength < sequence.length){
			if(arrayContains(newSequence, sequence[thisIndex])){
				thisIndex++;
			}
			else{
				newSequence[prefixlength] = sequence[thisIndex];
				prefixlength++;
				thisIndex++;
			}
			if(prefixlength + suffixlength == sequence.length){
				break;
			}
			if(arrayContains(newSequence, other.sequence[otherIndex])){
				otherIndex--;
			}
			else{
				newSequence[sequence.length - 1 - suffixlength] = other.sequence[otherIndex];
				suffixlength++;
				otherIndex--;
			}
			
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
		if(this.costScore == other.costScore && other.distanceScore == this.distanceScore){
			return this.hashCode() - other.hashCode();
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
	private void arrayShuffle(byte[] array){
		byte temp;
		int index;
		for(int i = array.length - 1; i > 0; i--){
			index = random.nextInt(i + 1);
			temp = array[i];
			array[i] = array[index];
			array[index] = temp;
			
		}
	}
	@Override
	public int compareTo(Genotype other) {
		return this.distanceScore -  other.distanceScore;
	}
}
