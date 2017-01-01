package pl.marczak.electreTri;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 17 gru 2016.
 * 01 : 12
 */
public class Main {
    public static Comparision[][] comparisionTable;
    public static Data data;


    public static void  main(String[] args) {
        data = new Data();


        System.out.print("Mamy " + data.cryterias.keySet().size() + " kryteriów");
        System.out.println(" ," + data.profiles.keySet().size() + " profili");
        System.out.println("Do testów " + data.alternatives.keySet().size() + " danych");

        comparisionTable = new Comparision[data.profiles.keySet().size() + 1][data.alternatives.keySet().size() + 1];

        for (Long alternativeId : data.alternatives.keySet()) {
            for (Long cryteriaId : data.profiles.keySet()) {
                double alt = data.alternatives.get(alternativeId).value;
                double prof = data.profiles.get(cryteriaId).value;
                if (alt > prof) {
                    comparisionTable[cryteriaId.intValue()][alternativeId.intValue()] = Comparision.GREATER;
                } else if (alt < prof) {
                    comparisionTable[cryteriaId.intValue()][alternativeId.intValue()] = Comparision.LESS;
                } else {
                    comparisionTable[cryteriaId.intValue()][alternativeId.intValue()] = Comparision.I;
                }
            }
        }

        showSum();
        calculatePesymi();
        showClasses();
    }

    /**
     *
     */
    private static void showClasses() {
        for (Long alternativeId : data.alternatives.keySet()) {
            System.out.println(data.alternatives.get(alternativeId).clasOptym);
        }
    }

    private static void calculatePesymi() {
        int clas = 0;
        for (Long alternativeId : data.alternatives.keySet()) {
            for (Long cryteriaId : data.profiles.keySet()) {
                if (comparisionTable[cryteriaId.intValue()][alternativeId.intValue()] == Comparision.LESS) {
                    data.alternatives.get(alternativeId).clasOptym = clas;
                    break;
                } else {
                    clas++;
                    data.alternatives.get(alternativeId).clasOptym = clas;
                }
            }
            clas = 0;
        }
    }

    private static void showSum() {
        for (Long alternativeId : data.alternatives.keySet()) {
            for (Long cryteriaId : data.profiles.keySet()) {
                System.out.print(comparisionTable[cryteriaId.intValue()][alternativeId.intValue()].name() + ",");
            }
            System.out.println();
        }

    }
}
