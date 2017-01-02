package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class CriterionDefinition {
    public String name;
    public OptimizationDirection optimizationDirection;
    public double weight;
    public double[] thresholds;

    public CriterionDefinition(String name, OptimizationDirection optimizationDirection, double weight, double[] thresholds) {
        this.name = name;
        this.optimizationDirection = optimizationDirection;
        this.weight = weight;
        this.thresholds = thresholds;
    }

    @Override
    public String toString() {
        return name;
    }
}
