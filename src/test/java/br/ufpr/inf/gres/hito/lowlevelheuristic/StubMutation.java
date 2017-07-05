/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.hito.lowlevelheuristic;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Giovani
 */
public class StubMutation implements MutationOperator<Solution<?>> {

    public StubMutation() {
    }

    @Override
    public Solution<?> execute(Solution<?> source) {
        for (int i = 0; i < source.getNumberOfVariables(); i++) {
            source.setVariableValue(i, null);
        }
        return source;
    }

}
