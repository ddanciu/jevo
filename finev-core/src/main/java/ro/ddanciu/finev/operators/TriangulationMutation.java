package ro.ddanciu.finev.operators;

import static java.lang.String.format;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Segment;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.utils.TriangleUtils;
import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.operators.MutationOperator;

public class TriangulationMutation implements MutationOperator<Set<Triangle>> {

	private Random randomGenerator;
	
	@Override
	public boolean operate(Individual<Set<Triangle>> individual) {
		
		Set<Triangle> triangles = individual.getData();
		
		int pos = randomGenerator.nextInt(triangles.size());
		int i = 0;
		Triangle winner = null;
		for (Triangle t : triangles) {
			if (i++ == pos) {
				winner = t;
				break;
			}
		}
		
		for (Triangle t : triangles) {
			Segment common = TriangleUtils.segmentInCommon(winner, t);
			if (common != null) { 
				swapp(triangles, winner, t, common);
				return true;
			}
		}
		
		return false;
	}

	private void swapp(Set<Triangle> triangles, Triangle a, Triangle b, Segment common) {
		Set<Point> pointsInCommon = new HashSet<Point>();
		pointsInCommon.add(common.getP1());
		pointsInCommon.add(common.getP2());

		Point otherOfA = null;
		for (Point p : a) {
			if (! pointsInCommon.contains(p)) {
				otherOfA = p;
				break;
			}
		}
		assert (otherOfA != null) : format("Broken triangle %s common %s!", a, common);

		Point otherOfB = null;
		for (Point p : b) {
			if (! pointsInCommon.contains(p)) {
				otherOfB = p;
				break;
			}
		}
		assert (otherOfB != null) : format("Broken triangle %s common %s!", b, common);

		triangles.remove(b);
		triangles.remove(b);

		triangles.add(new Triangle(otherOfA, otherOfB, common.getP1()));
		triangles.add(new Triangle(otherOfA, otherOfB, common.getP2()));
	}


}
