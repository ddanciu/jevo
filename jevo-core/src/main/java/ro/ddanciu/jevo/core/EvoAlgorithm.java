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
public class EvoAlgorithm {

    private int maxGenerations;

    private Selector selector;

    private MutationOperator mutationOperator;

    private CrossoverOperator crossoverOperator;

    private Stopper stopper;

    private Evaluator evaluator;

    /**
     * repeat
     *   parents := SELECTION population, FiTNESS-FN)
     *   population := REPRODUCTION( parents)
     * until some individual is fit enough
     * return the best individual in population, according to FITNESS-FN
     * 
     * @return
     */
    public Individual run(Set<Individual<?>> intialPopulation) {
        int generation = 0;
        Set<Individual<?>> population = intialPopulation;
        do {

            Set<Individual<?>> parents = select(population);
            population = reproduction(parents);

        } while (!stop(population) && generation++ < maxGenerations);

        return best(population);
    }
    
    private Set<Individual<?>> select(Set<Individual<?>> population) {
        return selector.choose(population);
    }

    private Set<Individual<?>> reproduction(Set<Individual<?>> parents) {
        Set<Individual<?>> population = crossover(parents);
        population = mutation(population);

        return population;
    }

    private Set<Individual<?>> crossover(Set<Individual<?>> parents) {
        
        Set<Individual<?>> next = new HashSet<Individual<?>>(parents);
        
        Iterator<Individual<?>> it = parents.iterator();
        while (it.hasNext()) {
            Set<Individual<?>> offsprings = crossoverOperator.operate(it);
            next.addAll(offsprings);
        }

        return next;

    }

    private Set<Individual<?>> mutation(Set<Individual<?>> population) {
        for (Individual individual : population) {
            mutationOperator.operate(individual);
        }

        return population;
    }

    private boolean stop(Set<Individual<?>> population) {
        return stopper.stop(population);
    }

    private Individual best(Set<Individual<?>> population) {
        Comparable maxFitness = null;
        Individual best = null;
        for (Individual individual : population) {
            Comparable fitness = evaluator.evaluate(individual);
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

    public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
        this.crossoverOperator = crossoverOperator;
    }

    public void setMutationOperator(MutationOperator mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setStopper(Stopper stopper) {
        this.stopper = stopper;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

}
