package ro.ddanciu.finev.selector;

import static java.lang.String.format;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import ro.ddanciu.jevo.core.Evaluator;
import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.selectors.Selector;

/**
 * @author dan
 */
public class FixedSizePopulationSelector<X extends Comparable<X>, D> implements Selector<D> {
	
	private Evaluator<X, D> evaluator;
	
	private int populationSize;
	
	public void setEvaluator(Evaluator<X, D> evaluator) {
		this.evaluator = evaluator;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	@Override
	public Set<Individual<D>> choose(Set<Individual<D>> population) {
		
		if (population.size() < populationSize) {
			throw new IllegalArgumentException(
					format("Population %s is smaller in size than %d", population, populationSize));
		}
		
		if (population.size() == populationSize) {
			return population;
		}

		TreeMap<Comparable<X>, Individual<D>> judge = new TreeMap<Comparable<X>, Individual<D>>();
		
		for (Individual<D> i : population) {
			judge.put(evaluator.evaluate(i), i);
		}
		
		Set<Individual<D>> newPop = new HashSet<Individual<D>>();
		for (int i = 0; i < populationSize; i++) {
			newPop.add(judge.pollFirstEntry().getValue());
		}
		return newPop;
	}
	
	@Override
	public Individual<D> best(Set<Individual<D>> population) {
		X maxFitness = null;
		Individual<D> best = null;
		for (Individual<D> individual : population) {
			X fitness = evaluator.evaluate(individual);
			if (maxFitness == null || fitness.compareTo(maxFitness) > 0) {
				maxFitness = fitness;
				best = individual;
			}
		}
		return best;
	}
}
