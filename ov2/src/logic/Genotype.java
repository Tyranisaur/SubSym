package logic;

import java.util.Random;

public class Genotype {
	
	static Random random = new Random();

	protected byte[] sequence;
	private int bits;
	public double fitness;
	
	public Genotype(){
		this.bits = Parameters.length;
		int bytes = bits/8;
		if(bits % 8 != 0){
			bytes++;
		}
		sequence = new byte[bytes];
		random.nextBytes(sequence);
	}
	
	public String toString(){
		String st = "";
		String tot = "";
		for(int i = 0; i < sequence.length; i++){
			st = Integer.toBinaryString(sequence[i] & 255);
			while(st.length()% 8 != 0){
				st = "0" + st;
			}
			tot += st;
			
		}
		String ret = "";
		for(int i = 0; i < bits; i++){
			ret+= tot.charAt(i);
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
