package ro.ddanciu.test.finev.operators;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ro.ddanciu.finev.operators.TriangulationMutation;
import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.Individual;

public class TriangulationMutationTest {
	


	private Set<Triangle> triangles;
	private Triangle a;
	private Triangle b;
	private Triangle c;
	
	private Individual<Set<Triangle>> individual;
	
	private Random randomGenerator;
	private TriangulationMutation operator;

	@Before
	public void setup() {
		triangles = new LinkedHashSet<Triangle>();
		a = new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6));
		b = new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3));
		c = new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6));
		
		triangles.add(a);
		triangles.add(b);
		triangles.add(c);
		
		individual = Individual.Factory.newInstance(triangles);
		
		randomGenerator = Mockito.mock(Random.class);
		operator = new TriangulationMutation();
		operator.setRandom(randomGenerator);
	}


	
	@Test
	public void basic() {
		
		Mockito.when(randomGenerator.choice(triangles)).thenReturn(a);

		operator.operate(individual);
		
		Triangle expected1 = new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3));
		Triangle expected2 = new Triangle(new Point(3, 3), new Point(0, 4), new Point(2, 6));
		
		assertFalse("Original still in set!", triangles.contains(a));
		assertFalse("Original still in set!", triangles.contains(b));
		assertTrue("New triangle missing!", triangles.contains(expected1));
		assertTrue("New triangle missing!", triangles.contains(expected2));
		assertTrue("Colateral damage!", triangles.contains(c));
		
	}

	
	@Test
	public void otherWay() {

		Mockito.when(randomGenerator.choice(triangles)).thenReturn(b);
		operator.operate(individual);
		
		Triangle expected1 = new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3));
		Triangle expected2 = new Triangle(new Point(3, 3), new Point(0, 4), new Point(2, 6));
		
		assertFalse("Original still in set!", triangles.contains(a));
		assertFalse("Original still in set!", triangles.contains(b));
		assertTrue("New triangle missing!", triangles.contains(expected1));
		assertTrue("New triangle missing!", triangles.contains(expected2));
		assertTrue("Colateral damage!", triangles.contains(c));
		
	}

	@Test
	public void broken() {

		Mockito.when(randomGenerator.choice(triangles)).thenReturn(c);

		operator.operate(individual);
		
		assertTrue("Broken swap!", triangles.contains(b));
		assertTrue("Broken swap!", triangles.contains(c));
		assertTrue("Colateral damage!", triangles.contains(a));
		
	}
}
