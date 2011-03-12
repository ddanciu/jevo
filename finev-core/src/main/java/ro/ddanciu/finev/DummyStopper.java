package ro.ddanciu.finev;

import java.util.Collection;

import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.Stopper;

public class DummyStopper<D> implements Stopper<D> {

	@Override
	public boolean stop(Collection<Individual<D>> population) {
		return false;
	}

}
