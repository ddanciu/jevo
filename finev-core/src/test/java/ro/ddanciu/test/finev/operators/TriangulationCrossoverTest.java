package ro.ddanciu.test.finev.operators;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.operators.TriangulationCrossover;
import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.Vector;
import ro.ddanciu.jevo.core.Individual;

public class TriangulationCrossoverTest {
	
	private TriangulationCrossover operator;
	private Random randomGenerator;
	
	@Before
	public void init() {
		randomGenerator = mock(Random.class);

		operator = new TriangulationCrossover();
		operator.setRandom(randomGenerator);
	}
	
	@Test
	public void disjunct() {
		Triangle t1 = new Triangle(new Point(1, 0), new Point(0, 0), new Point(0, 1));
		Triangle t2 = new Triangle(new Point(2, 0), new Point(0, 2), new Point(2, 2));

		Set<Triangle> origin1 = new HashSet<Triangle>();
		origin1.add(t1);
		
		Set<Triangle> origin2 = new HashSet<Triangle>();
		origin2.add(t2);

		Vector winner = new Vector(new Point(0, 1), new Point(1, 0));
		Set<Set<Triangle>> actual = run(origin1, origin2, winner);
		
		assertEquals("Wrong number!", 2, actual.size());
		assertTrue("First triangle missing!", actual.contains(origin1));
		assertTrue("Second triangle missing!", actual.contains(origin2));
		
	}
	
