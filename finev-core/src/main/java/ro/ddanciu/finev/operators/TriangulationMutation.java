package ro.ddanciu.finev.operators;

import static java.lang.String.format;
import static java.math.MathContext.DECIMAL128;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Line;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Segment;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.utils.TriangleUtils;
import ro.ddanciu.jevo.core.operators.AbstractTriangulationMutation;

public class TriangulationMutation extends AbstractTriangulationMutation<Set<Triangle>> {

	private Random random;
	
	private float rate;
	
	public void setRandom(Random random) {
		this.random = random;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	@Override
	protected boolean operateInternal(Set<Triangle> triangles) {
		
		
		Triangle winner = random.choice(triangles);
		
		float perfectness = new BigDecimal("2").divide(winner.perfectness(), MathContext.DECIMAL128).floatValue();
		float myRate = Math.max(perfectness, rate);
		if (!random.rate(myRate)) {
			return false;
		}
		
		for (Triangle t : triangles) {
			if (t == winner) {
				continue;
			}
			Segment common = TriangleUtils.segmentInCommon(winner, t);
			if (common != null) { 
				
				boolean swapped = swap(triangles, winner, t, common);
				return swapped;
			}
		}
		
		return false;
	}

	public static boolean swap(Set<Triangle> triangles, Triangle a, Triangle b, Segment common) {

		Point otherOfA = otherOf(a, common);
		Point otherOfB = otherOf(b, common);
		
		Line ab = Line.defineByPoints(otherOfA, otherOfB);
		if (ab.distance(common.getP1()).doubleValue() < 0.001
				|| ab.distance(common.getP2()).doubleValue() < 0.001) {
			return false;
		}
		
		Triangle t1;
		Triangle t2;

		if (TriangleUtils.counterClockwise(otherOfA, otherOfB, common.getP1()) < 0) {
			t1 = new Triangle(otherOfA, otherOfB, common.getP1());
			t2 = new Triangle(otherOfA, common.getP2(), otherOfB);
		} else {
			t1 = new Triangle(otherOfB, otherOfA, common.getP1());
			t2 = new Triangle(otherOfB, common.getP2(), otherOfA);
		}

		BigDecimal oldArea = a.area().add(b.area(), DECIMAL128).setScale(MY_SCALE, MY_RND);
		BigDecimal newArea = t1.area().add(t2.area(), DECIMAL128).setScale(MY_SCALE, MY_RND);
		
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
