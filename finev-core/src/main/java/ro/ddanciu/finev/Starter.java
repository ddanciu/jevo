package ro.ddanciu.finev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ro.ddanciu.finev.io.Writer;
import ro.ddanciu.finite.elements.api.Point;
import ro.ddanciu.finite.elements.api.PoliLine;
import ro.ddanciu.finite.elements.api.Triangle;
import ro.ddanciu.finite.elements.api.readers.ElementsReader;
import ro.ddanciu.jevo.core.EvoAlgorithm;
import ro.ddanciu.jevo.core.Individual;

public class Starter {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {

		if (args.length != 2) {
			System.out.println("Not correct arguments! Please specify command line arguments!");
			System.exit(1);
		}
		
		
		String inFilePath = args[0];
		File inFile = new File(inFilePath);
		
		PoliLine original = new ElementsReader(new FileInputStream(inFile)).readPoliLine();
		Collection<Point> references = new ElementsReader(new FileInputStream(args[1])).readPoints();
		
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
		
		Set<Triangle> triangulation = algorithm.run(initialPopulation).getData();
		System.out.println(triangulation);
		
		Writer writer = (Writer) context.getBean("writer", Writer.class);
		
		String outFileName = inFile.getName() + "." + writer.getClass().getSimpleName() + ".liml";
		File outFile = new File(inFile.getParentFile(), outFileName);	
		
		
		writer.write(new PrintStream(outFile), triangulation.toArray(new PoliLine[triangulation.size()]));
		
	}

}
