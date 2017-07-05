/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.hito.lowlevelheuristic;

import com.google.common.collect.Lists;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.crossover.PMXCrossover;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.impl.DefaultDoubleSolution;

/**
 *
 * @author Giovani
 */
public class LowLevelHeuristicTest {

    public LowLevelHeuristicTest() {
    }

    @Test
    public void testGetQuality() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        assertEquals(0.0, instance.getQuality(), 0.0000001);
        instance.setQuality(3.0);
        assertEquals(3.0, instance.getQuality(), 0.0000001);
    }

    @Test
    public void testGetTime() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        assertEquals(0.0, instance.getTime(), 0.0000001);
        instance.setTime(3.0);
        assertEquals(3.0, instance.getTime(), 0.0000001);
    }

    @Test
    public void testGetCrossoverOperator() {
        final PMXCrossover operator = new PMXCrossover(1.0);
        LowLevelHeuristic instance = new LowLevelHeuristic(operator, null);
        assertEquals(operator, instance.getCrossoverOperator());
        assertTrue(instance.getMutationOperator() instanceof NullMutation);
        instance.setCrossoverOperator(null);
        assertTrue(instance.getCrossoverOperator() instanceof NullCrossover);
    }

    @Test
    public void testGetMutationOperator() {
        final PermutationSwapMutation operator = new PermutationSwapMutation<>(1.0);
        LowLevelHeuristic instance = new LowLevelHeuristic(null, operator);
        assertEquals(operator, instance.getMutationOperator());
        assertTrue(instance.getCrossoverOperator() instanceof NullCrossover);
        instance.setMutationOperator(null);
        assertTrue(instance.getMutationOperator() instanceof NullMutation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        LowLevelHeuristic instance = new LowLevelHeuristic(null, new PermutationSwapMutation<>(1.0));
        instance = new LowLevelHeuristic(new PMXCrossover(1.0), null);
        instance = new LowLevelHeuristic(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        instance = new LowLevelHeuristic(new PMXCrossover(1.0), new NullMutation());
        instance = new LowLevelHeuristic(new NullCrossover(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor3() {
        LowLevelHeuristic instance = new LowLevelHeuristic(null, new NullMutation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor4() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new NullCrossover(), new NullMutation());
    }

    @Test
    public void testGetName() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        assertEquals("C: PMXCrossover, M: PermutationSwapMutation", instance.getName());
        instance.setCrossoverOperator(new NullCrossover());
        assertEquals("C: NullCrossover, M: PermutationSwapMutation", instance.getName());
        instance.setMutationOperator(null);
        assertEquals("C: NullCrossover, M: NullMutation", instance.getName());
        instance.setMutationOperator(new PermutationSwapMutation<>(1.0));
        assertEquals("C: NullCrossover, M: PermutationSwapMutation", instance.getName());
    }

    @Test
    public void testGetNumberOfParents() {
        final PMXCrossover pmxCrossover = new PMXCrossover(1.0);
        LowLevelHeuristic instance = new LowLevelHeuristic(pmxCrossover, new PermutationSwapMutation<>(1.0));
        assertEquals(pmxCrossover.getNumberOfParents(), instance.getNumberOfParents());
        final NullCrossover nullCrossover = new NullCrossover();
        instance.setCrossoverOperator(nullCrossover);
        assertEquals(nullCrossover.getNumberOfParents(), instance.getNumberOfParents());
    }

    @Test
    public void testHashCode() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        LowLevelHeuristic instance2 = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        assertEquals(instance.hashCode(), instance2.hashCode());
        instance2.setCrossoverOperator(new NullCrossover());
        assertNotEquals(instance.hashCode(), instance2.hashCode());
        instance2.setCrossoverOperator(new PMXCrossover(1.0));
        assertEquals(instance.hashCode(), instance2.hashCode());
        instance2.setMutationOperator(new NullMutation());
        assertNotEquals(instance.hashCode(), instance2.hashCode());
        instance2.setMutationOperator(new PermutationSwapMutation(1.0));
        assertEquals(instance.hashCode(), instance2.hashCode());
    }

    @Test
    public void testEquals() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        LowLevelHeuristic instance2 = new LowLevelHeuristic(new PMXCrossover(1.0), new PermutationSwapMutation<>(1.0));
        assertEquals(instance, instance2);
        assertNotEquals(instance, null);
        assertNotEquals(instance, new Object());
        instance2.setCrossoverOperator(new NullCrossover());
        assertNotEquals(instance, instance2);
        instance2.setCrossoverOperator(new PMXCrossover(1.0));
        assertEquals(instance, instance2);
        instance2.setMutationOperator(new NullMutation());
        assertNotEquals(instance, instance2);
        instance2.setMutationOperator(new PermutationSwapMutation(1.0));
        assertEquals(instance, instance2);
    }

    @Test
    public void testApply() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new StubCrossover(), null);
        final DTLZ1 problem = new DTLZ1();

        final DefaultDoubleSolution parent1 = new DefaultDoubleSolution(problem);
        final DefaultDoubleSolution parent2 = new DefaultDoubleSolution(problem);

        List<Solution> parents = Lists.newArrayList(parent1, parent2);
        List<Solution> offspring = instance.apply(parents);

        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            assertEquals(parents.get(1).getVariableValue(i), offspring.get(0).getVariableValue(i));
        }

        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            assertEquals(parents.get(0).getVariableValue(i), offspring.get(1).getVariableValue(i));
        }
    }

    @Test
    public void testApply2() {
        LowLevelHeuristic instance = new LowLevelHeuristic(null, new StubMutation());
        final DTLZ1 problem = new DTLZ1();

        final DefaultDoubleSolution parent1 = new DefaultDoubleSolution(problem);
        final DefaultDoubleSolution parent2 = new DefaultDoubleSolution(problem);

        List<Solution> parents = Lists.newArrayList(parent1, parent2);
        List<Solution> offspring = instance.apply(parents);

        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            assertNull(offspring.get(0).getVariableValue(i));
        }

        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            assertNull(offspring.get(1).getVariableValue(i));
        }
    }

    @Test
    public void testApply3() {
        LowLevelHeuristic instance = new LowLevelHeuristic(new StubCrossover(), new StubMutation());
        final DTLZ1 problem = new DTLZ1();

        final DefaultDoubleSolution parent1 = new DefaultDoubleSolution(problem);
        parent1.setObjective(0, -1);
        parent1.setObjective(1, -1);
        parent1.setObjective(2, -1);
        final DefaultDoubleSolution parent2 = new DefaultDoubleSolution(problem);
        parent2.setObjective(0, -2);
        parent2.setObjective(1, -2);
        parent2.setObjective(2, -2);

        List<Solution> parents = Lists.newArrayList(parent1, parent2);
        List<Solution> offspring = instance.apply(parents);

        for (int i = 0; i < offspring.get(0).getNumberOfObjectives(); i++) {
            assertEquals(parents.get(1).getObjective(i), offspring.get(0).getObjective(i), 0.00001);
        }

        for (int i = 0; i < offspring.get(1).getNumberOfObjectives(); i++) {
            assertEquals(parents.get(0).getObjective(i), offspring.get(1).getObjective(i), 0.00001);
        }

        for (int i = 0; i < offspring.get(0).getNumberOfVariables(); i++) {
            assertNull(offspring.get(0).getVariableValue(i));
        }

        for (int i = 0; i < offspring.get(1).getNumberOfVariables(); i++) {
            assertNull(offspring.get(1).getVariableValue(i));
        }
    }

}
