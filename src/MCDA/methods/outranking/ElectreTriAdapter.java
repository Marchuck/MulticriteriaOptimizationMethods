package MCDA.methods.outranking;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionValue;

import java.util.List;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 08
 */
public class ElectreTriAdapter {


    /**
     * @param alternatives first element should contain needed parameters:
     * @param profiles
     * @param cut_off      threshold which decides if value should be grouped or not
     * @return
     */
    public static ElectreTri adapt(List<Alternative> alternatives, List<Alternative> profiles, double cut_off) {
        System.out.println("received " + alternatives.size() + " alternatives, " + profiles.size() + " profiles, cut off is " + cut_off);
        Alternative al = new Alternative("id" + String.valueOf(0));
        //alternatives.add(al);

        double[][] _alternatives = getAlternatives(alternatives);
        double[][] _profiles = getAlternatives(profiles);

        Alternative first = alternatives.get(0);
        List<CriterionValue> cv = first.getCriteria();

        double[] _indiscernibilityThresholds = adaptThreshold(cv, 0);
        double[] _preferenceThresholds = adaptThreshold(cv, 1);
        double[] _vetos = adaptThreshold(cv, 2);
        double[] _weights = adaptWeights(cv);

        ElectreTri electreTri = new ElectreTri.Builder()
                .setAlternatives(_alternatives)
                .setProfiles(_profiles)
                .setIndiscernibilityThresholdsQ(_indiscernibilityThresholds)
                .setPreferenceThresholds(_preferenceThresholds)
                .setVetos(_vetos)
                .setWeights(_weights)
                .setThreshold(cut_off)
                .build();

        return electreTri;
    }

    private static double[] adaptThreshold(List<CriterionValue> criterionValues, int index) {

        double thresholds[] = new double[criterionValues.size()];

        for (int i = 0; i < criterionValues.size(); i++) {
            thresholds[i] = criterionValues.get(i).criterion.thresholds[index];
        }

        return thresholds;
    }

    static double[] adaptWeights(List<CriterionValue> criterionValues) {


        double weights[] = new double[criterionValues.size()];

        for (int i = 0; i < criterionValues.size(); i++) {
            weights[i] = criterionValues.get(i).criterion.weight;
        }

        return weights;
    }

    private static double[][] getAlternatives(List<Alternative> a) {

        int cols = a.get(0).getCriteria().size();
        int rows = a.size();

        double[][] adaptedAlternatives = new double[rows][cols];
        System.out.println("rows: " + rows + ", cols: " + cols);
        for (int i = 0; i < rows; i++) {
            int size = a.get(i).getCriteria().size();
            // System.out.println(i + "_cols: " + size);

            for (int j = 0; j < size; j++) {
                adaptedAlternatives[i][j] = a.get(i).getCriteria().get(j).value;
            }
        }

        return transposeMatrix(adaptedAlternatives);
    }

    public static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

}
