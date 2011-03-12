package ro.ddanciu.jevo.core;

/**
 *
 * @author dan
 */
public interface FitnessFunction<T extends Comparable<?>, K> {

    T eval(Individual<K> individual);
    
}
