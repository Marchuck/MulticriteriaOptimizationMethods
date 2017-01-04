package pl.marczak.vc_drsa;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionDefinition;
import MCDA.definitions.CriterionValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Łukasz Marczak on 2016-12-29.
 */
public class DominanceAlternative {

    public static final int DOMINATING = 1;
    public static final int DOMINATED = -1;

    private final Alternative candidate;

    private List<String> attributeNames = new ArrayList<>();

    private List<Alternative> dataset = new ArrayList<>();

    private List<Alternative> evaluatedObjects = new ArrayList<>();
    private int compareType;


    public DominanceAlternative(Alternative candidate) {
        if (candidate == null) throw new RuntimeException("Candidate cannot be null!");
        this.candidate = candidate;

    }

    public DominanceAlternative fromDataset(List<Alternative> dataset) {
        this.dataset = dataset;
        return this;
    }


    public DominanceAlternative ofAttributes(String... attributeNames) {
        return ofAttributes(Arrays.asList(attributeNames));
    }

    public DominanceAlternative ofAttributes(List<String> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }

    public DominanceAlternative ofAttributes(Set<String> attributeNames) {
        this.attributeNames.clear();
        this.attributeNames.addAll(attributeNames);
        return this;
    }

    public List<Alternative> dominating() {
        evaluatedObjects.clear();
        evaluate(DOMINATING);
        return evaluatedObjects;
    }

    public List<Alternative> dominated() {
        evaluatedObjects.clear();
        evaluate(DOMINATED);
        return evaluatedObjects;
    }


    public static int compare(Alternative thisObj, Alternative anotherObj, String selectedAttributeName) {

        double otherAttributeValue = find(anotherObj.getCriteria(), selectedAttributeName);
        double thisAttributeValue = find(thisObj.getCriteria(), selectedAttributeName);

        return Double.compare(thisAttributeValue, otherAttributeValue);
    }

    static double find(List<CriterionValue> attrs, String name) {
        for (CriterionValue attribute : attrs) if (attribute.criterion.name.equals(name)) return attribute.value;
        return 0;
    }

    List<Alternative> evaluate(int compareValue) {
        this.compareType = compareValue;
        for (Alternative object : dataset) {
            boolean appliesForEveryAttributeFromAllAttributes = true;
            for (String attribute : attributeNames) {
                if (compare(candidate, object, attribute) == compareValue) {
                    appliesForEveryAttributeFromAllAttributes = false;
                }
            }
            if (appliesForEveryAttributeFromAllAttributes) {
                evaluatedObjects.add(object);
            }
        }
        return evaluatedObjects;
    }

    public String dominanceType() {
        return compareType == -1 ? "DOMINATED BY" : "DOMINATING";
    }
}
