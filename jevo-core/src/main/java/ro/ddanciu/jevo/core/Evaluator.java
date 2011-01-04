/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author dan
 */
public final class Evaluator<T extends Comparable> {
    
    private final Map<UUID, T> cache = new HashMap<UUID, T>();

    private FitnessFunction<T> fitnessFunction;

    public T evaluate(Individual individual) {
        T fitness;
        if (cache.containsKey(individual.getId())) {
            fitness = cache.get(individual.getId());
        } else {
            fitness = fitnessFunction.eval(individual);
            cache.put(individual.getId(), fitness);
        }

        return fitness;
    }

    public FitnessFunction<T> getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction<T> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
