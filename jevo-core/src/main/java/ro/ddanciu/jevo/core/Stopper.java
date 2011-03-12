/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core;

import java.util.Collection;

/**
 *
 * @author dan
 */
public interface Stopper<I> {

    public boolean stop(Collection<Individual<I>> population);

}
