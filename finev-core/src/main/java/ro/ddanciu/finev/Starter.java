package ro.ddanciu.finev;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.readers.ElementsReader;
import ro.ddanciu.jevo.core.EvoAlgorithm;
import ro.ddanciu.jevo.core.Individual;

public class Starter {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {

		PoliLine original = null;
		Collection<Point> references = null;
		if (args.length == 2) {
			original = new ElementsReader(new FileInputStream(args[0])).readPoliLine();
			references = new ElementsReader(new FileInputStream(args[1])).readPoints();
		} else {
			System.out.println("No correct arguments! Please specify one as command line arguments!");
			System.exit(1);
		}

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/ro/ddanciu/finev/beans.xml");
		
		EvoAlgorithm<Set<Triangle>> algorithm = 
				(EvoAlgorithm<Set<Triangle>>) context.getBean("algorithm", EvoAlgorithm.class);
		Collection<Set<Triangle>> options = ((InitialPopulation) context.getBean("initialPopulation", InitialPopulation.class)).generate(original);
		Collection<Individual<Set<Triangle>>> initialPopulation = new HashSet<Individual<Set<Triangle>>>();

		for (Set<Triangle> o : options) {
			initialPopulation.add(Individual.Factory.newInstance(o));
		}
		
		Set<Point> distanceReferences = (Set<Point>) context.getBean("distanceReferences", Set.class);
		distanceReferences.addAll(references);
		
		Individual<Set<Triangle>> winner = algorithm.run(initialPopulation);
		
		System.out.println(winner.getData());
	}

}
