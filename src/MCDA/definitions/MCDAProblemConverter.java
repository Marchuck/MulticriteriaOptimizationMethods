package MCDA.definitions;

import java.util.ArrayList;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class MCDAProblemConverter extends MCDAProblem {

    public MCDAProblemConverter(MCDAProblem problem) {
        super(new ArrayList<Alternative>(), problem.preferenceFunction, problem.critera);

        for (Alternative original : problem.alternatives) {
            Alternative a = new Alternative(original.name);
            for (int i = 0; i < problem.critera.size(); i++) {

                CriterionDefinition criteriaDefinition = problem.critera.get(i);

                double value = original.getCriteria().get(i).value;

                if (criteriaDefinition.optimizationDirection == OptimizationDirection.MAXIMIZE) {
                    a.addCriterion(new CriterionValue(criteriaDefinition, value));
                } else {
                    a.addCriterion(new CriterionValue(criteriaDefinition, -value));
                }
            }
        }
    }
}
