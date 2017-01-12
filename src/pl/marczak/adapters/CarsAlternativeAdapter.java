package pl.marczak.adapters;

import MCDA.definitions.*;

import static pl.marczak.adapters.IntegerResponse.isInteger;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 48
 */
public class CarsAlternativeAdapter extends DefaultAlternativeAdapter {

    public CarsAlternativeAdapter(String[] attributeNames, OptimizationDirection[] directions, double[] weights, double[][] thresholds) {
        super(attributeNames, directions, weights, thresholds);
    }

    @Override
    public String separator() {
        return ",";
    }

    @Override
    public String alternativePrefix() {
        return "car_";
    }

    @Override
    public void fillAlternative(Alternative alternative, String[] attrs) {
        for (int j = 0; j < attrs.length; j++) {
            String attr = attrs[j];
            CriterionDefinition cdef = createCriterionDefinition(j);
            CriterionValue cv = new CriterionValue(cdef, mapToValue(attr));
            alternative.addCriterion(cv);
        }
    }

    static int mapToValue(String attr) {

        if (attr.equalsIgnoreCase("v-high")) {
            return 4;
        } else if (attr.equalsIgnoreCase("big") || attr.equalsIgnoreCase("high")) {
            return 3;
        } else if (attr.equalsIgnoreCase("med") || attr.equalsIgnoreCase("big") || attr.equalsIgnoreCase("acc")) {
            return 2;
        } else {

            IntegerResponse response = isInteger(attr);

            if (response.isInteger) return response.value;
            else return 1;
        }
    }

}
