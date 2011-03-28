package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ZERO;
import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;

import java.math.BigDecimal;
import java.util.Set;

import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

/**
 * @author dan
 */
public class PerimeterTriangulationFitness implements FitnessFunction<BigDecimal, Set<Triangle>> {

	@Override
	public BigDecimal eval(Individual<Set<Triangle>> individual) {
		Set<Triangle> data = individual.getData();
		BigDecimal total = ZERO;
		for (Triangle t : data) {
			BigDecimal x = eval(t);
			total = total.add(x, MY_CNTX);
		}
		
		return total;
	}
	
	public BigDecimal eval(Triangle t) {

		BigDecimal a = t.getE1().length();
		BigDecimal b = t.getE2().length();
		BigDecimal c = t.getE3().length();
		
		BigDecimal x = a.add(b, MY_CNTX).add(c, MY_CNTX);
		return x;
	}

}
