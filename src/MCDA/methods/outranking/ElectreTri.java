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

    private void solver() {


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

    public Result solve(double[][] A, double[][] PP, double[] q, double[] p, double[] v, double[] w, double t) {
        Result result = PrepareResult(A, PP);
        int aColNumber = getColLength(A);
        int ppColNumber = getColLength(PP);
        int aRowNumber = getRowLength(A);

        double[][][] c = create3DMatrix(aColNumber, ppColNumber, aRowNumber);
        double[][][] d = create3DMatrix(aColNumber, ppColNumber, aRowNumber);

        for (int ind = 0; ind < aRowNumber; ind++) {
            double[][] performanceTable = createMatrix(aRowNumber, ppColNumber);

            for (int i = 0; i < aColNumber; i++) {
                for (int j = 0; j < ppColNumber; j++) {
                    performanceTable[i][j] = PP[ind][j] - A[ind][i];

                    if (performanceTable[i][j] < q[ind])
                        c[i][j][ind] = 1;
                    else if (performanceTable[i][j] > p[ind])
                        c[i][j][ind] = 0;
                    else
                        c[i][j][ind] = 1 - (q[ind] - performanceTable[i][j]) / (q[ind] - p[ind]);

                    if (performanceTable[i][j] < p[ind])
                        d[i][j][ind] = 0;
                    else if (performanceTable[i][j] > v[ind])
                        d[i][j][ind] = 1;
                    else
                        d[i][j][ind] = (performanceTable[i][j] - p[ind]) / (v[ind] - p[ind]);
                }
            }
        }

        double[][][] W = create3DMatrix(aColNumber, ppColNumber, aRowNumber);
        for (int ind = 0; ind < aRowNumber; ind++)
            for (int i = 0; i < aColNumber; i++)
                for (int j = 0; j < ppColNumber; j++)
                    W[i][j][ind] = w[ind];


        double[][] CW = createMatrix(aColNumber, ppColNumber);
        double[][] DW = createMatrix(aColNumber, ppColNumber);

        for (int i = 0; i < aColNumber; i++)
            for (int j = 0; j < ppColNumber; j++)
                for (int ind = 0; ind < aRowNumber; ind++) {
                    CW[i][j] += c[i][j][ind] * W[i][j][ind] / 100;
                    DW[i][j] += d[i][j][ind] * W[i][j][ind] / 100;
                }


        double[][] CS = createMatrix(aColNumber, ppColNumber);
        for (int i = 0; i < aColNumber; i++) {
            for (int j = 0; j < ppColNumber; j++) {
                CS[i][j] = CW[i][j];
                for (int ind = 0; ind < aRowNumber; ind++) {
                    if (d[i][j][ind] > CW[i][j]) {
                        CS[i][j] = CS[i][j] * (1 - d[i][j][ind] / (1 - CW[i][j]));
                    }
                }
            }
        }

        double[][] prof = createMatrix(aColNumber, ppColNumber);
        for (int i = 0; i < aColNumber; i++) {
            for (int j = 0; j < ppColNumber; j++) {
                if (CS[i][j] > t)
                    prof[i][j] = 1;
                else
                    prof[i][j] = 0;
            }
        }

        result.Rank = prof;
        result.Table = CS;

        return result;
    }

    private double[][][] create3DMatrix(int x, int y, int z) {
        double[][][] result = new double[x][][];
        for (int i = 0; i < x; i++)
            result[i] = createMatrix(y, z);

        return result;
    }

    private int getRowLength(double[][] doubles) {
        return doubles.length;
    }

    private Result PrepareResult(double[][] A, double[][] pp) {
        Result result = new Result();
        result.Rank = createMatrix(getColLength(A), getColLength(pp));
        result.Table = createMatrix(getColLength(A), getColLength(pp));
        return result;
    }

    private double[][] createMatrix(int rows, int columns) {
        double[][] result = new double[rows][];

        for (int i = 0; i < rows; i++)
            result[i] = new double[columns];

        return result;
    }

    private int getColLength(double[][] matrix) {
        return matrix[0].length;
    }

    static class Result {
        public double[][] Rank;
        public double[][] Table;
    }
}
