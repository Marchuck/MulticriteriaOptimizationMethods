package MCDA.definitions;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public enum OptimizationDirection {
    MINIMIZE, MAXIMIZE;

    @Override
    public String toString() {
        return name();
    }

    public int toInt() {
        return (this == MINIMIZE) ? -1 : 1;
    }
}
