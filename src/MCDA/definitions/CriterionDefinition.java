package MCDA.definitions;

import java.util.Arrays;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class CriterionDefinition {
    public static final CriterionDefinition EMPTY = new CriterionDefinition("undefined", OptimizationDirection.MINIMIZE, 0, null);
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
        if (name.equals("undefined")) return name;
        else return "CriterionDefinition{" + "name='" + name + '\'' +
                    ", optimizationDirection=" + optimizationDirection +
                    ", weight=" + weight +
                    ", thresholds=" + Arrays.toString(thresholds) +
                    '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CriterionDefinition that = (CriterionDefinition) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (!name.equals(that.name)) return false;
        if (optimizationDirection != that.optimizationDirection) return false;
        return Arrays.equals(thresholds, that.thresholds);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + optimizationDirection.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(thresholds);
        return result;
    }
}
