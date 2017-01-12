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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriterionValue value1 = (CriterionValue) o;

        if (Double.compare(value1.value, value) != 0) return false;
        return criterion.equals(value1.criterion);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = criterion.hashCode();
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
