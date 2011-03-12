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
public class Evaluator<T extends Comparable<T>, K> {
    
    private final Map<UUID, T> cache = new HashMap<UUID, T>();

    private FitnessFunction<T, K> fitnessFunction;

    public T evaluate(Individual<K> individual) {
        T fitness;
        if (cache.containsKey(individual.getId())) {
            fitness = cache.get(individual.getId());
        } else {
            fitness = fitnessFunction.eval(individual);
            cache.put(individual.getId(), fitness);
        }

        return fitness;
    }

    public FitnessFunction<T, K> getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction<T, K> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
