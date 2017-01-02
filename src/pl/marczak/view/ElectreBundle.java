package pl.marczak.view;

import java.util.Arrays;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 02 sty 2017.
 * 14 : 50
 */
public class ElectreBundle {
    public String separator;
    public boolean firstLineSkip;
    public double[] weights;

    public ElectreBundle(String separator, boolean firstLineSkip, double[] weights) {
        this.separator = separator;
        this.firstLineSkip = firstLineSkip;
        this.weights = weights;
        System.out.print(this);
    }

    @Override
    public String toString() {
        return "ElectreBundle{" +
                "separator='" + separator + '\'' +
                ", firstLineSkip=" + firstLineSkip +
                ", weights=" + Arrays.toString(weights) +
                '}';
    }
}
