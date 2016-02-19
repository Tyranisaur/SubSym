package logic;

import java.util.Comparator;

public class GenoTypeComparator implements Comparator<Genotype>{

	@Override
	public int compare(Genotype arg0, Genotype arg1) {
		double difference = arg1.fitness - arg0.fitness;
		if( difference == 0){
			return 0;
		}
		if(difference > 0){
			return -1;
		}
		else{
			return 1;
		}
	}

}
