package MCDA.methods.outranking;

import MCDA.definitions.Alternative;
import MCDA.definitions.MCDAProblem;
import MCDA.definitions.Solver;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class ElectreTri implements Solver {

    List<Pair<Alternative, Double>> solution = new ArrayList<>();

    @Override
    public List<Pair<Alternative, Double>> getResult() {
        return solution;
    }

    @Override
    public void solve() {

    }

    public static void main(String[] args) {
        ElectreTri electreTri = new ElectreTri();

        List<Alternative> alternatives = new ArrayList<>();

        alternatives.add(new Alternative("a1"));
        alternatives.add(new Alternative("a2"));
        alternatives.add(new Alternative("a3"));
        alternatives.add(new Alternative("a4"));
        alternatives.add(new Alternative("a5"));

       // MCDAProblem problem = new MCDAProblem(alternatives,preferenceFunction,criterionDefs);

        electreTri.solve();

        List<Pair<Alternative, Double>> solution = electreTri.getResult();

    }
}
