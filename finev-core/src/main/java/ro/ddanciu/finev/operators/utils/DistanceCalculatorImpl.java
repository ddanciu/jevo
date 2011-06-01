package ro.ddanciu.finev.operators.utils;

import static java.math.MathContext.DECIMAL128;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
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
			norm = avg.divide(maxUntilNow, DECIMAL128);
		} else {
			norm = BigDecimal.ONE;
		}
		return norm.setScale(MY_SCALE, MY_RND);
		
	}

	private BigDecimal averageDistance(Point x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Point reference : references) {
			BigDecimal distance = reference.distance(x);
			sum = sum.add(distance, DECIMAL128);
		}
		BigDecimal avg = sum.divide(new BigDecimal(references.size()), DECIMAL128);
		return avg;
	}

}
