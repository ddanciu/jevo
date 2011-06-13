package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.util.Set;

import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

/**
 * @author dan
 */
public class PerfectnessTriangulationFitness implements FitnessFunction<BigDecimal, Set<Triangle>> {

	@Override
	public BigDecimal eval(Individual<Set<Triangle>> individual) {
		Set<Triangle> data = individual.getData();
		BigDecimal sum = ZERO;
		for (Triangle t : data) {
			BigDecimal x = t.perfectness();
			double xd = x.doubleValue();
			if (xd < 2.1) {
				// DO NOTHING x = x; 
			} else if (xd < 2.2) {
				x = x.multiply(new BigDecimal("5"), DECIMAL128);
			} else if (xd < 2.3) {
				x = x.multiply(new BigDecimal("10"), DECIMAL128);
			} else if (xd < 2.4) {
				x = x.multiply(new BigDecimal("30"), DECIMAL128);
			} else {
				x = x.multiply(new BigDecimal("100"), DECIMAL128);
			}
			
			sum = sum.add(x, DECIMAL128);
		}
		
		return sum.divide(new BigDecimal(data.size()), DECIMAL128);
	}
	

}
