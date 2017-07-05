package br.ufpr.inf.gres.hito.algorithm;

import br.ufpr.inf.gres.hito.lowlevelheuristic.LowLevelHeuristic;
import br.ufpr.inf.gres.hito.selectionmethod.ChoiceFunction;
import com.google.common.base.Preconditions;
import java.util.*;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

/**
 *
 * @author Giovani Guizzo
 * @param <S>
 */
public class HITO<S extends Solution<?>> extends NSGAII<S> {

    protected ChoiceFunction<S> choiceFunction;
    protected Set<LowLevelHeuristic<S>> lowLevelHeuristics;

    public HITO(Problem<S> problem,
            int maxEvaluations,
            int populationSize,
            double alpha,
            double beta,
            Collection<CrossoverOperator<S>> crossoverOperators,
            Collection<MutationOperator<S>> mutationOperators,
            SelectionOperator<List<S>, S> selectionOperator,
            SolutionListEvaluator<S> evaluator) {
        super(problem,
                maxEvaluations,
                populationSize,
                null,
                null,
                selectionOperator,
                evaluator);
        this.choiceFunction = new ChoiceFunction(alpha, beta);
        initalizeLowLevelHeuristics(crossoverOperators, mutationOperators);
    }

    @Override
    public String getDescription() {
        return "Hyper-Heuristic for the Integration and Test Order Problem using NSGA-II";
    }

    @Override
    public String getName() {
        return "HITO (NSGA-II)";
    }

    public Set<LowLevelHeuristic<S>> getLowLevelHeuristics() {
        return lowLevelHeuristics;
    }

    protected void initalizeLowLevelHeuristics(Collection<CrossoverOperator<S>> crossoverOperators, Collection<MutationOperator<S>> mutationOperators) {
        buildLowLevelHeursitics(crossoverOperators, mutationOperators);
        initialExecutionLowLevelHeuristics();
    }

    protected void buildLowLevelHeursitics(Collection<CrossoverOperator<S>> crossoverOperators, Collection<MutationOperator<S>> mutationOperators) {
        this.lowLevelHeuristics = new LinkedHashSet<>();

        for (CrossoverOperator<S> crossoverOperator : crossoverOperators) {
            this.lowLevelHeuristics.add(new LowLevelHeuristic<>(crossoverOperator, null));
            for (MutationOperator<S> mutationOperator : mutationOperators) {
                this.lowLevelHeuristics.add(new LowLevelHeuristic<>(crossoverOperator, mutationOperator));
            }
        }

        for (MutationOperator<S> mutationOperator : mutationOperators) {
            this.lowLevelHeuristics.add(new LowLevelHeuristic<>(null, mutationOperator));
        }

        Preconditions.checkArgument(!this.lowLevelHeuristics.isEmpty(), "There must be at least one crossover or mutation operator.");
    }

    protected void initialExecutionLowLevelHeuristics() {
        List<S> parents = new ArrayList<>();
        parents.add(getProblem().createSolution());
        parents.add(getProblem().createSolution());

        this.evaluatePopulation(parents);

        for (LowLevelHeuristic<S> lowLevelHeuristic : this.lowLevelHeuristics) {
            List<S> offspring = lowLevelHeuristic.apply(parents);
            this.evaluatePopulation(offspring);
            evaluateLowLevelHeuristic(lowLevelHeuristic, parents, offspring);
        }
    }

    protected void evaluateLowLevelHeuristic(LowLevelHeuristic<S> lowLevelHeuristic, Collection<S> parents, Collection<S> children) {
        double quality = 0.0;

        DominanceComparator<S> dominanceComparator = new DominanceComparator<>();
        for (S parent : parents) {
            for (S child : children) {
                quality += ((double) dominanceComparator.compare(parent, child) + 1) / 2;
            }
        }

        quality /= (parents.size() * children.size());

        lowLevelHeuristic.setQuality(quality);
    }

    @Override
    protected List<S> reproduction(List<S> matingPopulation) {
        List<S> offspringPopulation = new ArrayList<>();

        int i = 0;
        while (offspringPopulation.size() < getMaxPopulationSize()) {
            LowLevelHeuristic<S> chosenLowLevelHeuristic = this.choiceFunction.choose(this.lowLevelHeuristics);

            List<S> parents = new ArrayList<>();
            for (int j = 0; j < chosenLowLevelHeuristic.getNumberOfParents(); j++) {
                parents.add(matingPopulation.get(i + j));
            }

            List<S> generatedChildren = chosenLowLevelHeuristic.apply(parents);
            this.evaluatePopulation(generatedChildren);

            this.evaluateLowLevelHeuristic(chosenLowLevelHeuristic, parents, generatedChildren);
            this.updateLowLevelHeuristics(chosenLowLevelHeuristic);

            offspringPopulation.addAll(generatedChildren);
            i += generatedChildren.size();
        }
        return offspringPopulation;
    }

    protected void updateLowLevelHeuristics(LowLevelHeuristic<S> appliedLowLevelHeuristic) {
        for (LowLevelHeuristic<S> lowLevelHeuristic : lowLevelHeuristics) {
            lowLevelHeuristic.setTime(lowLevelHeuristic.getTime() + 1);
        }
        appliedLowLevelHeuristic.setTime(0);
    }

    @Override
    public void run() {
        List<S> offspringPopulation;
        List<S> matingPopulation;

        setPopulation(createInitialPopulation());
        setPopulation(evaluatePopulation(getPopulation()));
        initProgress();
        while (!isStoppingConditionReached()) {
            matingPopulation = selection(getPopulation());
            offspringPopulation = reproduction(matingPopulation);
            setPopulation(replacement(getPopulation(), offspringPopulation));
            updateProgress();
        }
    }

}
