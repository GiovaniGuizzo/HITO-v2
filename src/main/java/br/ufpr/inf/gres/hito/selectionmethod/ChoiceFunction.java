package br.ufpr.inf.gres.hito.selectionmethod;

import br.ufpr.inf.gres.hito.lowlevelheuristic.LowLevelHeuristic;
import com.google.common.collect.ArrayListMultimap;
import java.util.Collection;
import java.util.List;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.pseudorandom.JMetalRandom;

/**
 *
 * @author Giovani Guizzo
 */
public class ChoiceFunction<S extends Solution<?>> {

    protected double alpha;
    protected double beta;
    private final JMetalRandom random;

    public ChoiceFunction(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
        this.random = JMetalRandom.getInstance();
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
        int randomValue = random.nextInt(0, bestLowLevelHeuristics.size() - 1);

        return bestLowLevelHeuristics.get(randomValue);
    }

}
