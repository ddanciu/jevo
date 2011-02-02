package ro.ddanciu.test.finev.operators;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.operators.TriangulationCrossover;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.Individual;

public class TriangulationCrossoverTest {
	
	private TriangulationCrossover operator;
	private Random randomGenerator;
	
	@Before
	public void init() {
		randomGenerator = mock(Random.class);

		operator = new TriangulationCrossover();
		operator.setRandomGenerator(randomGenerator);
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
		
		List<Individual<Set<Triangle>>> population = new ArrayList<Individual<Set<Triangle>>>();
		population.add(Individual.Factory.newInstance(origin1)); 
		population.add(Individual.Factory.newInstance(origin2));
		
		Set<Individual<Set<Triangle>>> result = operator.operate(population.iterator());
		Set<Set<Triangle>> actual = new HashSet<Set<Triangle>>();
		for (Individual<Set<Triangle>> x : result) {
			actual.add(x.getData());
		}
		
		Set<Triangle> expected1 = new HashSet<Triangle>();
		expected1.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(2, 6)));
		expected1.add(new Triangle(new Point(1, 0), new Point(2, 6), new Point(3, 3)));
		expected1.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected1.add(new Triangle(new Point(3, 3), new Point(2, 6), new Point(5, 5)));
		expected1.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(5, 5)));
		expected1.add(new Triangle(new Point(4, 1), new Point(5, 5), new Point(6, 2)));
		
		Set<Triangle> expected2 = new HashSet<Triangle>();
		expected2.add(new Triangle(new Point(1, 0), new Point(0, 4), new Point(3, 3)));
		expected2.add(new Triangle(new Point(0, 4), new Point(2, 6), new Point(3, 3)));
		expected2.add(new Triangle(new Point(1, 0), new Point(3, 3), new Point(4, 1)));
		expected2.add(new Triangle(new Point(4, 1), new Point(3, 3), new Point(2, 6)));
		expected2.add(new Triangle(new Point(4, 1), new Point(2, 6), new Point(6, 2)));
		expected2.add(new Triangle(new Point(6, 2), new Point(2, 6), new Point(5, 5)));
		
		
		Set<Set<Triangle>> expected = new HashSet<Set<Triangle>>();
		expected.add(expected1);
		expected.add(expected2);
		
		assertEquals("Crossover failed!", expected, actual);
		
	}
}
