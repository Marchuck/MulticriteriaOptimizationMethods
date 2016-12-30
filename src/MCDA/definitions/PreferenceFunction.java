package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public interface PreferenceFunction {
    PreferenceFunction UNDEFINED = new PreferenceFunction() {
        @Override
        public double getPreference(double deviation, double... thresholds) {
            throw new RuntimeException("Undefined Preference Function!");
        }
    };

    double getPreference(double deviation, double... thresholds);
}
