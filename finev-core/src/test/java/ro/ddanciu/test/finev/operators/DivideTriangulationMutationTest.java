package ro.ddanciu.test.finev.operators;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ro.ddanciu.finite.elements.api.utils.TriangleUtils.counterClockwise;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ro.ddanciu.finev.operators.DivideTriangulationMutation;
import ro.ddanciu.finev.operators.utils.DistanceCalculator;
import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.utils.TriangleUtils;

public class DivideTriangulationMutationTest {
	
	private DivideTriangulationMutation mutation;
	private Random random;
	private DistanceCalculator distanceCalculator;
	
	@Before
	public void init() {
		
		random = mock(Random.class);
		distanceCalculator = mock(DistanceCalculator.class);
		
		mutation = new DivideTriangulationMutation();
		
		mutation.setDistanceCalculator(distanceCalculator);
		mutation.setRandom(random);
		mutation.setRate(0.5f);
	}

	@Test
	public void overRate() {
		Triangle triangle = new Triangle(new Point(1, 0), new Point(0, 0), new Point(0, 1));
		Triangle otherTriangle = new Triangle(new Point(2, 0), new Point(0, 2), new Point(2, 2));
		
		Set<Triangle> triangles = new HashSet<Triangle>();
		triangles.add(triangle);
		triangles.add(otherTriangle);
		
		when(random.choice(triangles)).thenReturn(triangle);
		when(distanceCalculator.compute(TriangleUtils.incenter(triangle))).thenReturn(new BigDecimal("0.6"));
		
		boolean done = mutation.operateInternal(triangles);
		
		assertFalse("Changes performed, but shouldn't!", done);
		assertTrue("Missing main triangle!", triangles.contains(triangle));
		assertTrue("Missing other triangle!", triangles.contains(otherTriangle));
	}


	@Test
	public void underRate() {
		Triangle triangle = new Triangle(new Point(0, 1), new Point(0, 0), new Point(1, 0));
		Triangle otherTriangle = new Triangle(new Point(0, 2), new Point(2, 0), new Point(2, 2));
		
		Set<Triangle> triangles = new HashSet<Triangle>();
		triangles.add(triangle);
		triangles.add(otherTriangle);
		
		when(random.choice(triangles)).thenReturn(triangle);
		when(distanceCalculator.compute(TriangleUtils.incenter(triangle))).thenReturn(new BigDecimal("0.3"));
		
		boolean done = mutation.operateInternal(triangles);
		
		assertTrue("Changes performed, but shouldn't!", done);
		assertFalse("Main triangle still in!", triangles.contains(triangle));
		assertTrue("Missing other triangle!", triangles.contains(otherTriangle));
		
		for (Triangle t : triangles) {
			assertTrue(format("Not in counter-clock-wise! %s, %s, %s", t.getP1(), t.getP2(), t.getP3()), 
					counterClockwise(t.getP1(), t.getP2(), t.getP3()) > 0);
		}
	}

}
