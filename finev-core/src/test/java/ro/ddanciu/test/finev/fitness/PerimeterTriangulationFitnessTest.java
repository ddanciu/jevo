/**
 * 
 */
package ro.ddanciu.test.finev.fitness;
import static org.junit.Assert.assertEquals;
import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ro.ddanciu.finev.fitness.PerimeterTriangulationFitness;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

/**
 * @author dan
 *
 */
public class PerimeterTriangulationFitnessTest {
	
	private static final BigDecimal TWO = new BigDecimal(2);

	private static final BigDecimal SQRT_TWO = new BigDecimal(Math.sqrt(2.00));
	
	private FitnessFunction<BigDecimal, Set<Triangle>> target = new PerimeterTriangulationFitness();

	@Test
	public void once() {
		Set<Triangle> triangles = new HashSet<Triangle>();
		triangles.add(new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 0)));
		Individual<Set<Triangle>> individual = Individual.Factory.newInstance(triangles );
		BigDecimal fitness = target.eval(individual);
		
		assertEquals("Perimeter failed!", SQRT_TWO.add(TWO, MY_CNTX), fitness);
	}
	

	@Test
	public void twice() {
		Set<Triangle> triangles = new HashSet<Triangle>();
		triangles.add(new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 0)));
		triangles.add(new Triangle(new Point(1, 1), new Point(1, 0), new Point(0, 1)));
		Individual<Set<Triangle>> individual = Individual.Factory.newInstance(triangles );
		BigDecimal fitness = target.eval(individual);
		
		assertEquals("Perimeter failed!", SQRT_TWO.add(TWO, MY_CNTX).multiply(TWO, MY_CNTX), fitness);
	}
	

}
