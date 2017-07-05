/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.hito.selectionmethod;

import br.ufpr.inf.gres.hito.lowlevelheuristic.LowLevelHeuristic;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.mutation.PermutationSwapMutation;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;
import org.uma.jmetal.util.pseudorandom.PseudoRandomGenerator;
import org.uma.jmetal.util.pseudorandom.impl.JavaRandomGenerator;

/**
 *
 * @author Giovani
 */
public class ChoiceFunctionTest {

    private PseudoRandomGenerator oldRandomGenerator;

    public ChoiceFunctionTest() {
    }

    @Before
    public void before() {
        oldRandomGenerator = JMetalRandom.getInstance().getRandomGenerator();
        JMetalRandom.getInstance().setRandomGenerator(new JavaRandomGenerator(2));
    }

    @After
    public void after() {
        JMetalRandom.getInstance().setRandomGenerator(oldRandomGenerator);
    }

    @Test
    public void testChooseTie() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(1);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2);
        assertEquals(llh2, choiceFunction.choose(llhs));
        assertEquals(llh1, choiceFunction.choose(llhs));
        assertEquals(llh2, choiceFunction.choose(llhs));
        assertEquals(llh1, choiceFunction.choose(llhs));

    }

    @Test
    public void testChooseTie2() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(0);
        llh1.setTime(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(1);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2);
        assertEquals(llh2, choiceFunction.choose(llhs));
    }

    @Test
    public void testChooseTie3() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(0);
        llh1.setTime(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(0);
        llh2.setTime(1);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2);
        assertEquals(llh2, choiceFunction.choose(llhs));
    }

    @Test
    public void testChooseTie4() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(0);
        llh1.setTime(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(0);
        llh2.setTime(1);
        LowLevelHeuristic llh3 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh3.setQuality(0);
        llh3.setTime(0);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2, llh3);
        assertEquals(llh2, choiceFunction.choose(llhs));
    }

    @Test
    public void testChooseTie5() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(0);
        llh1.setTime(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(0);
        llh2.setTime(1);
        LowLevelHeuristic llh3 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh3.setQuality(1);
        llh3.setTime(1);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2, llh3);
        assertEquals(llh3, choiceFunction.choose(llhs));
    }

    @Test
    public void testChoose() {
        ChoiceFunction choiceFunction = new ChoiceFunction(1, 1);

        LowLevelHeuristic llh1 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh1.setQuality(1);
        LowLevelHeuristic llh2 = new LowLevelHeuristic(new NullCrossover(), new PermutationSwapMutation<>(1.0));
        llh2.setQuality(0);

        final ArrayList<LowLevelHeuristic> llhs = Lists.newArrayList(llh1, llh2);

        assertEquals(llh1, choiceFunction.choose(llhs));

        llh2.setQuality(2);
        assertEquals(llh2, choiceFunction.choose(llhs));

        llh1.setTime(2);
        assertEquals(llh1, choiceFunction.choose(llhs));

        llh2.setTime(2);
        assertEquals(llh2, choiceFunction.choose(llhs));

        llh1.setQuality(0.5);
        llh2.setQuality(0.500000001);
        assertEquals(llh2, choiceFunction.choose(llhs));
    }

}
