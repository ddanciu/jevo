/**
 * 
 */
package ro.ddanciu.jevo.core.operators;

import java.util.HashSet;
import java.util.Set;

import ro.ddanciu.jevo.core.Individual;

/**
 * @author dan
 *
 */
public abstract class AbstractCrossoverOperator<I> implements CrossoverOperator<I> {

	@Override
	public Set<Individual<I>> operate(Individual<I> x, Individual<I> y) {
		
		Set<I> result = operateInternal(x.getData(), y.getData());
		
		if (result != null) {
			Set<Individual<I>> offsprings = new HashSet<Individual<I>>();
			for (I r : result) {
				offsprings.add(Individual.Factory.newInstance(r));
			}
			return offsprings;
		} 
		return null;
	}
	
	protected abstract Set<I> operateInternal(I x, I y);

	
}
