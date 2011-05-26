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
		BigDecimal norm = avg.divide(maxUntilNow, MathContext.DECIMAL32);
		return norm.round(MY_CNTX);
		
	}

	private BigDecimal averageDistance(Point x) {
		BigDecimal sum = BigDecimal.ZERO;
		for (Point reference : references) {
			sum.add(reference.distance(x), MathContext.DECIMAL32);
		}
		BigDecimal avg = sum.divide(new BigDecimal(references.size()), MathContext.DECIMAL32);
		return avg;
	}

}
