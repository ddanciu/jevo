package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ONE;
import static java.math.MathContext.DECIMAL128;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.util.Set;

import ro.ddanciu.finev.operators.utils.DistanceCalculator;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

public class MinimalRatedWeightFitness implements FitnessFunction<BigDecimal, Set<Triangle>> {

	private BigDecimal rate;
	
	private DistanceCalculator distanceCalculator;
	
	@Override
	public BigDecimal eval(Individual<Set<Triangle>> individual) {
		Set<Triangle> triangulation = individual.getData();
		return eval(triangulation);
	}

	public BigDecimal eval(Set<Triangle> triangulation) {
		BigDecimal pmax = getMaximumPerimeter(triangulation);
		
		BigDecimal ratesSum = BigDecimal.ZERO;
		BigDecimal ratedFitSum = BigDecimal.ZERO;
		
		for (Triangle t : triangulation) {
			BigDecimal delta = distanceCalculator.compute(t.incenter());

			BigDecimal tfit = evalTriangle(t.perimeter(), delta, pmax);
			
			ratesSum = ratesSum.add(delta, DECIMAL128);
			ratedFitSum = ratedFitSum.add(delta.multiply(tfit, DECIMAL128), DECIMAL128);
		}
		
		return ratedFitSum.divide(ratesSum, DECIMAL128).setScale(MY_SCALE, MY_RND);
	}

	private BigDecimal evalTriangle(BigDecimal perimeter, BigDecimal delta, BigDecimal pmax) {
		BigDecimal base = rate.multiply(perimeter.divide(delta.multiply(pmax, DECIMAL128), DECIMAL128), DECIMAL128);
		if (ONE.compareTo(base) > 0) {
			base = ONE.divide(ONE.subtract(base, DECIMAL128), DECIMAL128);
		}
		return base.setScale(MY_SCALE, MY_RND);
	}

	private BigDecimal getMaximumPerimeter(Set<Triangle> triangulation) {
		BigDecimal pmax = BigDecimal.ZERO;
		for (Triangle t : triangulation) {
			pmax = pmax.max(t.perimeter());
		}
		return pmax;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public DistanceCalculator getDistanceCalculator() {
		return distanceCalculator;
	}

	public void setDistanceCalculator(DistanceCalculator distanceCalculator) {
		this.distanceCalculator = distanceCalculator;
	}
}
