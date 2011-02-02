/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core.selectors;

import java.util.Set;
import ro.ddanciu.jevo.core.Individual;

/**
 *
 * @author dan
 */
public interface Selector<I> {

    Set<Individual<I>> choose(Set<Individual<I>> population);

}
