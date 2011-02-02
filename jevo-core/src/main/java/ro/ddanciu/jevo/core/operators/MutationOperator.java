/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core.operators;

import ro.ddanciu.jevo.core.Individual;

/**
 *
 * @author dan
 */
public interface MutationOperator<I> {

    public boolean operate(Individual<I> individual);

}
