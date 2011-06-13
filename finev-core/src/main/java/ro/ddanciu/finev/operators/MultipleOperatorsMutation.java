/**
 * 
 */
package ro.ddanciu.finev.operators;

import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.operators.MutationOperator;

/**
 * @author dan
 *
 */
public final class MultipleOperatorsMutation<I> implements MutationOperator<I> {

	private MutationOperator<I>[] operators;
	
	@Override
	public final boolean operate(Individual<I> individual) {
		for (MutationOperator<I> operator : operators) {
			if (operator.operate(individual)) {
				System.out.println("++ Mutation occurred!");
				return true;
			}
		}
		return false;
	}

	public MutationOperator<I>[] getOperators() {
		return operators;
	}

	public void setOperators(MutationOperator<I>[] operators) {
		this.operators = operators;
	}
	
}
