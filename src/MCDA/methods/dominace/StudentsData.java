package MCDA.methods.dominace;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionDefinition;
import MCDA.definitions.CriterionValue;
import MCDA.definitions.OptimizationDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2017-01-02.
 */
public class StudentsData {


    List<Alternative> alternatives = new ArrayList<>();


    public StudentsData() {

        double[] weights = new double[]{0.25, 0.25, 0.25, 0.25,};

        OptimizationDirection max = OptimizationDirection.MAXIMIZE;

        CriterionDefinition mathCritDef = new CriterionDefinition("Mathematics", max,
                weights[0], null);
        CriterionDefinition physicsCritDef = new CriterionDefinition("Physics", max,
                weights[1], null);
        CriterionDefinition literatureCritDef = new CriterionDefinition("Literature", max,
                weights[2], null);
        CriterionDefinition comprEvalCritDef = new CriterionDefinition("Comprehensive Evaluation",
                max, weights[3], null);

        int BAD = 0;
        int MEDIUM = 1;
        int GOOD = 2;

        Alternative alternativeS1 = new Alternative("S1");

        List<CriterionValue> criterionValues = new ArrayList<>();
        criterionValues.add(new CriterionValue(mathCritDef, GOOD));
        criterionValues.add(new CriterionValue(physicsCritDef, MEDIUM));
        criterionValues.add(new CriterionValue(literatureCritDef, BAD));
        criterionValues.add(new CriterionValue(comprEvalCritDef, BAD));

        alternativeS1.setCriteriaValues(criterionValues);
        alternatives.add(alternativeS1);

        Alternative alternativeS2 = new Alternative("S2");

        List<CriterionValue> criterionValuesS2 = new ArrayList<>();
        criterionValuesS2.add(new CriterionValue(mathCritDef, MEDIUM));
        criterionValuesS2.add(new CriterionValue(physicsCritDef, MEDIUM));
        criterionValuesS2.add(new CriterionValue(literatureCritDef, BAD));
        criterionValuesS2.add(new CriterionValue(comprEvalCritDef, MEDIUM));

        alternativeS2.setCriteriaValues(criterionValuesS2);
        alternatives.add(alternativeS2);


        Alternative alternativeS3 = new Alternative("S3");

        List<CriterionValue> criterionValuesS3 = new ArrayList<>();
        criterionValuesS3.add(new CriterionValue(mathCritDef, MEDIUM));
        criterionValuesS3.add(new CriterionValue(physicsCritDef, MEDIUM));
        criterionValuesS3.add(new CriterionValue(literatureCritDef, MEDIUM));
        criterionValuesS3.add(new CriterionValue(comprEvalCritDef, MEDIUM));

        alternativeS3.setCriteriaValues(criterionValuesS3);
        alternatives.add(alternativeS3);

        Alternative alternativeS4 = new Alternative("S4");

        List<CriterionValue> criterionValuesS4 = new ArrayList<>();
        criterionValuesS4.add(new CriterionValue(mathCritDef, GOOD));
        criterionValuesS4.add(new CriterionValue(physicsCritDef, GOOD));
        criterionValuesS4.add(new CriterionValue(literatureCritDef, MEDIUM));
        criterionValuesS4.add(new CriterionValue(comprEvalCritDef, GOOD));

        alternativeS4.setCriteriaValues(criterionValuesS4);
        alternatives.add(alternativeS4);

        Alternative alternativeS5 = new Alternative("S5");

        List<CriterionValue> criterionValuesS5 = new ArrayList<>();
        criterionValuesS5.add(new CriterionValue(mathCritDef, GOOD));
        criterionValuesS5.add(new CriterionValue(physicsCritDef, MEDIUM));
        criterionValuesS5.add(new CriterionValue(literatureCritDef, GOOD));
        criterionValuesS5.add(new CriterionValue(comprEvalCritDef, GOOD));

        alternativeS5.setCriteriaValues(criterionValuesS5);
        alternatives.add(alternativeS5);


        Alternative alternativeS6 = new Alternative("S6");

        List<CriterionValue> criterionValuesS6 = new ArrayList<>();
        criterionValuesS6.add(new CriterionValue(mathCritDef, GOOD));
        criterionValuesS6.add(new CriterionValue(physicsCritDef, GOOD));
        criterionValuesS6.add(new CriterionValue(literatureCritDef, GOOD));
        criterionValuesS6.add(new CriterionValue(comprEvalCritDef, GOOD));

        alternativeS6.setCriteriaValues(criterionValuesS6);
        alternatives.add(alternativeS6);


        Alternative alternativeS7 = new Alternative("S7");

        List<CriterionValue> criterionValuesS7 = new ArrayList<>();
        criterionValuesS7.add(new CriterionValue(mathCritDef, BAD));
        criterionValuesS7.add(new CriterionValue(physicsCritDef, BAD));
        criterionValuesS7.add(new CriterionValue(literatureCritDef, BAD));
        criterionValuesS7.add(new CriterionValue(comprEvalCritDef, BAD));

        alternativeS7.setCriteriaValues(criterionValuesS7);
        alternatives.add(alternativeS7);

        Alternative alternativeS8 = new Alternative("S8");

        List<CriterionValue> criterionValuesS8 = new ArrayList<>();
        criterionValuesS8.add(new CriterionValue(mathCritDef, BAD));
        criterionValuesS8.add(new CriterionValue(physicsCritDef, BAD));
        criterionValuesS8.add(new CriterionValue(literatureCritDef, MEDIUM));
        criterionValuesS8.add(new CriterionValue(comprEvalCritDef, BAD));

        alternativeS8.setCriteriaValues(criterionValuesS8);
        alternatives.add(alternativeS8);



    }
}
