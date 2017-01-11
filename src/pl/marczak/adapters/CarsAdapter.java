package pl.marczak.adapters;

import MCDA.definitions.*;

import static pl.marczak.adapters.CarsAdapter.IntegerResponse.isInteger;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 48
 */
public class CarsAdapter implements DataReader.ReadStrategy<Alternative> {
    String[] attributeNames;
    OptimizationDirection[] directions;
    double[] weights;
    double[][] thresholds;

    public CarsAdapter(String[] attributeNames, OptimizationDirection[] directions, double[] weights, double[][] thresholds) {
        this.attributeNames = attributeNames;
        this.directions = directions;
        this.weights = weights;
        this.thresholds = thresholds;
    }

    @Override
    public Alternative read(String line, int lineNumber) {
        Alternative alternative = new Alternative("car_" + MCDACommons.fillWithZeros(5, lineNumber));
        String[] attrs = line.split(",");
        for (int j = 0; j < attrs.length; j++) {
            String attr = attrs[j];
            CriterionDefinition cdef = createCriterionDefinition(j);
            CriterionValue cv = new CriterionValue(cdef, mapToValue(attr));
            alternative.addCriterion(cv);
        }
        return alternative;
    }

    private CriterionDefinition createCriterionDefinition(int j) {
        if (MCDACommons.isEmpty(attributeNames) ||
                MCDACommons.isEmpty(directions) ||
                MCDACommons.isEmpty(weights) ||
                MCDACommons.isEmpty(thresholds)){
            return CriterionDefinition.EMPTY;
        }

        else return new CriterionDefinition(attributeNames[j], directions[j], weights[j], thresholds[j]);
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

    static class IntegerResponse {

        final boolean isInteger;
        final int value;

        IntegerResponse(boolean isInteger, int value) {
            this.isInteger = isInteger;
            this.value = value;
        }

        static IntegerResponse isInteger(String value) {
            Integer result;
            try {
                result = Integer.parseInt(value);
                return new IntegerResponse(true, result);
            } catch (NumberFormatException valueNotInteger) {
                return new IntegerResponse(false, -1);
            }
        }
    }

}
