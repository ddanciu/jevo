package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ZERO;
import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

import ro.ddanciu.finite.elements.api.Constants;
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
		
		BigDecimal x = TWO.multiply(a, MY_CNTX).multiply(b, MY_CNTX).multiply(c, MY_CNTX);
		
		BigDecimal t1 = b.add(c, MY_CNTX).subtract(a, MY_CNTX);
		BigDecimal t2 = c.add(a, MY_CNTX).subtract(b, MY_CNTX);
		BigDecimal t3 = a.add(b, MY_CNTX).subtract(c, MY_CNTX);
		BigDecimal y = t1.multiply(t2, MY_CNTX).multiply(t3, MY_CNTX);
		
		return x.divide(y, MathContext.DECIMAL128)
				.setScale(MY_SCALE, MY_RND);
	}

}
