package pl.marczak.adapters;

import MCDA.definitions.Alternative;
import MCDA.definitions.MCDACommons;
import MCDA.definitions.OptimizationDirection;


/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 48
 */
public class CreditsWith_A_Adapter extends DefaultAlternativeAdapter {


    public CreditsWith_A_Adapter(String[] attributeNames, OptimizationDirection[] directions, double[] weights, double[][] thresholds) {
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
    public String name() {
        return "Credits with A";
    }


    @Override
    public Alternative read(String line, int lineNumber) {
        Alternative alternative = new Alternative(alternativePrefix() + MCDACommons.fillWithZeros(5, lineNumber));
        String[] attrs = line.replaceAll("A", "").split(separator());
        fillAlternative(alternative, attrs);
        return alternative;
    }
}
