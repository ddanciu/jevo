package ro.ddanciu.jevo.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ro.ddanciu.jevo.core.operators.CrossoverOperator;
import ro.ddanciu.jevo.core.operators.MutationOperator;
import ro.ddanciu.jevo.core.selectors.Selector;

/**
 * What is the fitness function?
 * How is an individual represented?
 * How are individuals selected?
 * How do individuals reproduce?
 *
 * @author dan
 */
public class EvoAlgorithm<I> {

    private int maxGenerations;

    private Selector<I> selector;

    private MutationOperator<I> mutationOperator;

    private CrossoverOperator<I> crossoverOperator;

    private Stopper<I> stopper;

    private Evaluator<Integer, I> evaluator;

    /**
     * repeat
     *   parents := SELECTION population, FiTNESS-FN)
     *   population := REPRODUCTION( parents)
     * until some individual is fit enough
     * return the best individual in population, according to FITNESS-FN
     * 
     * @return
     */
    public Individual<I> run(Set<Individual<I>> intialPopulation) {
        int generation = 0;
        Set<Individual<I>> population = intialPopulation;
        do {

            Set<Individual<I>> parents = select(population);
            population = reproduction(parents);

        } while (!stop(population) && generation++ < maxGenerations);

        return best(population);
    }
    
    private Set<Individual<I>> select(Set<Individual<I>> population) {
        return selector.choose(population);
    }

    private Set<Individual<I>> reproduction(Set<Individual<I>> parents) {
        Set<Individual<I>> population = crossover(parents);
        population = mutation(population);

        return population;
    }

    private Set<Individual<I>> crossover(Set<Individual<I>> parents) {
        
        Set<Individual<I>> next = new HashSet<Individual<I>>(parents);
        
        Iterator<Individual<I>> it = parents.iterator();
        while (it.hasNext()) {
            Set<Individual<I>> offsprings = crossoverOperator.operate(it);
            next.addAll(offsprings);
        }

        return next;

    }

    private Set<Individual<I>> mutation(Set<Individual<I>> population) {
        for (Individual<I> individual : population) {
            mutationOperator.operate(individual);
        }

        return population;
    }

    private boolean stop(Set<Individual<I>> population) {
        return stopper.stop(population);
    }

    private Individual<I> best(Set<Individual<I>> population) {
        Integer maxFitness = null;
        Individual<I> best = null;
        for (Individual<I> individual : population) {
            Integer fitness = evaluator.evaluate(individual);
            if (maxFitness == null || fitness.compareTo(maxFitness) > 0) {
                maxFitness = fitness;
                best = individual;
            }
        }
        return best;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public void setCrossoverOperator(CrossoverOperator<I> crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
    }

    public void setMutationOperator(MutationOperator<I> mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    public void setSelector(Selector<I> selector) {
        this.selector = selector;
    }

    public void setStopper(Stopper<I> stopper) {
        this.stopper = stopper;
    }

    public void setEvaluator(Evaluator<Integer, I> evaluator) {
        this.evaluator = evaluator;
    }

}
