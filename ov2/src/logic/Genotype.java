package logic;

import java.util.Random;

public class Genotype {
	
	static Random random = new Random();

	private byte[] sequence;
	private int bits;
	
	protected Genotype(){
		
	}
	public Genotype(int bits){
		int bytes = bits/8;
		if(bits % 8 != 0){
			bytes++;
		}
		this.bits = bits;
		sequence = new byte[bytes];
		for(int i = 0; i < bytes; i++){
			sequence[i] = (byte) random.nextInt(256);
		}
	}
	
	public String toString(){
		String st = "";
		for(int i = 0; i < sequence.length; i++){
			st += Integer.toBinaryString(sequence[i]);
			
		}
		String ret = "";
		for(int i = 0; i < bits; i++){
			ret+= st.charAt(i);
		}
		return ret;
	}
	
	public Genotype crossOver(Genotype other, double p){
		Genotype child = new Genotype(bits);
		int fraction = (int) (sequence.length*Math.random());
		if( p > Math.random()){
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
	public Genotype mutate(double p){
		Genotype child = new Genotype(bits);
		int bitvalue;
		for(int i = 0; i < sequence.length; i++){
			child.sequence[i] = sequence[i];
			bitvalue = 1;
			for(int j = 0; j < 8; j++){
				if(random.nextDouble() < p){
					child.sequence[i] = (byte) (child.sequence[i] ^ (byte) bitvalue);
				}
				bitvalue = bitvalue << 2;
			}
		}
		return child;
		
	}
	
}
