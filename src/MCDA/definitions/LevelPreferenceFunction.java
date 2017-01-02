package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class LevelPreferenceFunction implements PreferenceFunction {

    @Override
    public double getPreference(double deviation, double... thresholds) {

        double absDev = Math.abs(deviation);
        if (absDev <= thresholds[0]) return 0;
        if (absDev > thresholds[1]) return 1;
        return .5;
    }
}
