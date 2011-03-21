package ro.ddanciu.test.finev.operators;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.operators.TriangulationMutation;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Segment;
import ro.ddanciu.finite.elements.api.Triangle;

public class TriangulationMutationSwapTest {

	
	private Set<Triangle> triangles;
	private Triangle a;
	private Triangle b;
	private Triangle c;


	@Before
	public void setup() {
		triangles = new HashSet<Triangle>();
		a = new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6));
		b = new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3));
		c = new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6));
		
		triangles.add(a);
		triangles.add(b);
		triangles.add(c);
	}


	@Test
	public void basic() {
		
		Segment common = new Segment(new Point(1, 0), new Point(2, 6));
		boolean doneIt = TriangulationMutation.swap(triangles, a, b, common);
		
		Triangle expected1 = new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3));
		Triangle expected2 = new Triangle(new Point(3, 3), new Point(0, 4), new Point(2, 6));
		
		assertTrue("Mutation didn't happen!", doneIt);
		assertFalse("Original still in set!", triangles.contains(a));
		assertFalse("Original still in set!", triangles.contains(b));
		assertTrue("New triangle missing!", triangles.contains(expected1));
		assertTrue("New triangle missing!", triangles.contains(expected2));
		assertTrue("Colateral damage!", triangles.contains(c));
		
	}

	
	@Test
	public void broken() {
		
		Segment common = new Segment(new Point(3, 3), new Point(2, 6));
		boolean doneIt = TriangulationMutation.swap(triangles, b, c, common);

		assertFalse("Mutation happen!", doneIt);
		assertTrue("Broken swap!", triangles.contains(b));
		assertTrue("Broken swap!", triangles.contains(c));
		assertTrue("Colateral damage!", triangles.contains(a));
		
	}
	
	@Test
	public void something() {
		
		triangles = new HashSet<Triangle>();
		a = new Triangle(new Point(0, 2), new Point(3, 6), new Point(4, 4));
		b = new Triangle(new Point(0, 2), new Point(1, 5), new Point(3, 6));
		
		triangles.add(a);
		triangles.add(b);
		
		Segment common = new Segment(new Point(0, 2), new Point(3, 6));

		
		boolean doneIt = TriangulationMutation.swap(triangles, a, b, common);
		
		assertTrue("Mutation didn't happen!", doneIt);
		assertFalse("Original still in set!", triangles.contains(a));
		assertFalse("Original still in set!", triangles.contains(b));
		
		
		Triangle expected1 = new Triangle(new Point(0, 2), new Point(1, 5), new Point(4, 4));
		assertTrue("New triangle missing!", triangles.contains(expected1));
		
		Triangle expected2 = new Triangle(new Point(1, 5), new Point(3, 6), new Point(4, 4));
		assertTrue("New triangle missing!", triangles.contains(expected2));
		
		
	}

}
