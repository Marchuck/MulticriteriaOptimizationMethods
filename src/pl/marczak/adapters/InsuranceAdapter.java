package pl.marczak.adapters;

import MCDA.definitions.OptimizationDirection;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 48
 */
public class InsuranceAdapter extends DefaultAlternativeAdapter {

    public InsuranceAdapter(String[] attributeNames, OptimizationDirection[] directions,
                            double[] weights, double[][] thresholds) {
        super(attributeNames, directions, weights, thresholds);
    }

    public InsuranceAdapter() {
        super(null, null, null, null);
    }

    @Override
    public String separator() {
        return "\\s++";
    }

    @Override
    public String alternativePrefix() {
        return "ins_";
    }

    @Override
    public String name() {
        return "Insurance";
    }

}
