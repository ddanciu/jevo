package ro.ddanciu.finev.operators.utils;

import java.util.Set;

public class Random {
	

	private java.util.Random randomGenerator;
	
	public void setRandomGenerator(java.util.Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}
	
	public <I> I choice(Set<I> supply) {
		
		if (supply.isEmpty()) {
			return null;
		}
		
		int pos = randomGenerator.nextInt(supply.size());
		int i = 0;
		for (I t : supply) {
			if (i++ == pos) {
				return t;
			}
		}
		
		assert false : "Illegal situation which should never be reached!";
		return null;
	}
	
	public boolean rate(float coefficient) {
		return randomGenerator.nextFloat() > coefficient;
	}

}
