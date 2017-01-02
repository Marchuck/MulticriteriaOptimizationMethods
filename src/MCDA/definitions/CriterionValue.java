package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class CriterionValue {
    public CriterionDefinition criterion;

    public double value;

    public CriterionValue(CriterionDefinition criterion, double value) {
        this.criterion = criterion;
        this.value = value;
    }

    public double getPreference(CriterionValue secondCriterion, PreferenceFunction preferenceFunction) {
        return preferenceFunction.getPreference(
                criterion.optimizationDirection.toInt() * (value - secondCriterion.value), criterion.thresholds);
    }

}
