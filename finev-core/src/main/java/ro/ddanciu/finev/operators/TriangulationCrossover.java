package ro.ddanciu.finev.operators;

import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.gatherExterior;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.gatherInterior;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.mapping;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.minimumCommonPoliLine;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.Vector;
import ro.ddanciu.jevo.core.operators.AbstractCrossoverOperator;

public class TriangulationCrossover extends AbstractCrossoverOperator<Set<Triangle>> {
	
	private Random randomGenerator;

	public void setRandomGenerator(Random randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	@Override
	protected Set<Set<Triangle>> operateInternal(Iterator<Set<Triangle>> it) {
		if (! it.hasNext()) {
			return new HashSet<Set<Triangle>>();
		}
		Set<Triangle> mom = it.next();
		if (! it.hasNext()) {
			return Collections.singleton(mom);
		}
		
		Set<Triangle> dad = it.next();

		Map<Vector, List<Vector>> momsMapping = mapping(mom);
		Map<Vector, List<Vector>> dadsMapping = mapping(dad);
		
		Set<Vector> onlyMoms = new HashSet<Vector>(momsMapping.keySet());
		for (Vector v : dadsMapping.keySet()) {
			onlyMoms.remove(v);
			onlyMoms.remove(v.invert());
		}
		
		Set<Vector> minimum;
		Vector winner = randomGenerator.choice(onlyMoms);
		try {

			if (winner != null) {
				minimum = minimumCommonPoliLine(momsMapping, dadsMapping, winner);
			} else {
				minimum = null;
			}
	
			if (minimum == null) {
				Set<Set<Triangle>> parents = new HashSet<Set<Triangle>>();
				parents.add(mom);
				parents.add(dad);
				return parents;
			}
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println(String.format("Mom: %s", mom));
			System.out.println(String.format("Dad: %s", dad));
			System.out.println(String.format("Common: %s", winner));
			throw e;
		}

		Set<Triangle> child1 = new HashSet<Triangle>();
		child1.addAll(gatherInterior(momsMapping, minimum));
		child1.addAll(gatherExterior(dadsMapping, minimum));
		
		Set<Triangle> child2 = new HashSet<Triangle>();
		child2.addAll(gatherInterior(dadsMapping, minimum));
		child2.addAll(gatherExterior(momsMapping, minimum));
		
		Set<Set<Triangle>> offsprings = new HashSet<Set<Triangle>>();
		offsprings.add(child1);
		offsprings.add(child2);
		
		return offsprings;
	}


}
