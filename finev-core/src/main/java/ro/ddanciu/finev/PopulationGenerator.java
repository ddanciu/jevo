package ro.ddanciu.finev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import ro.ddanciu.finite.elements.algorithms.CornerFanDecomposition;
import ro.ddanciu.finite.elements.algorithms.SeidelTrapezoidation;
import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Triangle;

public class PopulationGenerator {

	private Random randomGenerator;

	private CornerFanDecomposition cornerFanDecomposition;

	private SeidelTrapezoidation trapezoidation;

	public Set<Set<Triangle>> generate(PoliLine original, int howMany) {
		trapezoidation = new SeidelTrapezoidation();
		
		Set<PoliLine> decomposition = trapezoidation.decompose(original);
		Map<PoliLine, List<Set<Triangle>>> pool = new HashMap<PoliLine, List<Set<Triangle>>>();
		
		for (PoliLine p : decomposition) {
			Set<Set<Triangle>> variations = new HashSet<Set<Triangle>>();
			for (int i = 0; i < p.size(); i++) {
				variations.add(cornerFanDecomposition.decompose(p, i));
			}
			pool.put(p, new ArrayList<Set<Triangle>>(variations));
		}
		
		
		Set<Set<Triangle>> individuals = new HashSet<Set<Triangle>>();
		for (int i = 0; i < howMany; i++) {
			Set<Triangle> individual = new HashSet<Triangle>();
			
			for (List<Set<Triangle>> triangles: pool.values()) {
				int variation = randomGenerator.nextInt(triangles.size());
				individual.addAll(triangles.get(variation));
			}
			
			individuals.add(individual);
		}
		
		return individuals;
	}
	
	
}
