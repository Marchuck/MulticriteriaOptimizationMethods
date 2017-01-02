package MCDA.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class Alternative {
    public String name;

    public int ranking;

    private List<CriterionValue> criteriaValues = new ArrayList<>();

    public Alternative(String name) {
        this.name = name;
    }

    public void addCriterion(CriterionValue value) {
        criteriaValues.add(value);
    }

    public List<CriterionValue> getCriteria() {
        return criteriaValues;
    }

    @Override
    public String toString() {
        return "{ name= " + name + ", criteriaValues= " + MCDACommons.printCollection(criteriaValues) + " }";
    }
}
