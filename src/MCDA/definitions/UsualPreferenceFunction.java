package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class UsualPreferenceFunction implements PreferenceFunction {

    @Override
    public double getPreference(double deviation, double... thresholds) {
        return deviation > thresholds[0] ? 1 : 0;
    }
}
