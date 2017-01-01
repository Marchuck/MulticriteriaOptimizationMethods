package MCDA.definitions;

import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class CriterionClass {
    public double[] values;

    public CriterionClass(double[] values) {
        this.values = values;
    }

    public static List<CriterionClass> createProblemFrom(MCDAProblem problem){

        CriteriaClassBuilder classBuilder = new CriteriaClassBuilder(problem.critera);
        classBuilder.addCriterionClass(new double[] { 10000, 100000, 1000000 }); // NPV
        classBuilder.addCriterionClass(new double[] { -5000000 - 200000 - 100000 }); // Ryzyko - minimalizowane kryterium, wartosci przeciwne
        classBuilder.addCriterionClass(new double[] { 0.5, 1, 2 }); // Factor
        classBuilder.addCriterionClass(new double[] { 5, 9, 15 }); // StrategicPos
        return classBuilder.getClasses();
    }
}
