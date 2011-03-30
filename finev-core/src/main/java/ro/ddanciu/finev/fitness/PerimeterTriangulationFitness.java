package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ZERO;
import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;
import static ro.ddanciu.finite.elements.api.utils.TriangleUtils.perimeter;

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
			BigDecimal x = perimeter(t);
			total = total.add(x, MY_CNTX);
		}
		
		return total;
	}
	
	

}
