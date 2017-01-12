package pl.marczak.adapters;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionDefinition;
import MCDA.definitions.CriterionValue;
import MCDA.definitions.OptimizationDirection;


/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 48
 */
public class GermanAlternativeAdapter extends DefaultAlternativeAdapter {


    public GermanAlternativeAdapter(String[] attributeNames, OptimizationDirection[] directions, double[] weights, double[][] thresholds) {
        super(attributeNames, directions, weights, thresholds);
    }

    @Override
    public String separator() {
        return " ";
    }

    @Override
    public String alternativePrefix() {
        return "credit_";
    }

    @Override
    public void fillAlternative(Alternative alternative, String[] attributes) {
        for (int j = 0; j < attributes.length; j++) {
            String attr = attributes[j];
            CriterionDefinition cdef = createCriterionDefinition(j);
            CriterionValue cv = new CriterionValue(cdef, Integer.valueOf(attr));
            alternative.addCriterion(cv);
        }
    }
}
