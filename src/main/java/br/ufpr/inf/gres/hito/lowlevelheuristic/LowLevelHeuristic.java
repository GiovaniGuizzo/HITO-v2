package br.ufpr.inf.gres.hito.lowlevelheuristic;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Objects;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.impl.crossover.NullCrossover;
import org.uma.jmetal.operator.impl.mutation.NullMutation;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 * @param <S>
 */
public class LowLevelHeuristic<S extends Solution<?>> {

    protected double quality;
    protected double time;

    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;

    public LowLevelHeuristic(CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
        Preconditions.checkArgument((crossoverOperator != null && !(crossoverOperator instanceof NullCrossover))
                || (mutationOperator != null && !(mutationOperator instanceof NullMutation)),
                "A Low Level Heuristic must have at least one operator.");
        this.quality = 0.0;
        this.time = 0;
        this.setCrossoverOperator(crossoverOperator);
        this.setMutationOperator(mutationOperator);
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public void setCrossoverOperator(CrossoverOperator<S> crossoverOperator) {
        if (crossoverOperator != null) {
            this.crossoverOperator = crossoverOperator;
        } else {
            this.crossoverOperator = new NullCrossover<>();
        }
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public void setMutationOperator(MutationOperator<S> mutationOperator) {
        if (mutationOperator != null) {
            this.mutationOperator = mutationOperator;
        } else {
            this.mutationOperator = new NullMutation<>();
        }
    }

    public String getName() {
        return "C: " + crossoverOperator.getClass().getSimpleName()
                + ", M: " + mutationOperator.getClass().getSimpleName();
    }

    public int getNumberOfParents() {
        return crossoverOperator.getNumberOfParents();
    }

    public List<S> apply(List<S> parents) {
        List<S> offspring = crossoverOperator.execute(parents);

        for (S s : offspring) {
            mutationOperator.execute(s);
        }

        return offspring;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LowLevelHeuristic<?> other = (LowLevelHeuristic<?>) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
