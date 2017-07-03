package br.ufpr.inf.gres.hito.problem;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.uma.jmetal.problem.impl.AbstractIntegerPermutationProblem;
import org.uma.jmetal.solution.PermutationSolution;

/**
 *
 * @author Giovani Guizzo
 */
public class ITO extends AbstractIntegerPermutationProblem {

    protected Map<Integer, Set<String>> methodMap;
    protected Map<Integer, Set<String>> attributeMap;
    protected Map<Integer, Set<String>> extensionMap;
    protected Set<Integer> uniqueUnitIds;

    public ITO(String methodMatrixFile) throws ClassNotFoundException {
        this.methodMap = new LinkedHashMap<>();
        this.attributeMap = new LinkedHashMap<>();
        this.extensionMap = new LinkedHashMap<>();
        this.uniqueUnitIds = new LinkedHashSet();
        this.readFile(methodMatrixFile);

        setNumberOfVariables(uniqueUnitIds.size());
        setNumberOfObjectives(2);
        setName("ITO");
    }

    protected final void readFile(String methodMatrixFile) {
        try (Scanner matrixScanner = new Scanner(new File(methodMatrixFile))) {

            List<Map<Integer, Set<String>>> workingList = new ArrayList<>();
            workingList.add(methodMap);
            workingList.add(attributeMap);
            workingList.add(extensionMap);

            Splitter splitter = Splitter.on(",");

            int workingIndex = 0;
            Map<Integer, Set<String>> workingMap = workingList.get(workingIndex++);
            while (matrixScanner.hasNextLine()) {
                String line = matrixScanner.nextLine();
                if (!line.isEmpty()) {
                    if (line.equals("END")) {
                        workingMap = workingList.get(workingIndex++);
                        continue;
                    }

                    Iterator<String> splitIterator = splitter.split(line).iterator();
                    Integer unit = Integer.parseInt(splitIterator.next());
                    uniqueUnitIds.add(unit);
                    Set<String> setOfDependencies = new LinkedHashSet<>();
                    while (splitIterator.hasNext()) {
                        setOfDependencies.add(splitIterator.next());
                    }
                    workingMap.put(unit, setOfDependencies);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void evaluate(PermutationSolution<Integer> solution) {
        repairSolution(solution);

        int methodFitness = 0;
        int attributeFitness = 0;

        Set<Integer> testedUnits = new LinkedHashSet<>();
        Set<Integer> emulatedMethods = new LinkedHashSet<>();
        Set<Integer> emulatedAttributes = new LinkedHashSet<>();

        Splitter splitter = Splitter.on(".");

        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            int unit = solution.getVariableValue(i);
            testedUnits.add(unit);

            for (String methodDependency : methodMap.get(unit)) {
                Iterator<String> dependencyIterator = splitter.split(methodDependency).iterator();
                int dependentUnit = Integer.parseInt(dependencyIterator.next());
                if (!testedUnits.contains(dependentUnit)) {
                    Integer method = Integer.parseInt(dependencyIterator.next());
                    if (!emulatedMethods.contains(method)) {
                        emulatedMethods.add(method);
                        methodFitness++;
                    }
                }
            }

            for (String attributeDependency : attributeMap.get(unit)) {
                Iterator<String> dependencyIterator = splitter.split(attributeDependency).iterator();
                int dependentUnit = Integer.parseInt(dependencyIterator.next());
                if (!testedUnits.contains(dependentUnit)) {
                    Integer attribute = Integer.parseInt(dependencyIterator.next());
                    if (!emulatedAttributes.contains(attribute)) {
                        emulatedAttributes.add(attribute);
                        attributeFitness++;
                    }
                }
            }
        }

        solution.setObjective(0, methodFitness);
        solution.setObjective(1, attributeFitness);
    }

    private void repairSolution(PermutationSolution<Integer> solution) {
        Set<Integer> testedUnits = new HashSet<>();
        List<Integer> variablesList = new ArrayList<>();

        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            int unit = solution.getVariableValue(i);
            variablesList.add(unit);
        }

        for (int i = 0; i < variablesList.size(); i++) {
            Integer unit = variablesList.get(i);
            testedUnits.add(unit);
            Set<String> extendedUnits = extensionMap.get(unit);
            boolean decrement = false;
            if (extendedUnits != null) {
                for (String extendedUnit : extendedUnits) {
                    Integer extendedUnitId = Integer.parseInt(extendedUnit.trim());
                    if (!testedUnits.contains(extendedUnitId)) {
                        variablesList.remove(extendedUnitId);
                        variablesList.add(i, extendedUnitId);
                        decrement = true;
                    }
                }
            }
            if (decrement) {
                i--;
            }
        }

        for (int i = 0; i < variablesList.size(); i++) {
            solution.setVariableValue(i, variablesList.get(i));
        }
    }

    @Override
    public int getPermutationLength() {
        return uniqueUnitIds.size();
    }

    public String getMethodMapAsString() {
        return getMapAsString(methodMap);
    }

    public String getAttributeMapAsString() {
        return getMapAsString(attributeMap);
    }

    public String getExtensionMapAsString() {
        return getMapAsString(extensionMap);
    }

    public Map<Integer, Set<String>> getMethodMatrix() {
        return new LinkedHashMap<>(methodMap);
    }

    public Map<Integer, Set<String>> getAttributeMatrix() {
        return new LinkedHashMap<>(attributeMap);
    }

    public Map<Integer, Set<String>> getExtensionMatrix() {
        return new LinkedHashMap<>(extensionMap);
    }

    private String getMapAsString(Map<Integer, Set<String>> map) {
        Joiner joiner = Joiner.on(",");
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Set<String>> entry : map.entrySet()) {
            Set<String> line = new LinkedHashSet<>();
            line.add(entry.getKey().toString());
            for (String string : entry.getValue()) {
                line.add(string);
            }
            joiner.appendTo(result, line);
            result.append("\n");
        }
        return result.toString();
    }

    public String getUniqueIdsAsString() {
        return Joiner.on(",").join(uniqueUnitIds);
    }

}
