package MCDA.methods.outranking;

import MCDA.definitions.Alternative;
import MCDA.definitions.MCDACommons;
import MCDA.definitions.Solver;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class ElectreTri implements Solver {

    List<Pair<Alternative, Double>> solution = new ArrayList<>();

    List<Alternative> alternatives, profiles;

    public static final int PESYMISTIC = -1;

    public static final int OPTYMISTIC = 1;

    public ElectreTri(List<Alternative> alternatives, List<Alternative> profiles) {
        this.alternatives = alternatives;
        this.profiles = profiles;
    }

    @Override
    public List<Pair<Alternative, Double>> getResult() {
        return solution;
    }

    @Override
    public void solve() {
        if (!MCDACommons.anyEmpty(alternatives, profiles)) solveImpl();
        else System.out.println("ERROR OCCURRED: EMPTY INPUT DATA");
    }

    private void solveImpl() {

        int[][] comparisonTable = fillComparisonTable();

        calculate(comparisonTable, PESYMISTIC);
        System.out.println("\nPESYMISTIC\n");
        System.out.print(MCDACommons.printCollection(alternatives));

        calculate(comparisonTable, OPTYMISTIC);
        System.out.println("\nOPTYMISTIC\n");
        System.out.print(MCDACommons.printCollection(alternatives));

        solution.addAll(alternatives.stream().map(a -> new Pair<>(a, a.value)).collect(Collectors.toList()));
    }

    private void calculate(int[][] comparisonTable, int comparisonType) {
        int clas = 0;
        for (int i = 0; i < comparisonTable.length; i++) {
            for (int j = 0; j < comparisonTable[0].length; j++) {
                if (comparisonTable[i][j] == comparisonType) {
                    alternatives.get(i).ranking = clas;
                    break;
                } else {
                    clas += (-1 * comparisonType);
                    alternatives.get(i).ranking = clas;
                }
            }
            clas = 0;
        }
    }

    private int[][] fillComparisonTable() {

        int[][] comparisonTable = new int[alternatives.size()][profiles.size()];

        for (int i = 0; i < alternatives.size(); i++) {
            Alternative alternative = alternatives.get(i);
            double alternativeValue = alternative.value();

            for (int j = 0; j < profiles.size(); j++) {
                Alternative profile = profiles.get(i);
                double profileValue = profile.value();

                comparisonTable[i][j] = Double.compare(alternativeValue, profileValue);
            }
        }
        return comparisonTable;
    }

    public static void main(String[] args) {

    }
}
