package logic;

import java.util.ArrayList;

public class SurpSeqGene extends Genotype {

	private byte[] sequence;
	int symbols;
	
	
	public SurpSeqGene(int length, int symbols) {
		this.symbols = symbols;
		sequence = new byte[length];
		for(int i = 0; i < length; i++){
			sequence[i] = (byte) random.nextInt(symbols);
		}
	}
	
	public SurpSeqGene mutate(double p){
		SurpSeqGene child = new SurpSeqGene(sequence.length, symbols);
		for(int i = 0; i < sequence.length; i++){
			child.sequence[i] = sequence[i];
			if(random.nextDouble() < p){
				child.sequence[i] = (byte) random.nextInt(symbols);
			}
		}
		return child;
	}
	public ArrayList<Integer> getSequence(){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int i = 0; i < sequence.length; i++){
			ret.add((int) sequence[i]);
		}
		return ret;
	}
	
	public String toString(){
		return sequence.toString();
	}

}
