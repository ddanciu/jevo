package ro.ddanciu.test.finev.operators;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ro.ddanciu.finev.operators.utils.DistanceCalculator;
import ro.ddanciu.finev.operators.utils.DistanceCalculatorImpl;
import ro.ddanciu.finite.elements.api.Point;

public class DistanceCalculatorTest {
	
	private static final BigDecimal MY_ONE = BigDecimal.ONE.setScale(MY_SCALE, MY_RND);
	private DistanceCalculator calculator;
	private Set<Point> references;
	
	@Before
	public void init() {
		DistanceCalculatorImpl _calculator = new DistanceCalculatorImpl();
		
		references = new HashSet<Point>();
		_calculator.setReferences(references);
		
		calculator = _calculator;
	}

	@Test
	public void test0() {
		references.add(new Point(0, 0));
		BigDecimal norm;
		
		norm = calculator.compute(new Point(0, 0));
		assertEquals("Distance from (0, 0) to (0, 0) normalized is 1.", MY_ONE, norm);

		norm = calculator.compute(new Point(2, 0));
		assertEquals("Distance from (0, 0) to (2, 0) normalized is 1.", MY_ONE, norm);

		norm = calculator.compute(new Point(1, 0));
		assertEquals("Distance from (0, 0) to (1, 0) normalized is 0.5.", 
				new BigDecimal("0.5").setScale(MY_SCALE, MY_RND), norm);
	}

	@Test
	public void test1() {
		references.add(new Point(0, 0));
		references.add(new Point(2, 0));
		BigDecimal norm;
		
		norm = calculator.compute(new Point(1, 0));
		assertEquals("Distance from [(0, 0), (2, 0)] to (0, 0) normalized is 1.", MY_ONE, norm);

		norm = calculator.compute(new Point(0, 2));
		assertEquals("Distance from [(0, 0), (2, 0)] to (0, 2) normalized is 1.", MY_ONE, norm);

		norm = calculator.compute(new Point(2, 2));
		assertEquals("Distance from [(0, 0), (2, 0)] to (0, 2) normalized is 1.", MY_ONE, norm);

		norm = calculator.compute(new Point(1, 2));
		double expected = sqrt(5)/(1 + sqrt(2));
		BigDecimal expectedScaled = new BigDecimal(expected).setScale(MY_SCALE, MY_RND);
		assertEquals("Distance from [(0, 0), (2, 0)] to (1, 2) normalized is sqrt(5)/2.", expectedScaled, norm);
	}

}
