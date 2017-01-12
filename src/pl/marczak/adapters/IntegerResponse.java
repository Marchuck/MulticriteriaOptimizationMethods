package pl.marczak.adapters;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 12 sty 2017.
 * 08 : 38
 */
public class IntegerResponse {
    public final boolean isInteger;
    public final int value;

    IntegerResponse(boolean isInteger, int value) {
        this.isInteger = isInteger;
        this.value = value;
    }

    public static IntegerResponse isInteger(String value) {
        Integer result;
        try {
            result = Integer.parseInt(value);
            return new IntegerResponse(true, result);
        } catch (NumberFormatException valueNotInteger) {
            return new IntegerResponse(false, -1);
        }
    }
}
