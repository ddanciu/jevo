package ro.ddanciu.finev.fitness;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static ro.ddanciu.finite.elements.api.Constants.MY_RND;
import static ro.ddanciu.finite.elements.api.Constants.MY_SCALE;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;

public class CumulativeFitness<T> implements FitnessFunction<BigDecimal, T> {

	private Map<FitnessFunction<BigDecimal, T>, Double> config = null;
	
	public void setConfig(Map<FitnessFunction<BigDecimal, T>, Double> config) {
		this.config = config;
	}

	@Override
	public BigDecimal eval(Individual<T> individual) {
		
		assert config != null : "Bad setup, config not set!";

		BigDecimal value = ZERO;
		for (Entry<FitnessFunction<BigDecimal, T>, Double> entry : config.entrySet()) {
			BigDecimal partial = entry.getKey().eval(individual);
			BigDecimal weigth = new BigDecimal(Double.toString(entry.getValue()));
			
			BigDecimal x = partial.multiply(weigth, DECIMAL128);
			
			value = value.add(x, DECIMAL128);
		}
		value.setScale(MY_SCALE, MY_RND);
		return value;
	}

}
