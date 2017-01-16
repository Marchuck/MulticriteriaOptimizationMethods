package pl.marczak.adapters;

import MCDA.definitions.*;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 12 sty 2017.
 * 08 : 52
 */
public abstract class DefaultAlternativeAdapter implements DataReader.ReadStrategy<Alternative> {

    public abstract String separator();

    public abstract String alternativePrefix();

    public abstract String name();

    protected String[] attributeNames;
    protected OptimizationDirection[] directions;
    protected double[] weights;
    protected double[][] thresholds;

    public DefaultAlternativeAdapter(String[] attributeNames, OptimizationDirection[] directions, double[] weights, double[][] thresholds) {
        this.attributeNames = attributeNames;
        this.directions = directions;
        this.weights = weights;
        this.thresholds = thresholds;
    }

    @Override
    public Alternative read(String line, int lineNumber) {
        Alternative alternative = new Alternative(alternativePrefix() + MCDACommons.fillWithZeros(5, lineNumber));
        String[] attrs = line.split(separator());
        fillAlternative(alternative, attrs);
        System.out.println("Alternative " + lineNumber);
        return alternative;
    }

    public void fillAlternative(Alternative alternative, String[] attributes) {
        for (int j = 0; j < attributes.length; j++) {
            String attr = attributes[j];
            CriterionDefinition cdef = createCriterionDefinition(j);
            CriterionValue cv = new CriterionValue(cdef, Double.parseDouble(attr));
            alternative.addCriterion(cv);
        }
    }

    protected CriterionDefinition createCriterionDefinition(int j) {
        if (MCDACommons.isEmpty(attributeNames) || MCDACommons.isEmpty(directions) ||
                MCDACommons.isEmpty(weights) || MCDACommons.isEmpty(thresholds)) {
            return CriterionDefinition.EMPTY;
        } else return new CriterionDefinition(attributeNames[j], directions[j], weights[j], thresholds[j]);
    }
}
