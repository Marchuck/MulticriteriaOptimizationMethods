package MCDA.methods.outranking;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class ElectreTri {

    final double[][] alternatives;
    final double[][] profiles;
    final double[] q;
    final double[] p;
    final double[] vetos;
    final double[] weights;
    final double threshold;

    private Result result;

    private ElectreTri(double[][] alternatives, double[][] profiles,
                       double[] q, double[] p, double[] vetos, double[] weights, double threshold) {
        this.alternatives = alternatives;
        this.profiles = profiles;
        this.q = q;
        this.p = p;
        this.vetos = vetos;
        this.weights = weights;
        this.threshold = threshold;
    }

    public static class Builder {
        double[][] alternatives;
        double[][] profiles;
        double[] q;
        double[] p;
        double[] vetos;
        double[] weights;
        double threshold;

        public Builder setAlternatives(double[][] alternatives) {
            this.alternatives = alternatives;
            return this;
        }

        public Builder setProfiles(double[][] profiles) {
            this.profiles = profiles;
            return this;
        }

        public Builder setIndiscernibilityThresholdsQ(double[] q) {
            this.q = q;
            return this;
        }

        public Builder setPreferenceThresholds(double[] p) {
            this.p = p;
            return this;
        }

        public Builder setVetos(double[] vetos) {
            this.vetos = vetos;
            return this;
        }

        public Builder setWeights(double[] weights) {
            this.weights = weights;
            return this;
        }

        public Builder setThreshold(double threshold) {
            this.threshold = threshold;
            return this;
        }

        public ElectreTri build() {
            return new ElectreTri(alternatives, profiles, q, p, vetos, weights, threshold);
        }
    }

    public Result solve() {
        if (result == null) result = solve(alternatives, profiles, q, p, vetos, weights, threshold);
        return result;
    }


    public static ElectreTri demo() {

        double[][] alternatives = new double[][]{
                {20322200, 3789900, 3704000, 4333900, 1210900, 3146200, 1602200, 1792300, 4337200, 3668000},
                {-0.78, -0.44, -0.35, -0.56, -0.81, -0.7300, -0.4, -0.75, -0.95, -0.77, -0.44},
                {3, 7, 9, 1, 2, 6, 5, 8, 3, 4, 5},
                {7, 7, 8, 8, 7, 5, 1, 8, 7, 2, 7},
                {2, 5, 3, 10, 4, 7, 3, 7, 8, 6, 10}
        };
        double[][] PP = new double[][]{{120000, 300000}, {-0.72, -0.25}, {3, 7}, {4, 7}, {5, 8},};

        double[] q = new double[]{2, 10, 1.23, 0.5, 1};
        double[] p = new double[]{7, 3, 6, 1, 5};
        double[] v = new double[]{100, 850, 5, 4.5, 8};
        double[] w = new double[]{50, 70, 20, 70, 45};
        double t = 2;
        ElectreTri electreTri = new ElectreTri.Builder()
                .setAlternatives(alternatives)
                .setProfiles(PP)
                .setIndiscernibilityThresholdsQ(q)
                .setPreferenceThresholds(p)
                .setVetos(v)
                .setWeights(w)
                .setThreshold(t)
                .build();
        return electreTri;
    }

    public static void main(String[] args) {
        ElectreTri electreTri = ElectreTri.demo();
        Result result = electreTri.solve();
        System.out.print(result);
    }

    public static boolean debugEnabled = false;

    public Result solve(double[][] alternatives, double[][] profiles, double[] q_indiscernibility_thresholds,
                        double[] preference_thresholds, double[] veto_thresholds, double[] weights, double threshold) {

        if (debugEnabled) {
            System.out.println("Alternatives dimension: " + printDimension(alternatives));
            System.out.println("Profiles dimension: " + printDimension(profiles));
            System.out.println("indiscernibility_thresholds dimension: " + printDimension(q_indiscernibility_thresholds));
            System.out.println("preference_thresholds dimension: " + printDimension(preference_thresholds));
            System.out.println("veto_thresholds dimension: " + printDimension(veto_thresholds));
            System.out.println("weights dimension: " + printDimension(weights));
        }

        int alternativesColumns = getColLength(alternatives);
        int profilesColumns = getColLength(profiles);
        int alternativesRows = getRowLength(alternatives);

        if (debugEnabled) {
            System.out.println("alternativesColumns: " + alternativesColumns);
            System.out.println("profilesColumns: " + profilesColumns);
            System.out.println("alternativesRows: " + alternativesRows);
        }

        double[][][] c = create3DMatrix(alternativesColumns, profilesColumns, alternativesRows);
        double[][][] d = create3DMatrix(alternativesColumns, profilesColumns, alternativesRows);

        fillCDMatrices(c, d, alternativesRows, alternativesColumns, profilesColumns,
                alternatives, profiles, q_indiscernibility_thresholds,
                preference_thresholds, veto_thresholds);

        double[][][] W = create3DMatrix(alternativesColumns, profilesColumns, alternativesRows);

        for (int ind = 0; ind < alternativesRows; ind++)
            for (int i = 0; i < alternativesColumns; i++)
                for (int j = 0; j < profilesColumns; j++)
                    W[i][j][ind] = weights[ind];

        double[][] CW = new double[alternativesColumns][profilesColumns];
        double[][] DW = new double[alternativesColumns][profilesColumns];

        for (int i = 0; i < alternativesColumns; i++)
            for (int j = 0; j < profilesColumns; j++)
                for (int ind = 0; ind < alternativesRows; ind++) {
                    CW[i][j] += c[i][j][ind] * W[i][j][ind] / 100;
                    DW[i][j] += d[i][j][ind] * W[i][j][ind] / 100;
                }

        double[][] CS = createCS(alternativesColumns, alternativesRows, profilesColumns, CW, d);

        double[][] _ranking = createRanking(alternativesColumns, profilesColumns, CS, threshold);
        if (debugEnabled)
            System.out.println("\n\n\n\nRANKING: \n\n\n\n" + doubleArrayToString(_ranking) + "\n\n\n\n\n");

        return new Result(_ranking, CS);
    }

    private double[][] createCS(int alternativesColumns, int alternativesRows, int profilesColumns,
                                double[][] CW, double[][][] D) {

        double[][] CS = new double[alternativesColumns][profilesColumns];

        for (int i = 0; i < alternativesColumns; i++) {
            for (int j = 0; j < profilesColumns; j++) {
                CS[i][j] = CW[i][j];
                for (int k = 0; k < alternativesRows; k++) {
                    if (D[i][j][k] > CW[i][j]) {
                        CS[i][j] *= (1 - D[i][j][k] / (1 - CW[i][j]));
                    }
                }
            }
        }
        return CS;
    }

    private double[][] createRanking(int alternativesColumns, int profilesColumns, double[][] CS, double threshold) {

        double[][] _ranking = new double[alternativesColumns][profilesColumns];

        for (int i = 0; i < alternativesColumns; i++) {
            for (int j = 0; j < profilesColumns; j++) {
                if (debugEnabled) System.out.print("CS[" + i + "][" + j + "] = " + String.format("%.4f", CS[i][j]));
                if (CS[i][j] > threshold) {
                    _ranking[i][j] = 1;
                } else {
                    _ranking[i][j] = 0;
                }
            }
        }
        return _ranking;
    }

    private void fillCDMatrices(double[][][] c, double[][][] d, int aRowLength, int aColLength, int ppColLength,
                                double alternatives[][], double[][] profiles, double[] q,
                                double[] p, double[] v) {

        for (int ind = 0; ind < aRowLength; ind++) {

            double[][] perf = createMatrix(aColLength, ppColLength);

            for (int i = 0; i < aColLength; i++) {
                for (int j = 0; j < ppColLength; j++) {
                    System.out.println("i = "+i+", j = "+j);
                    perf[i][j] = profiles[ind][j] - alternatives[ind][i];

                    if (perf[i][j] < q[ind]) c[i][j][ind] = 1;
                    else if (perf[i][j] > p[ind]) c[i][j][ind] = 0;
                    else c[i][j][ind] = 1 - (q[ind] - perf[i][j]) / (q[ind] - p[ind]);

                    if (perf[i][j] < p[ind]) d[i][j][ind] = 0;
                    else if (perf[i][j] > v[ind]) d[i][j][ind] = 1;
                    else d[i][j][ind] = (perf[i][j] - p[ind]) / (v[ind] - p[ind]);
                }
            }
        }

    }

    private String printDimension(double[] arr) {
        return String.valueOf(arr.length);
    }

    private String printDimension(double[][] alternatives) {
        return "[ " + alternatives.length + " x " + printDimension(alternatives[0]) + "]";
    }

    private double[][][] create3DMatrix(int x, int y, int z) {
        return new double[x][y][z];
    }

    private int getRowLength(double[][] doubles) {
        return doubles.length;
    }


    private double[][] createMatrix(int rows, int columns) {
        return new double[rows][columns];
    }

    private int getColLength(double[][] matrix) {
        return matrix[0].length;
    }

    public static class Result {
        public double[][] rank;
        public double[][] table;

        public Result(double[][] ranking, double[][] cs) {
            rank = ranking;
            table = cs;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "\n\nrank=" + doubleArrayToString(rank) +
                    ",\n\ntable=" + doubleArrayToString(table) +
                    "\n}";
        }
    }

    public static String doubleArrayToString(double[][] a) {
        if (a == null) return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append(" [ ");
        for (int i = 0; ; i++) {
            b.append(elegantDoubleArray(a[i]));
            if (i == iMax)
                return b.append(" ] ").toString();
            b.append(",\n");
        }
    }

    public static String elegantDoubleArray(double[] a) {
        if (a == null) return "null";
        int iMax = a.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(String.format("%.4f", a[i]));
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
}
