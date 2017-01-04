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
        return "{ name= " + name + ",value=" + value + ",ranking=" + ranking + " criteriaValues= " + MCDACommons.printCollection(criteriaValues) + " }";
    }

    public String name(){
        return name;
    }
}
