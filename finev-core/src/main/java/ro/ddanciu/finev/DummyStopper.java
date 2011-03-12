package ro.ddanciu.finev;

import java.util.Set;

import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.Stopper;

public class DummyStopper<D> implements Stopper<D> {

	@Override
	public boolean stop(Set<Individual<D>> population) {
		return false;
	}

}
