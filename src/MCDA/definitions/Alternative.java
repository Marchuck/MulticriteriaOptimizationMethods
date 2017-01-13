package MCDA.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class Alternative {
    public String name;

    public int ranking;

    public double value;

    private List<CriterionValue> criteriaValues = new ArrayList<>();

    public Alternative(String name) {
        this.name = name;
    }

    public void addCriterion(CriterionValue value) {
        criteriaValues.add(value);
    }


    public double value() {
        double value = 0;
        for (CriterionValue v : criteriaValues) {
            value += v.value * v.criterion.weight;
        }

        return value;
    }

    public void setCriteriaValues(List<CriterionValue> criteriaValues) {
        this.criteriaValues = criteriaValues;
    }

    public List<CriterionValue> getCriteria() {
        return criteriaValues;
    }

    @Override
    public String toString() {
        return "{ name= " + name + ", criteriaValues= " + MCDACommons.printCollection(criteriaValues) + " }";
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alternative that = (Alternative) o;

        if (ranking != that.ranking) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (!name.equals(that.name)) return false;
        return criteriaValues.equals(that.criteriaValues);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + ranking;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + criteriaValues.hashCode();
        return result;
    }
}
