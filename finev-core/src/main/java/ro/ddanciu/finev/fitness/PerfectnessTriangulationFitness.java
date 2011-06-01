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

	private static final BigDecimal TWO = new BigDecimal(2);

	@Override
	public BigDecimal eval(Individual<Set<Triangle>> individual) {
		Set<Triangle> data = individual.getData();
		BigDecimal best = ZERO;
		for (Triangle t : data) {
			BigDecimal x = eval(t);
			if (best.compareTo(x) < 0) {
				best = x;
			}
		}
		
		return best;
	}
	
	public BigDecimal eval(Triangle t) {

		BigDecimal a = t.getE1().length();
		BigDecimal b = t.getE2().length();
		BigDecimal c = t.getE3().length();
		
		BigDecimal x = TWO.multiply(a, DECIMAL128).multiply(b, DECIMAL128).multiply(c, DECIMAL128);
		
		BigDecimal t1 = b.add(c, DECIMAL128).subtract(a, DECIMAL128);
		BigDecimal t2 = c.add(a, DECIMAL128).subtract(b, DECIMAL128);
		BigDecimal t3 = a.add(b, DECIMAL128).subtract(c, DECIMAL128);
		BigDecimal y = t1.multiply(t2, DECIMAL128).multiply(t3, DECIMAL128);
		
		BigDecimal rez = x.divide(y, DECIMAL128);
		rez = rez.setScale(MY_SCALE, MY_RND);
		return rez;
	}

}
