package br.ufpr.inf.gres.hito.lowlevelheuristic;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani Guizzo
 * @param <S>
 */
public class LowLevelHeuristic<S extends Solution<?>> {

    protected final String name;

    protected double quality;
    protected double time;

    protected CrossoverOperator<S> crossoverOperator;
    protected MutationOperator<S> mutationOperator;

    public LowLevelHeuristic(CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
        Preconditions.checkArgument(crossoverOperator != null && mutationOperator != null, "A Low Level Heuristic must have at least one operator.");
        this.quality = 0.0;
        this.time = 0;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;

        this.name = "C: " + (crossoverOperator != null ? crossoverOperator.getClass().getSimpleName() : "NONE")
                + ", M: " + (mutationOperator != null ? mutationOperator.getClass().getSimpleName() : "NONE");
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
        this.crossoverOperator = crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public void setMutationOperator(MutationOperator<S> mutationOperator) {
        this.mutationOperator = mutationOperator;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfParents() {
        return crossoverOperator != null ? crossoverOperator.getNumberOfParents() : 2;
    }

    public List<S> apply(List<S> parents) {
        List<S> offspring;
        if (crossoverOperator != null) {
            offspring = crossoverOperator.execute(parents);
        } else {
            offspring = new ArrayList<>();
            for (S parent : parents) {
                offspring.add((S) parent.copy());
            }
        }

        List<S> resultOffspring = new ArrayList<>();
        if (mutationOperator != null) {
            for (S s : offspring) {
                resultOffspring.add(mutationOperator.execute(s));
            }
        }
        return resultOffspring;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
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
        return Objects.equals(this.name, other.name);
    }

}
