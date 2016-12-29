package pl.marczak.vc_drsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-29.
 */
public class RoughApproximation {

    private final Kind kind;
    private final DRSAObject candidate;

    private List<String> attributeNames = new ArrayList<>();

    private List<DRSAObject> dataset = new ArrayList<>();

    private List<DRSAObject> evaluatedObjects = new ArrayList<>();


    public RoughApproximation(Kind kind, DRSAObject candidate) {
        this.kind = kind;
        this.candidate = candidate;
    }

    public RoughApproximation fromDataset(List<DRSAObject> dataset) {
        this.dataset = dataset;
        return this;
    }


    public RoughApproximation ofAttributes(String... attributeNames) {
        return ofAttributes(Arrays.asList(attributeNames));
    }

    public RoughApproximation ofAttributes(List<String> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }

    public List<DRSAObject> evaluate() {

        evaluatedObjects.clear();

        evaluate(kind == Kind.PLUS ? 1 : -1);

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

    public enum Kind {
        PLUS, MINUS
    }


}
