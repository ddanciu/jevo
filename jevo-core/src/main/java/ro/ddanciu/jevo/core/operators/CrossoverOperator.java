/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core.operators;

import java.util.Iterator;
import java.util.Set;
import ro.ddanciu.jevo.core.Individual;

/**
 *
 * @author dan
 */
public interface CrossoverOperator {

    Set<Individual<?>> operate(Iterator<Individual<?>> it);

}
