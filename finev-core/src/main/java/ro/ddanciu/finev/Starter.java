package ro.ddanciu.finev;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.readers.ElementsReader;
import ro.ddanciu.jevo.core.EvoAlgorithm;
import ro.ddanciu.jevo.core.Individual;

public class Starter {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		PoliLine original = null;
		if (args.length > 0) {
			original = new ElementsReader(new FileInputStream(args[0])).readPoliLine();
		} else {
			System.out.println("No input file! Please specify one as command line argument!");
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
		
		Individual<Set<Triangle>> winner = algorithm.run(initialPopulation);
		
		System.out.println(winner.getData());
	}

}
