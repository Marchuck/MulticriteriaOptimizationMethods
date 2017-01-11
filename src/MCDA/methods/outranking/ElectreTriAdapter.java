package MCDA.methods.outranking;

import MCDA.definitions.Alternative;

import java.util.List;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 11 sty 2017.
 * 22 : 08
 */
public class ElectreTriAdapter {

    public static ElectreTri adapt(List<Alternative> alternatives) {

        Alternative al = new Alternative("id" + String.valueOf(0));
        alternatives.add(al);

        double[][] _alternatives = adaptAlternatives(alternatives);
        double[][] _profiles = adaptAlternatives();
        double[] _indiscernibilityThresholds = adaptAlternatives();
        double[] _preferenceThresholds = adaptAlternatives();
        double[] _vetos = adaptAlternatives();
        double[] _weights = adaptAlternatives();
        double _threshold = adaptAlternatives();

        ElectreTri electreTri = new ElectreTri.Builder()
                .setAlternatives(_alternatives)
                .setProfiles(_profiles)
                .setIndiscernibilityThresholdsQ(_indiscernibilityThresholds)
                .setPreferenceThresholds(_preferenceThresholds)
                .setVetos(_vetos)
                .setWeights(_weights)
                .setThreshold(_threshold)
                .build();
        return electreTri;
    }

    private static double[][] adaptAlternatives(List<Alternative> alternatives) {
        //todo:
        return new double[0][];
    }

    private static <T> T adaptAlternatives() {
        //todo:
        return null;
    }
}
