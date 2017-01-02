package pl.marczak.view;

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
    }
}
