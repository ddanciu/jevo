package ro.ddanciu.finev.selector;

import static java.lang.String.format;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

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
	public Collection<Individual<D>> choose(Collection<Individual<D>> population) {
		
		if (population.size() < populationSize) {
			throw new IllegalArgumentException(
					format("Population %s is smaller in size than %d", population, populationSize));
		}
		
		if (population.size() == populationSize) {
			return population;
		}

		PriorityQueue<Competitor> judge = new PriorityQueue<Competitor>();
		
		for (Individual<D> i : population) {
			judge.add(new Competitor(i));
		}
		
		Set<Individual<D>> newPop = new HashSet<Individual<D>>();
		for (int i = 0; i < populationSize; i++) {
			newPop.add(judge.poll().individual);
		}
		return newPop;
	}
	
	@Override
	public Individual<D> best(Collection<Individual<D>> population) {
		X maxFitness = null;
		Individual<D> best = null;
		for (Individual<D> individual : population) {
			X fitness = evaluator.evaluate(individual);
			if (maxFitness == null || fitness.compareTo(maxFitness) > 0) {
				maxFitness = fitness;
				best = individual;
			}
		}
		System.out.println(String.format("Max fitness: %s", maxFitness));
		return best;
	}
	
	private final class Competitor implements Comparable<Competitor> {

		private final Individual<D> individual;
		private final X fitness;

		public Competitor(Individual<D> i) {
			fitness = evaluator.evaluate(i);
			this.individual = i;
		}

		@Override
		public int compareTo(Competitor other) {
			return fitness.compareTo(other.fitness);
		}
		
	}
}
