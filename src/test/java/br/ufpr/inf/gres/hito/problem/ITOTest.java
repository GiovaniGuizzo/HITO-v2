package br.ufpr.inf.gres.hito.problem;

import java.io.FileNotFoundException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.uma.jmetal.solution.PermutationSolution;

/**
 *
 * @author Giovani Guizzo
 */
public class ITOTest {

    private final ITO ito1;
    private final ITO ito2;

    public ITOTest() throws FileNotFoundException {
        ito1 = new ITO("src/test/resources/test1.txt");
        ito2 = new ITO("src/test/resources/test2.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void testConstructor() throws FileNotFoundException {
        new ITO("src/test/resources/unknown.txt");
    }

    @Test
    public void testMaps() {
        String expected = "0,1.3,4.6\n"
                + "1,0.2\n"
                + "2,1.3,4.5,4.6\n"
                + "3,0.1,0.2\n"
                + "4\n";
        assertEquals(expected, ito1.getMethodMapAsString());
        assertEquals(expected, ito1.getAttributeMapAsString());

        expected = "0,1\n"
                + "1,2,4\n"
                + "2\n"
                + "3\n"
                + "4\n";
        assertEquals(expected, ito1.getExtensionMapAsString());
    }

    @Test
    public void testUniqueIds() {
        String expected = "0,1,2,3,4";
        assertEquals(expected, ito1.getUniqueIdsAsString());
    }

    @Test
    public void testRepairSolution() {
        PermutationSolution<Integer> solution = ito1.createSolution();
        solution.setVariableValue(0, 0);
        solution.setVariableValue(1, 1);
        solution.setVariableValue(2, 2);
        solution.setVariableValue(3, 3);
        solution.setVariableValue(4, 4);

        ito1.evaluate(solution);

        Integer[] expected = new Integer[]{4, 2, 1, 0, 3};
        assertEquals(expected[0], solution.getVariableValue(0));
        assertEquals(expected[1], solution.getVariableValue(1));
        assertEquals(expected[2], solution.getVariableValue(2));
        assertEquals(expected[3], solution.getVariableValue(3));
        assertEquals(expected[4], solution.getVariableValue(4));
    }

    @Test
    public void testRepairSolution2() {
        PermutationSolution<Integer> solution = ito1.createSolution();
        solution.setVariableValue(0, 4);
        solution.setVariableValue(1, 2);
        solution.setVariableValue(2, 1);
        solution.setVariableValue(3, 0);
        solution.setVariableValue(4, 3);

        ito1.evaluate(solution);

        Integer[] expected = new Integer[]{4, 2, 1, 0, 3};
        assertEquals(expected[0], solution.getVariableValue(0));
        assertEquals(expected[1], solution.getVariableValue(1));
        assertEquals(expected[2], solution.getVariableValue(2));
        assertEquals(expected[3], solution.getVariableValue(3));
        assertEquals(expected[4], solution.getVariableValue(4));
    }

    @Test
    public void testObjectives() {
        PermutationSolution<Integer> solution = ito1.createSolution();
        solution.setVariableValue(0, 0);
        solution.setVariableValue(1, 1);
        solution.setVariableValue(2, 2);
        solution.setVariableValue(3, 3);
        solution.setVariableValue(4, 4);

        ito1.evaluate(solution);

        assertEquals(2, solution.getObjective(0), 0.00001);
        assertEquals(2, solution.getObjective(1), 0.00001);
    }

    @Test
    public void testObjectives2() {
        PermutationSolution<Integer> solution = ito1.createSolution();
        solution.setVariableValue(0, 4);
        solution.setVariableValue(1, 2);
        solution.setVariableValue(2, 1);
        solution.setVariableValue(3, 0);
        solution.setVariableValue(4, 3);

        ito1.evaluate(solution);

        assertEquals(2, solution.getObjective(0), 0.00001);
        assertEquals(2, solution.getObjective(1), 0.00001);
    }

    @Test
    public void testObjectives3() {
        PermutationSolution<Integer> solution = ito2.createSolution();
        solution.setVariableValue(0, 0);
        solution.setVariableValue(1, 1);
        solution.setVariableValue(2, 2);
        solution.setVariableValue(3, 3);
        solution.setVariableValue(4, 4);

        ito2.evaluate(solution);

        assertEquals(3, solution.getObjective(0), 0.00001);
        assertEquals(3, solution.getObjective(1), 0.00001);
    }

}
