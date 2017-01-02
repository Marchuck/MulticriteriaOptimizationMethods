package MCDA.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class MCDAProblem {
    public List<Alternative> alternatives = new ArrayList<>();
    public PreferenceFunction preferenceFunction = PreferenceFunction.UNDEFINED;
    public List<CriterionDefinition> critera = new ArrayList<>();

    public MCDAProblem(List<Alternative> alternatives, PreferenceFunction preferenceFunction, List<CriterionDefinition> critera) {
        this.alternatives = alternatives;
        this.preferenceFunction = preferenceFunction;
        this.critera = critera;
    }

    public String description() {
        StringBuilder desc = new StringBuilder();
        desc.append("MCDA problem with ")
                .append(alternatives.size()).append(" alternatives and ")
                .append(critera.size()).append(" criteria\n");

        for (int i = 0; i < critera.size(); i++) {
            CriterionDefinition criterion = critera.get(i);
            desc.append("Criterion + ").append(i).append(" : ")
                    .append(criterion.name)
                    .append(", direction= ")
                    .append(criterion.optimizationDirection.name())
                    .append(", thresholds= ");
            desc.append(Arrays.toString(criterion.thresholds));
            desc.append("\nAlternatives:\n");
            desc.append(MCDACommons.printCollection(alternatives));
            desc.append("\n");
        }

        return desc.toString();
    }

    @Override
    public String toString() {
        return description();
    }
}
