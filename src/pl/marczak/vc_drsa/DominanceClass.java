package pl.marczak.vc_drsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-29.
 */
public class DominanceClass {

    public static final int DOMINATING = 1;
    public static final int DOMINATED = -1;

    private final DRSAObject candidate;

    private List<String> attributeNames = new ArrayList<>();

    private List<DRSAObject> dataset = new ArrayList<>();

    private List<DRSAObject> evaluatedObjects = new ArrayList<>();


    public DominanceClass(DRSAObject candidate) {
        this.candidate = candidate;
    }

    public DominanceClass fromDataset(List<DRSAObject> dataset) {
        this.dataset = dataset;
        return this;
    }


    public DominanceClass ofAttributes(String... attributeNames) {
        return ofAttributes(Arrays.asList(attributeNames));
    }

    public DominanceClass ofAttributes(List<String> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }

    public List<DRSAObject> dominating() {
        evaluatedObjects.clear();
        evaluate(DOMINATING);
        return evaluatedObjects;
    }

    public List<DRSAObject> dominated() {
        evaluatedObjects.clear();
        evaluate(DOMINATED);
        return evaluatedObjects;
    }



    private void evaluate(int compareValue) {

        for (DRSAObject object : dataset) {
            boolean appliesForEveryAttributeFromAllAttributes = true;
            for (String attribute : attributeNames) {
                if (candidate.compare(object, attribute) == compareValue) {
                    appliesForEveryAttributeFromAllAttributes = false;
                }
            }
            if (appliesForEveryAttributeFromAllAttributes) {
                evaluatedObjects.add(object);
            }
        }
    }
}
