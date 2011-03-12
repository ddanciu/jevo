package ro.ddanciu.jevo.core.selectors;

import java.util.Collection;

import ro.ddanciu.jevo.core.Individual;

/**
 *
 * @author dan
 */
public interface Selector<I> {

    Collection<Individual<I>> choose(Collection<Individual<I>> population);

	Individual<I> best(Collection<Individual<I>> population);

}
