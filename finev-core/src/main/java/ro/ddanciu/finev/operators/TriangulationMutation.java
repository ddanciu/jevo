package ro.ddanciu.finev.operators;

import static java.lang.String.format;
import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;
import static ro.ddanciu.finite.elements.api.utils.TriangleUtils.area;

import java.math.BigDecimal;
import java.util.Set;

import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Segment;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.utils.TriangleUtils;
import ro.ddanciu.jevo.core.operators.AbstractTriangulationMutation;

public class TriangulationMutation extends AbstractTriangulationMutation<Set<Triangle>> {

	private Random random;
	
	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	protected boolean operateInternal(Set<Triangle> triangles) {
		
		Triangle winner = random.choice(triangles);
		
		for (Triangle t : triangles) {
			if (t == winner) {
				continue;
			}
			Segment common = TriangleUtils.segmentInCommon(winner, t);
			if (common != null) { 
				return swap(triangles, winner, t, common);
			}
		}
		
		return false;
	}

	public static boolean swap(Set<Triangle> triangles, Triangle a, Triangle b, Segment common) {

		Point otherOfA = otherOf(a, common);
		Point otherOfB = otherOf(b, common);

		Triangle t1 = new Triangle(otherOfA, otherOfB, common.getP1());
		Triangle t2 = new Triangle(otherOfA, otherOfB, common.getP2());
		
		BigDecimal oldArea = area(a).add(area(b), MY_CNTX);
		BigDecimal newArea = area(t1).add(area(t2), MY_CNTX);
		
		if ( oldArea.compareTo(newArea) == 0) {
			triangles.remove(a);
			triangles.remove(b);
			
			triangles.add(t1);
			triangles.add(t2);
			
			return true;
		}
		
		return false;
	}

	private static Point otherOf(Triangle a, Segment common) {
		Point otherOfA = null;
		for (Point p : a) {
			if (!common.getP1().equals(p) && !common.getP2().equals(p)) {
				otherOfA = p;
				break;
			}
		}
		assert (otherOfA != null) : format("Broken triangle %s common %s!", a, common);
		return otherOfA;
	}


}
