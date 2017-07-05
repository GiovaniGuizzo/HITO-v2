package br.ufpr.inf.gres.hito.selectionmethod;

import br.ufpr.inf.gres.hito.lowlevelheuristic.LowLevelHeuristic;
import com.google.common.collect.ArrayListMultimap;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 */
public class ChoiceFunction<S extends Solution<?>> {

    protected double alpha;
    protected double beta;
    private final Random random;

    public ChoiceFunction(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
        this.random = new Random();
    }

    public ChoiceFunction(double alpha, double beta, long seed) {
        this.alpha = alpha;
        this.beta = beta;
        this.random = new Random(seed);
    }

    public LowLevelHeuristic<S> choose(Collection<LowLevelHeuristic<S>> lowLevelHeuristics) {
        ArrayListMultimap<Double, LowLevelHeuristic<S>> choiceMap = ArrayListMultimap.create();

        Double bestChoiceValue = Double.MIN_VALUE;

        for (LowLevelHeuristic<S> lowLevelHeuristic : lowLevelHeuristics) {
            double choiceValue = (alpha * lowLevelHeuristic.getQuality()) + (beta * lowLevelHeuristic.getTime());
            choiceMap.put(choiceValue, lowLevelHeuristic);

            if (bestChoiceValue < choiceValue) {
                bestChoiceValue = choiceValue;
            }
        }

        List<LowLevelHeuristic<S>> bestLowLevelHeuristics = choiceMap.get(bestChoiceValue);
        int randomValue = random.nextInt(bestLowLevelHeuristics.size());

        return bestLowLevelHeuristics.get(randomValue);
    }

}
