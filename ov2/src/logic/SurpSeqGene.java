package logic;

import java.util.ArrayList;

public class SurpSeqGene extends Genotype {

	private byte[] sequence;
	
	
	public SurpSeqGene() {
		sequence = new byte[Parameters.length];
		for(int i = 0; i < sequence.length; i++){
			sequence[i] = (byte) random.nextInt(Parameters.symbols);
		}
	}
	
	public Genotype mutate(){
		SurpSeqGene child = new SurpSeqGene();
		for(int i = 0; i < sequence.length; i++){
			child.sequence[i] = sequence[i];
			if(random.nextDouble() < Parameters.mutationRate){
				child.sequence[i] = (byte) random.nextInt(Parameters.symbols);
			}
		}
		return child;
	}
	public ArrayList<Character> getSequence(){
		ArrayList<Character> ret = new ArrayList<Character>();
		for(int i = 0; i < sequence.length; i++){
			ret.add((char) sequence[i]);
		}
		return ret;
	}
	
	public String toString(){
		String ret = "" + sequence[0];
		for(int i = 1; i < sequence.length; i++){
			ret += ", " + sequence[i];
		}
		return ret;
	}

}