	@Test
	public void simple() {

		Triangle t11 = new Triangle(new Point(1, 0), new Point(0, 0), new Point(0, 1));
		Triangle t12 = new Triangle(new Point(1, 0), new Point(0, 1), new Point(1, 1));
		Triangle t13 = new Triangle(new Point(0, 1), new Point(0, 2), new Point(1, 1));
		
		Triangle t21 = new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 1));
		Triangle t22 = new Triangle(new Point(0, 0), new Point(1, 1), new Point(1, 0));
		Triangle t23 = new Triangle(new Point(1, 0), new Point(1, 1), new Point(2, 0));
		
		Set<Triangle> origin1 = new HashSet<Triangle>();
		origin1.add(t11);
		origin1.add(t12);
		origin1.add(t13);
		
		Set<Triangle> origin2 = new HashSet<Triangle>();
		origin2.add(t21);
		origin2.add(t22);
		origin2.add(t23);
		
		Set<Triangle> expected1 = new HashSet<Triangle>();
		expected1.add(t21);
		expected1.add(t22);
		expected1.add(t13);
		
		Set<Triangle> expected2 = new HashSet<Triangle>();
		expected2.add(t11);
		expected2.add(t12);
		expected2.add(t23);
				
		
		Vector[] winners = new Vector[] {
				new Vector(new Point(0, 1), new Point(1, 0)),
				new Vector(new Point(1, 0), new Point(0, 1))
		};
		
		
		for (Vector winner : winners) {

			Set<Set<Triangle>> actual = run(origin1, origin2, winner);
			assertEquals("Wrong number!", 2, actual.size());
			
			assertTrue("Bad mapping on crossover!", actual.contains(expected1));
			assertTrue("Bad mapping on crossover!", actual.contains(expected2));
		}
		

		Vector[] loosers = new Vector[] {
				new Vector(new Point(0, 2), new Point(1, 1)),
				new Vector(new Point(0, 1), new Point(0, 2))
		};
		
		for (Vector looser : loosers) {

			Set<Set<Triangle>> actual = run(origin1, origin2, looser);
			assertEquals("Wrong number!", 2, actual.size());
			
			assertTrue("Origin1 lost!", actual.contains(origin1));
			assertTrue("Origin2 lost!", actual.contains(origin2));
		
		}		
	}
	
	
	@Test 
	public void one() {
		
		Set<Triangle> origin1 = new HashSet<Triangle>();
		origin1.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6)));
		origin1.add(new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3)));
		origin1.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		origin1.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6)));
		origin1.add(new Triangle(new Point(4, 1), new Point(2, 6), new Point(6, 2)));
		origin1.add(new Triangle(new Point(6, 2), new Point(2, 6), new Point(5, 5)));
		
		Set<Triangle> origin2 = new HashSet<Triangle>();
		origin2.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3)));
		origin2.add(new Triangle(new Point(0, 4), new Point(2, 6), new Point(3, 3)));
		origin2.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		origin2.add(new Triangle(new Point(3, 3), new Point(2, 6), new Point(5, 5)));
		origin2.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(5, 5)));
		origin2.add(new Triangle(new Point(4, 1), new Point(5, 5), new Point(6, 2)));
		

		Set<Triangle> expected11 = new HashSet<Triangle>();
		expected11.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6)));
		expected11.add(new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3)));
		expected11.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected11.add(new Triangle(new Point(3, 3), new Point(2, 6), new Point(5, 5)));
		expected11.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(5, 5)));
		expected11.add(new Triangle(new Point(4, 1), new Point(5, 5), new Point(6, 2)));
		
		Set<Triangle> expected12 = new HashSet<Triangle>();
		expected12.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3)));
		expected12.add(new Triangle(new Point(0, 4), new Point(2, 6), new Point(3, 3)));
		expected12.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected12.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6)));
		expected12.add(new Triangle(new Point(4, 1), new Point(2, 6), new Point(6, 2)));
		expected12.add(new Triangle(new Point(6, 2), new Point(2, 6), new Point(5, 5)));
		
		Set<Set<Triangle>> expected1 = new HashSet<Set<Triangle>>();
		expected1.add(expected11);
		expected1.add(expected12);

		for (Vector x : new Vector[] {
				new Vector(new Point(2, 6), new Point(4, 1)),
				new Vector(new Point(4, 1), new Point(2, 6)),
				new Vector(new Point(2, 6), new Point(6, 2)),
				new Vector(new Point(6, 2), new Point(2, 6))}) {
			
			Set<Set<Triangle>> actual = run(origin1, origin2, x);
			assertEquals("Crossover failed!", expected1, actual);
		}
		
		
		Set<Triangle> expected21 = new HashSet<Triangle>();
		expected21.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3)));
		expected21.add(new Triangle(new Point(0, 4), new Point(2, 6), new Point(3, 3)));
		expected21.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected21.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6)));
		expected21.add(new Triangle(new Point(4, 1), new Point(2, 6), new Point(6, 2)));
		expected21.add(new Triangle(new Point(6, 2), new Point(2, 6), new Point(5, 5)));

		Set<Triangle> expected22 = new HashSet<Triangle>();
		expected22.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6)));
		expected22.add(new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3)));
		expected22.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected22.add(new Triangle(new Point(3, 3), new Point(2, 6), new Point(5, 5)));
		expected22.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(5, 5)));
		expected22.add(new Triangle(new Point(4, 1), new Point(5, 5), new Point(6, 2)));
		
		
		Set<Set<Triangle>> expected2 = new HashSet<Set<Triangle>>();
		expected2.add(expected21);
		expected2.add(expected22);

		for (Vector x : new Vector[] {
				new Vector(new Point(2, 6), new Point(1, 0)),
				new Vector(new Point(1, 0), new Point(2, 6))}) {
			
			Set<Set<Triangle>> actual = run(origin1, origin2, x);
			assertEquals("Crossover failed!", expected2, actual);
		}
		
	}

	@Test
	public void two() {

		Set<Triangle> origin1 = new HashSet<Triangle>();
		origin1.add(new Triangle(new Point(0, 2), new Point(1, 5), new Point(3, 6)));
		origin1.add(new Triangle(new Point(2, 0), new Point(3, 6), new Point(4, 4)));
		origin1.add(new Triangle(new Point(2, 0), new Point(0, 2), new Point(3, 6)));
		
		Set<Triangle> origin2 = new HashSet<Triangle>();
		origin2.add(new Triangle(new Point(4, 4), new Point(0, 2), new Point(1, 5)));
		origin2.add(new Triangle(new Point(3, 6), new Point(4, 4), new Point(1, 5)));
		origin2.add(new Triangle(new Point(4, 4), new Point(2, 0), new Point(0, 2)));
		
		Set<Set<Triangle>> expected = new HashSet<Set<Triangle>>();
		expected.add(origin1);
		expected.add(origin2);

		for (Vector x : new Vector[] {
				new Vector(new Point(3, 6), new Point(2, 0)),
				new Vector(new Point(2, 0), new Point(3, 6)),
				new Vector(new Point(3, 6), new Point(0, 2)),
				new Vector(new Point(0, 2), new Point(3, 6))}) {
			
			Set<Set<Triangle>> actual = run(origin1, origin2, x);
			assertEquals("Crossover failed!", expected, actual);
		}
	}
	

	@Test
	public void three() {

		Set<Triangle> origin1 = new HashSet<Triangle>();
		origin1.add(new Triangle(new Point(0, 2), new Point(1, 5), new Point(3, 6)));
		origin1.add(new Triangle(new Point(2, 0), new Point(0, 2), new Point(4, 4)));
		origin1.add(new Triangle(new Point(0, 2), new Point(3, 6), new Point(4, 4)));
		
		Set<Triangle> origin2 = new HashSet<Triangle>();
		origin2.add(new Triangle(new Point(4, 4), new Point(0, 2), new Point(1, 5)));
		origin2.add(new Triangle(new Point(3, 6), new Point(4, 4), new Point(1, 5)));
		origin2.add(new Triangle(new Point(4, 4), new Point(2, 0), new Point(0, 2)));
		
		Set<Triangle> expected1 = new HashSet<Triangle>();
		expected1.add(new Triangle(new Point(0, 2), new Point(1, 5), new Point(3, 6)));
		expected1.add(new Triangle(new Point(2, 0), new Point(0, 2), new Point(4, 4)));
		expected1.add(new Triangle(new Point(0, 2), new Point(3, 6), new Point(4, 4)));
		
		Set<Triangle> expected2 = new HashSet<Triangle>();
		expected2.add(new Triangle(new Point(4, 4), new Point(0, 2), new Point(1, 5)));
		expected2.add(new Triangle(new Point(3, 6), new Point(4, 4), new Point(1, 5)));
		expected2.add(new Triangle(new Point(4, 4), new Point(2, 0), new Point(0, 2)));
		
		Set<Set<Triangle>> expected = new HashSet<Set<Triangle>>();
		expected.add(expected1);
		expected.add(expected2);

		for (Vector x : new Vector[] {
				new Vector(new Point(3, 6), new Point(0, 2)),
				new Vector(new Point(0, 2), new Point(3, 6))}) {
			
			Set<Set<Triangle>> actual = run(origin1, origin2, x);
			assertEquals("Crossover failed!", expected, actual);
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Set<Triangle>> run(Set<Triangle> origin1, Set<Triangle> origin2, Vector winner) {
		
		when(randomGenerator.choice(anySet())).thenReturn(winner);
		
		List<Individual<Set<Triangle>>> population = new ArrayList<Individual<Set<Triangle>>>();
		population.add(Individual.Factory.newInstance(origin1)); 
		population.add(Individual.Factory.newInstance(origin2));
		
		Set<Individual<Set<Triangle>>> result = operator.operate(population.iterator());
		Set<Set<Triangle>> actual = new HashSet<Set<Triangle>>();
		for (Individual<Set<Triangle>> x : result) {
			actual.add(x.getData());
		}
		return actual;
	}
}
