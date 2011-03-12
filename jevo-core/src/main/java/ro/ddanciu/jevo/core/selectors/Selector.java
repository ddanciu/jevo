package ro.ddanciu.jevo.core.selectors;

import java.util.Set;
import ro.ddanciu.jevo.core.Individual;

/**
 *
 * @author dan
 */
public interface Selector<I> {

    Set<Individual<I>> choose(Set<Individual<I>> population);

	Individual<I> best(Set<Individual<I>> population);

}
