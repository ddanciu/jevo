package ro.ddanciu.finev.operators;

import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.divideByPoint;

import java.math.BigDecimal;
import java.util.Set;

import ro.ddanciu.finev.operators.utils.DistanceCalculator;
import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.operators.AbstractTriangulationMutation;

public class DivideTriangulationMutation extends AbstractTriangulationMutation<Set<Triangle>> {

	private Random random;
	
	private float rate;
	
	private DistanceCalculator distanceCalculator;
	
	public void setRandom(Random random) {
		this.random = random;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}
	
	public void setDistanceCalculator(DistanceCalculator distanceCalculator) {
		this.distanceCalculator = distanceCalculator;
	}

	@Override
	public boolean operateInternal(Set<Triangle> triangles) {
		
		Triangle winner = random.choice(triangles);
		Point incenter = winner.incenter();
		
		BigDecimal distance = distanceCalculator.compute(incenter);
		
		if (rate < distance.floatValue()) {
			return false;
		}
		
		divideByPoint(winner, incenter, triangles);
		return true;
	}

}
