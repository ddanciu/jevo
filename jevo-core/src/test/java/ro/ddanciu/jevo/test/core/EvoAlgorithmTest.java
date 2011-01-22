/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ddanciu.jevo.test.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ro.ddanciu.jevo.core.Evaluator;

import ro.ddanciu.jevo.core.EvoAlgorithm;
import ro.ddanciu.jevo.core.FitnessFunction;
import ro.ddanciu.jevo.core.Individual;
import ro.ddanciu.jevo.core.Stopper;
import ro.ddanciu.jevo.core.operators.CrossoverOperator;
import ro.ddanciu.jevo.core.operators.MutationOperator;
import ro.ddanciu.jevo.core.selectors.Selector;

/**
 *
 * @author dan
 */
public class EvoAlgorithmTest {

    public static final int MAX_GENERATIONS = 5;
    private EvoAlgorithm algorithm;
    private Selector selector;
    private CrossoverOperator crossoverOperator;
    private MutationOperator mutationOperator;
    private Stopper stopper;
    private FitnessFunction<Integer> fitnessFunction;

    @Before
    public void init() {

        selector = mock(Selector.class);
        crossoverOperator = mock(CrossoverOperator.class);
        mutationOperator = mock(MutationOperator.class);
        stopper = mock(Stopper.class);
        fitnessFunction = mock(FitnessFunction.class);

        Evaluator<Integer> evaluator = new Evaluator<Integer>();
        evaluator.setFitnessFunction(fitnessFunction);


        algorithm = new EvoAlgorithm();
        algorithm.setSelector(selector);
        algorithm.setCrossoverOperator(crossoverOperator);
        algorithm.setMutationOperator(mutationOperator);
        algorithm.setStopper(stopper);
        algorithm.setEvaluator(evaluator);
        algorithm.setMaxGenerations(MAX_GENERATIONS);

    }

    @Test
    public void basic() {
        Set<Individual<?>> initial = new HashSet<Individual<?>>();
        Individual<?> i1 = Individual.Factory.newInstance(new Object());
        Individual<?> i2 = Individual.Factory.newInstance(new Object());
        Individual<?> i3 = Individual.Factory.newInstance(new Object());

        initial.add(i1);
        initial.add(i2);
        initial.add(i3);

        when(selector.choose(initial))
                .thenReturn(initial);

        when(crossoverOperator.operate(Matchers.any(Iterator.class)))
                .thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Iterator<Individual> it = (Iterator<Individual>) args[0];
                return Collections.singleton(it.next()); //copy
            }
        });

        when(mutationOperator.operate(Matchers.any(Individual.class)))
                .thenReturn(false);

        when(stopper.stop(initial)).thenReturn(false);

        when(fitnessFunction.eval(i1)).thenReturn(1);
        when(fitnessFunction.eval(i2)).thenReturn(2);
        when(fitnessFunction.eval(i3)).thenReturn(3);
        
        Individual individual = algorithm.run(initial);

        Assert.assertNotNull("Best individual not found!", individual);
        Assert.assertEquals("Winner not what expected!", i3, individual);
    }

    @Test
    public void stopByStopper() {
        Set<Individual<?>> initial = new HashSet<Individual<?>>();
        Individual<?> i1 = Individual.Factory.newInstance(new Object());
        Individual<?> i2 = Individual.Factory.newInstance(new Object());
        Individual<?> i3 = Individual.Factory.newInstance(new Object());

        initial.add(i1);
        initial.add(i2);
        initial.add(i3);

        when(selector.choose(initial))
                .thenReturn(initial)
                .thenThrow(new RuntimeException("Should not be reached!"));

        when(crossoverOperator.operate(Matchers.any(Iterator.class)))
                .thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Iterator<Individual> it = (Iterator<Individual>) args[0];
                return Collections.singleton(it.next()); //copy
            }
        });

        when(mutationOperator.operate(Matchers.any(Individual.class)))
                .thenReturn(false);

        when(stopper.stop(initial)).thenReturn(true);

        when(fitnessFunction.eval(i1)).thenReturn(1);
        when(fitnessFunction.eval(i2)).thenReturn(2);
        when(fitnessFunction.eval(i3)).thenReturn(3);

        Individual individual = algorithm.run(initial);

        Assert.assertNotNull("Best individual not found!", individual);
        Assert.assertEquals("Winner not what expected!", i3, individual);
    }
}