package MCDA.definitions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class CriteriaClassBuilder {
    List<CriterionClass> classes = new ArrayList<>();
    List<CriterionDefinition> definitions = new ArrayList<>();

    public CriteriaClassBuilder(List<CriterionClass> classes, List<CriterionDefinition> definitions) {
        this.classes = classes;
        this.definitions = definitions;
    }

    public CriteriaClassBuilder(List<CriterionDefinition> definitions) {
        this(new ArrayList<CriterionClass>(), definitions);
    }

    public void addCriterionClass(double[] values) {
        classes.add(new CriterionClass(values));
    }

    public List<CriterionClass> getClasses() {
        if (classes.size() != definitions.size()) {
            throw new UnsupportedOperationException("You must define class for each Criteria. Classes.Count= " + classes.size()
                    + ", Criteria.Count=" + definitions.size());
        }
        return classes;
    }
}
