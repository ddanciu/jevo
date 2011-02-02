/**
 * 
 */
package ro.ddanciu.jevo.core.operators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ro.ddanciu.jevo.core.Individual;

/**
 * @author dan
 *
 */
public abstract class AbstractCrossoverOperator<I> implements CrossoverOperator<I> {

	@Override
	public Set<Individual<I>> operate(Iterator<Individual<I>> it) {
		Set<I> result = operateInternal(new WrapperIterator<I>(it));
		
		Set<Individual<I>> offsprings = new HashSet<Individual<I>>();
		for (I r : result) {
			offsprings.add(Individual.Factory.newInstance(r));
		}
		return offsprings;
	}
	
	protected abstract Set<I> operateInternal(Iterator<I> it);

	private static final class WrapperIterator<I> implements Iterator<I> {
		
		private final Iterator<Individual<I>> it;

		private WrapperIterator(Iterator<Individual<I>> it) {
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public I next() {
			return it.next().getData();
		}

		@Override
		public void remove() {
			it.remove();			
		}
		
	}

}
