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
public class MinimalWeightFitness implements FitnessFunction<BigDecimal, Set<Triangle>> {

	@Override
	public BigDecimal eval(Individual<Set<Triangle>> individual) {
		Set<Triangle> data = individual.getData();
		BigDecimal total = ZERO;
		for (Triangle t : data) {
			BigDecimal x = t.perimeter();
			total = total.add(x, DECIMAL128);
		}
		
		total = total.setScale(MY_SCALE, MY_RND);
		return total;
	}
	
	

}
