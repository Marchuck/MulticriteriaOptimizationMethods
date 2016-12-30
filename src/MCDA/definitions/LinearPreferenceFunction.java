package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class LinearPreferenceFunction implements PreferenceFunction {

    @Override
    public double getPreference(double deviation, double... thresholds) {

        if (deviation >= thresholds[1]) return 1;
        if (deviation <= thresholds[1]) return 0;

        return (deviation - thresholds[0]) / (thresholds[1] - thresholds[0]);
    }
}
