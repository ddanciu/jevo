package ro.ddanciu.finev.operators;

import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.gatherExterior;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.gatherInterior;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.mapping;
import static ro.ddanciu.finite.elements.api.utils.TriangulationUtils.minimumCommonPoliLine;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import ro.ddanciu.finev.operators.utils.Random;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.Vector;
import ro.ddanciu.jevo.core.operators.AbstractCrossoverOperator;

public class TriangulationCrossover extends AbstractCrossoverOperator<Set<Triangle>> {
	
	@Override
	protected Set<Set<Triangle>> operateInternal(Set<Triangle> mom, Set<Triangle> dad) {

		Map<Vector, List<Vector>> momsMapping = mapping(mom);
		Map<Vector, List<Vector>> dadsMapping = mapping(dad);
		
		Set<Vector> onlyMoms = new HashSet<Vector>(momsMapping.keySet());
		for (Vector v : dadsMapping.keySet()) {
			onlyMoms.remove(v);
			onlyMoms.remove(v.invert());
		}
		
		Set<Vector> minimum = null;
		Vector winner = null;
		Stack<Vector> onlyMomsStack = new Stack<Vector>();
		onlyMomsStack.addAll(onlyMoms);
		
		while (! onlyMomsStack.isEmpty()) {
			winner = onlyMomsStack.pop();
			minimum = minimumCommonPoliLine(momsMapping, dadsMapping, winner);
			
			if (minimum != null) {
				Collection<Triangle> e1;
				Collection<Triangle> e2;
				
				if ((e1 = gatherExterior(dadsMapping, minimum)).isEmpty() 
						|| (e2 = gatherExterior(momsMapping, minimum)).isEmpty()) {
					continue;
				}
				
				Collection<Triangle> i1 = gatherInterior(momsMapping, minimum);
				Collection<Triangle> i2 = gatherInterior(dadsMapping, minimum);
				
				Set<Triangle> child1 = new HashSet<Triangle>();
				child1.addAll(i1);
				child1.addAll(e1);
				
				Set<Triangle> child2 = new HashSet<Triangle>();
				child2.addAll(i2);
				child2.addAll(e2);
				
				Set<Set<Triangle>> offsprings = new HashSet<Set<Triangle>>();
				offsprings.add(child1);
				offsprings.add(child2);
				
				System.out.println(">< Crossover occurred!!");
				return offsprings;
			}
		}

		
		return null;
	}


}
