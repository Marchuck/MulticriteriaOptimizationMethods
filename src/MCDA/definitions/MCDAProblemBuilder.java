package MCDA.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class MCDAProblemBuilder {
    List<Alternative> alternatives = new ArrayList<>();
    List<CriterionDefinition> criterionDefinitions = new ArrayList<>();
    PreferenceFunction preferenceFunction = PreferenceFunction.UNDEFINED;

    public MCDAProblemBuilder() {
    }

    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public List<CriterionDefinition> getCriterionDefinitions() {
        return criterionDefinitions;
    }

    public void setCriterionDefinitions(List<CriterionDefinition> criterionDefinitions) {
        this.criterionDefinitions = criterionDefinitions;
    }

    public PreferenceFunction getPreferenceFunction() {
        return preferenceFunction;
    }

    public void setPreferenceFunction(PreferenceFunction preferenceFunction) {
        this.preferenceFunction = preferenceFunction;
    }

    public void addAlternative(Alternative alt, double... criteriaValues) {

        if (criteriaValues.length != criterionDefinitions.size())
            throw new IllegalArgumentException("Size of criteria values does not match with criteria definitions");

        for (int j = 0; j < criterionDefinitions.size(); j++) {

            CriterionDefinition def = criterionDefinitions.get(j);
            CriterionValue value = new CriterionValue(def, criteriaValues[j]);
            alt.addCriterion(value);
        }

        alternatives.add(alt);
    }

    public void resetAlternatives() {
        alternatives.clear();
    }

    public void reset() {
        alternatives.clear();
        criterionDefinitions.clear();
        preferenceFunction = PreferenceFunction.UNDEFINED;
    }

    public MCDAProblem createMCDAProblem() {
        MCDAProblem problem = new MCDAProblem(alternatives, preferenceFunction, criterionDefinitions);
        return problem;
    }
}
