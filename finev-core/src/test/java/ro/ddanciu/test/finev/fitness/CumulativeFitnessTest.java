package ro.ddanciu.test.finev.fitness;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ro.ddanciu.finev.fitness.CumulativeFitness;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

public class CumulativeFitnessTest {
	
	private CumulativeFitness<Object> target;
	
	private FitnessFunction<BigDecimal, Object> fitness1;
	private FitnessFunction<BigDecimal, Object> fitness2;
	private FitnessFunction<BigDecimal, Object> fitness3;

	private Individual<Object> individual1;
	private Individual<Object> individual2;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		
		target = new CumulativeFitness<Object>();

		fitness1 = Mockito.mock(FitnessFunction.class);
		fitness2 = Mockito.mock(FitnessFunction.class);
		fitness3 = Mockito.mock(FitnessFunction.class);
		
		Map<FitnessFunction<BigDecimal, Object>, Double> config = 
				new HashMap<FitnessFunction<BigDecimal,Object>, Double>();
		
		config.put(fitness1, 0.15);
		config.put(fitness2, 0.35);
		config.put(fitness3, 0.50);
		
		target.setConfig(config);

		individual1 = Individual.Factory.newInstance(new Object());
		individual2 = Individual.Factory.newInstance(new Object());
		
		when(fitness1.eval(individual1)).thenReturn(new BigDecimal(100));
		when(fitness2.eval(individual1)).thenReturn(new BigDecimal(200));
		when(fitness3.eval(individual1)).thenReturn(new BigDecimal(300));

		when(fitness1.eval(individual2)).thenReturn(new BigDecimal(500));
		when(fitness2.eval(individual2)).thenReturn(new BigDecimal(600));
		when(fitness3.eval(individual2)).thenReturn(new BigDecimal(700));
	}
	
	@Test
	public void justOne() {
		
		Map<FitnessFunction<BigDecimal, Object>, Double> config = 
				new HashMap<FitnessFunction<BigDecimal,Object>, Double>();
		config.put(fitness1, 0.15);
		target.setConfig(config);
		
		BigDecimal actual1 = target.eval(individual1);
		BigDecimal actual2 = target.eval(individual2);
		
		assertTrue("Weigthed fitness failed!", new BigDecimal(15 * 1).compareTo(actual1) == 0);
		assertTrue("Weigthed fitness failed!", new BigDecimal(15 * 5).compareTo(actual2) == 0);
	}
	
	@Test
	public void all() {
		BigDecimal actual1 = target.eval(individual1);
		BigDecimal actual2 = target.eval(individual2);
		
		assertTrue("Weigthed fitness failed!", new BigDecimal(1 * 15 + 2 * 35 + 3 * 50).compareTo(actual1) == 0);
		assertTrue("Weigthed fitness failed!", new BigDecimal(5 * 15 + 6 * 35 + 7 * 50).compareTo(actual2) == 0);
	}

}
