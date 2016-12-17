package pl.marczak.electreTri;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 17 gru 2016.
 * 01 : 14
 */
public class Cryterium {
    double weight = 0;
    String name = "";
    public Cryterium(double weight, String name) {
        this.weight = weight;
        this.name = name;
    }
}
