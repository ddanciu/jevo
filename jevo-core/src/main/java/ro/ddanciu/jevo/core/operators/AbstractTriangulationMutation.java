package ro.ddanciu.jevo.core.operators;

import ro.ddanciu.jevo.core.Individual;

public abstract class AbstractTriangulationMutation<D> implements MutationOperator<D> {

	public AbstractTriangulationMutation() {
		super();
	}

	protected abstract boolean operateInternal(D data);

	@Override
	public boolean operate(Individual<D> individual) {
		D data = individual.getData();
		return operateInternal(data);
	}

}