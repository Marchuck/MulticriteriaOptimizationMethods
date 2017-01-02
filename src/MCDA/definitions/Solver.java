package MCDA.definitions;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public interface Solver {
    List<Pair<Alternative,Double>> getResult();

    void solve();
}
