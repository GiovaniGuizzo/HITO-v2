/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpr.inf.gres.hito.lowlevelheuristic;

import com.google.common.collect.Lists;
import java.util.List;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;

/**
 *
 * @author Giovani
 */
public class StubCrossover implements CrossoverOperator<Solution<?>> {

    public StubCrossover() {
    }

    @Override
    public int getNumberOfParents() {
        return 2;
    }

    @Override
    public List<Solution<?>> execute(List<Solution<?>> source) {
        if (null == source) {
            throw new JMetalException("Null parameter");
        } else if (source.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + source.size());
        }

        return Lists.newArrayList(source.get(1).copy(), source.get(0).copy());
    }

}
