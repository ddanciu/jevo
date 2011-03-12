package ro.ddanciu.test.finev.selector;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ro.ddanciu.finev.selector.FixedSizePopulationSelector;
import ro.ddanciu.jevo.core.Evaluator;
import ro.ddanciu.jevo.core.Individual;

public class FixedSizePopulationSelectorTest {
	
	private FixedSizePopulationSelector<Integer, String> selector;
	private Evaluator<Integer, String> evaluator;
	private Individual<String> one;
	private Individual<String> two;
	private Individual<String> three;
	private Individual<String> four;
	private Individual<String> five;
	
	
	
	@Before
	public void init() {
		evaluator = mock(Evaluator.class);
		
		selector = new FixedSizePopulationSelector<Integer, String>();
		selector.setEvaluator(evaluator);
		
		one = Individual.Factory.newInstance("One");
		two = Individual.Factory.newInstance("Two");
		three = Individual.Factory.newInstance("Three");
		four = Individual.Factory.newInstance("Four");
		five = Individual.Factory.newInstance("Five");

		Mockito.when(evaluator.evaluate(one)).thenReturn(1);
		Mockito.when(evaluator.evaluate(two)).thenReturn(2);
		Mockito.when(evaluator.evaluate(three)).thenReturn(3);
		Mockito.when(evaluator.evaluate(four)).thenReturn(4);
		Mockito.when(evaluator.evaluate(five)).thenReturn(5);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void empty() {
		selector.setPopulationSize(1);
		selector.choose(new HashSet<Individual<String>>());
	}

	@Test
	public void one() {
		selector.setPopulationSize(1);
		Set<Individual<String>> expected = Collections.singleton(one);
		Set<Individual<String>> result = selector.choose(expected);
		
		assertSame("Same collection not returned when no changes happened!", expected, result);
	}


	@Test
	public void simple() {
		Set<Individual<String>> population = new HashSet<Individual<String>>();
		population.add(one);
		population.add(two);
		population.add(three);
		population.add(four);
		population.add(five);
		
		selector.setPopulationSize(3);
		Set<Individual<String>> result = selector.choose(population);
		
		Set<Individual<String>> expected = new HashSet<Individual<String>>();
		expected.add(one);
		expected.add(two);
		expected.add(three);
		
		assertEquals("Selection failed!", expected, result);

	}

	@Test
	public void same() {
		Individual<String> first = Individual.Factory.newInstance("first");
		Individual<String> second = Individual.Factory.newInstance("second");
		Individual<String> third = Individual.Factory.newInstance("third");

		Set<Individual<String>> population = new HashSet<Individual<String>>();
		population.add(first);
		population.add(second);
		population.add(third);
		

		Mockito.when(evaluator.evaluate(first)).thenReturn(1);
		Mockito.when(evaluator.evaluate(second)).thenReturn(1);
		Mockito.when(evaluator.evaluate(third)).thenReturn(1);
		
		selector.setPopulationSize(2);
		Set<Individual<String>> result = selector.choose(population);
		
		assertEquals("Selection failed!", 2, result.size());

	}
	
}
