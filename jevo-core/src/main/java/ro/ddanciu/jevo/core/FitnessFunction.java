/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core;

/**
 *
 * @author dan
 */
public interface FitnessFunction<T, K> {

    T eval(Individual<K> individual);
    
}
