/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ddanciu.jevo.core;

import java.util.Set;

/**
 *
 * @author dan
 */
public interface Stopper {

    public boolean stop(Set<Individual<?>> population);

}
