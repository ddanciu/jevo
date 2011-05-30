package ro.ddanciu.finev.operators.utils;

import static ro.ddanciu.finite.elements.api.Constants.MY_CNTX;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;

import ro.ddanciu.finite.elements.api.Point;

public class DistanceCalculatorImpl implements DistanceCalculator {
	
	private BigDecimal maxUntilNow = BigDecimal.ZERO;
	
	private Set<Point> references;

	@Required
	public void setReferences(Set<Point> references) {
		this.references = references;
	}
	
	/* (non-Javadoc)
	 * @see ro.ddanciu.finev.operators.utils.DistanceCalculator#compute(ro.ddanciu.finite.elements.api.Point)
	 */
	@Override
	public BigDecimal compute(Point x) {
		
		BigDecimal avg = averageDistance(x);
		maxUntilNow = maxUntilNow.max(avg);
		BigDecimal norm;
		if (maxUntilNow.compareTo(BigDecimal.ZERO) != 0) {
			norm = avg.divide(maxUntilNow, MathContext.DECIMAL128);
		} else {
			norm = BigDecimal.ONE;
		}
		return norm.round(MY_CNTX);
		
	}

	private BigDecimal averageDistance(Point x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Point reference : references) {
			BigDecimal distance = reference.distance(x);
			sum = sum.add(distance, MathContext.DECIMAL128);
		}
		BigDecimal avg = sum.divide(new BigDecimal(references.size()), MathContext.DECIMAL128);
		return avg;
	}

}
