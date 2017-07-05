/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.hito.algorithm;

import br.ufpr.inf.gres.hito.lowlevelheuristic.LowLevelHeuristic;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.uma.jmetal.operator.impl.crossover.BLXAlphaCrossover;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.SimpleRandomMutation;
import org.uma.jmetal.operator.impl.mutation.UniformMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

/**
 *
 * @author Giovani
 */
public class HITOTest {

    private HITO<DoubleSolution> hito;

    public HITOTest() {
        this.hito = new HITO<>(new DTLZ1(),
                1,
                2,
                1,
                1,
                Lists.newArrayList(new BLXAlphaCrossover(1.0), new SBXCrossover(1.0, 0.5)),
                Lists.newArrayList(new SimpleRandomMutation(1.0), new UniformMutation(1.0, 0.5)),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        new HITO<>(new DTLZ1(),
                1,
                2,
                1,
                1,
                Lists.newArrayList(),
                Lists.newArrayList(),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());
    }

    @Test
    public void testGetNameAndDescription() {
        assertEquals("Hyper-Heuristic for the Integration and Test Order Problem using NSGA-II", hito.getDescription());
        assertEquals("HITO (NSGA-II)", hito.getName());
    }

    @Test
    public void testGetLowLevelHeuristics() {
        LowLevelHeuristic llh1 = new LowLevelHeuristic(new BLXAlphaCrossover(1.0), null);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new BLXAlphaCrossover(1.0), new SimpleRandomMutation(1.0));
        LowLevelHeuristic llh3 = new LowLevelHeuristic(new BLXAlphaCrossover(1.0), new UniformMutation(1.0, 0.5));
        LowLevelHeuristic llh4 = new LowLevelHeuristic(new SBXCrossover(1.0, 0.5), null);
        LowLevelHeuristic llh5 = new LowLevelHeuristic(new SBXCrossover(1.0, 0.5), new SimpleRandomMutation(1.0));
        LowLevelHeuristic llh6 = new LowLevelHeuristic(new SBXCrossover(1.0, 0.5), new UniformMutation(1.0, 0.5));
        LowLevelHeuristic llh7 = new LowLevelHeuristic(null, new SimpleRandomMutation(1.0));
        LowLevelHeuristic llh8 = new LowLevelHeuristic(null, new UniformMutation(1.0, 0.5));

        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(8, llhs.size());

        final Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(llh1, iterator.next());
        assertEquals(llh2, iterator.next());
        assertEquals(llh3, iterator.next());
        assertEquals(llh4, iterator.next());
        assertEquals(llh5, iterator.next());
        assertEquals(llh6, iterator.next());
        assertEquals(llh7, iterator.next());
        assertEquals(llh8, iterator.next());
    }

    @Test
    public void testGetLowLevelHeuristics2() {
        HITO<DoubleSolution> hito = new HITO<>(new DTLZ1(),
                1,
                2,
                1,
                1,
                Lists.newArrayList(new BLXAlphaCrossover(1.0), new SBXCrossover(1.0, 0.5)),
                Lists.newArrayList(),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new BLXAlphaCrossover(1.0), null);
        LowLevelHeuristic llh4 = new LowLevelHeuristic(new SBXCrossover(1.0, 0.5), null);

        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(2, llhs.size());

        final Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(llh1, iterator.next());
        assertEquals(llh4, iterator.next());
    }

    @Test
    public void testGetLowLevelHeuristics3() {
        HITO<DoubleSolution> hito = new HITO<>(new DTLZ1(),
                1,
                2,
                1,
                1,
                Lists.newArrayList(),
                Lists.newArrayList(new SimpleRandomMutation(1.0), new UniformMutation(1.0, 0.5)),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());

        LowLevelHeuristic llh7 = new LowLevelHeuristic(null, new SimpleRandomMutation(1.0));
        LowLevelHeuristic llh8 = new LowLevelHeuristic(null, new UniformMutation(1.0, 0.5));

        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(2, llhs.size());

        final Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(llh7, iterator.next());
        assertEquals(llh8, iterator.next());
    }

    @Test
    public void testInitializeLowLevelHeuristics() {
        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(8, llhs.size());

        final Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
    }

    @Test
    public void testInitializeLowLevelHeuristics2() {
        final JMetalRandom jmetalRandom = JMetalRandom.getInstance();
        PseudoRandomGenerator oldRandomGenerator = jmetalRandom.getRandomGenerator();

        jmetalRandom.setRandomGenerator(new JavaRandomGenerator(37966));

        HITO<DoubleSolution> hito = new HITO<>(new DTLZ1(),
                1,
                2,
                1,
                1,
                Lists.newArrayList(new BLXAlphaCrossover(1.0), new SBXCrossover(1.0, 0.5)),
                Lists.newArrayList(new SimpleRandomMutation(1.0), new UniformMutation(1.0, 0.5)),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());

        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(8, llhs.size());

        final Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(0.75, iterator.next().getQuality(), 0.000001);
        assertEquals(0.0, iterator.next().getQuality(), 0.000001);
        assertEquals(1.0, iterator.next().getQuality(), 0.000001);
        assertEquals(0.0, iterator.next().getQuality(), 0.000001);
        assertEquals(0.25, iterator.next().getQuality(), 0.000001);
        assertEquals(0.5, iterator.next().getQuality(), 0.000001);
        assertEquals(0.25, iterator.next().getQuality(), 0.000001);
        assertEquals(0.0, iterator.next().getQuality(), 0.000001);

        jmetalRandom.setRandomGenerator(oldRandomGenerator);
    }

    @Test
    public void testRun() {
        final JMetalRandom jmetalRandom = JMetalRandom.getInstance();
        PseudoRandomGenerator oldRandomGenerator = jmetalRandom.getRandomGenerator();

        jmetalRandom.setRandomGenerator(new JavaRandomGenerator(37966));

        HITO<DoubleSolution> hito = new HITO<>(new DTLZ1(),
                4,
                2,
                1,
                1,
                Lists.newArrayList(new BLXAlphaCrossover(1.0), new SBXCrossover(1.0, 0.5)),
                Lists.newArrayList(new SimpleRandomMutation(1.0), new UniformMutation(1.0, 0.5)),
                new BinaryTournamentSelection<>(),
                new SequentialSolutionListEvaluator());

        final Set<LowLevelHeuristic<DoubleSolution>> llhs = hito.getLowLevelHeuristics();
        assertEquals(8, llhs.size());

        hito.run();

        Iterator<LowLevelHeuristic<DoubleSolution>> iterator = llhs.iterator();
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);

        hito.run();

        iterator = llhs.iterator();
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);

        hito.run();

        iterator = llhs.iterator();
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);

        hito.run();

        iterator = llhs.iterator();
        assertEquals(2, iterator.next().getTime(), 0.000001);
        assertEquals(4, iterator.next().getTime(), 0.000001);
        assertEquals(3, iterator.next().getTime(), 0.000001);
        assertEquals(4, iterator.next().getTime(), 0.000001);
        assertEquals(0, iterator.next().getTime(), 0.000001);
        assertEquals(1, iterator.next().getTime(), 0.000001);
        assertEquals(4, iterator.next().getTime(), 0.000001);
        assertEquals(4, iterator.next().getTime(), 0.000001);

        jmetalRandom.setRandomGenerator(oldRandomGenerator);
    }

}
