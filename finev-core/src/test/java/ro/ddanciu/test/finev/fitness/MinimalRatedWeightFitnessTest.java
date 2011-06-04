package ro.ddanciu.test.finev.fitness;

import static org.mockito.Mockito.mock;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ro.ddanciu.finev.fitness.MinimalRatedWeightFitness;
import ro.ddanciu.finev.operators.utils.DistanceCalculator;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.Triangle;

public class MinimalRatedWeightFitnessTest {
	
	private static final Triangle T1 = new Triangle(new Point(0, 0), new Point(0, 1), new Point(1, 0));
	private static final Triangle T2 = new Triangle(new Point(1, 1), new Point(1, 0), new Point(0, 1));

	private MinimalRatedWeightFitness fitness;
	
	private DistanceCalculator distanceCalculator;
	
	
	@Before
	public void init() {
		
		distanceCalculator = mock(DistanceCalculator.class);

		Mockito.when(distanceCalculator.compute(T1.incenter())).thenReturn(BigDecimal.valueOf(0.50f));
		Mockito.when(distanceCalculator.compute(T2.incenter())).thenReturn(BigDecimal.valueOf(0.10f));

		fitness = new MinimalRatedWeightFitness();
		fitness.setDistanceCalculator(distanceCalculator);
		fitness.setRate(BigDecimal.ZERO);
	}

	@Test
	public void one() {
		fitness.setRate(BigDecimal.ONE);
		BigDecimal actual = fitness.eval(new HashSet<Triangle>(Arrays.asList(T1)));
		Assert.assertEquals("Minimal rated weight failed!", BigDecimal.valueOf(2).setScale(MY_SCALE, MY_RND), actual);

		fitness.setRate(BigDecimal.valueOf(2));
		BigDecimal actual2 = fitness.eval(new HashSet<Triangle>(Arrays.asList(T1)));
		Assert.assertEquals("Minimal rated weight failed!", BigDecimal.valueOf(4).setScale(MY_SCALE, MY_RND), actual2);
	}

	@Test
	public void otherOne() {
		fitness.setRate(BigDecimal.ONE);
		BigDecimal actual = fitness.eval(new HashSet<Triangle>(Arrays.asList(T2)));
		Assert.assertEquals("Minimal rated weight failed!", 
				BigDecimal.valueOf(10).setScale(MY_SCALE, MY_RND), actual);
	}

	@Test
	public void both() {
		fitness.setRate(BigDecimal.ONE);
		BigDecimal actual = fitness.eval(new HashSet<Triangle>(Arrays.asList(T1, T2)));
		Assert.assertEquals("Minimal rated weight failed!", 
				BigDecimal.valueOf(10f/3).setScale(MY_SCALE, MY_RND), actual);
	}

}
