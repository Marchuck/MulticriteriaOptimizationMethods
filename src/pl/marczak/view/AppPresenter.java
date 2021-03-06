package pl.marczak.view;

import MCDA.definitions.*;
import javafx.util.Pair;
import pl.marczak.adapters.*;
import pl.marczak.view.electreTri.ElectreBundle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Queues
 *
 * @author Lukasz Marczak
 * @since 30 gru 2016.
 * 14 : 11
 */
public class AppPresenter {

    final AppCallbacks appCallbacks;

    Thread spidermanThread;

    double value = 0.0;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            value = (value + 0.01);
            if (value > 1) value -= 1;

            appCallbacks.showProgress(value);
        }
    };
    private String[] firstTwoLines;

    public AppPresenter(AppCallbacks connector) {
        this.appCallbacks = connector;

        //showMockProgressBar();

    }

    private void showMockProgressBar() {
        spidermanThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runnable.run();
            }
        });
        spidermanThread.start();
    }

    public void onFileChosen(File file) {

    }

    public void stop() {
        if (spidermanThread != null) {
            spidermanThread.interrupt();
            spidermanThread = null;
        }
    }

    public void onElectreTriChosen(File file, ElectreBundle electreBundle) {
        System.out.println("onElectreTriChosen");


        String separator = electreBundle.separator;

        boolean shouldSkipFirstLine = electreBundle.firstLineSkip;

        double[] weights = null;

        DataReader<Alternative> alternativeDataReader = new DataReader<>(shouldSkipFirstLine);

        List<Alternative> alternatives = alternativeDataReader.read(file, (line, index) -> {

            System.out.println("parsing line: " + line + " with separator \"" + separator + "\"");

            String[] values = line.split(separator);
            System.out.println("There are " + values.length + " items");
            Alternative alternative = new Alternative("S" + index);
            String[] attributeNames = alternativeDataReader.getPropertyNames(separator, values.length);

            System.out.println("There are " + attributeNames.length + " items");
            if (values.length != attributeNames.length) throw new IllegalStateException("cannot happen!!!");
            for (int i = 0; i < values.length; i++) {
                CriterionDefinition criterionDefinition =
                        new CriterionDefinition(attributeNames[i], null, 1.0d / attributeNames.length, null);

                String doubleValue = values[i].replace(",", ".").trim();
                System.out.println(doubleValue);
                double criterionDefinitionValue = Double.parseDouble(doubleValue);
                CriterionValue criterionValue = new CriterionValue(criterionDefinition, criterionDefinitionValue);
                alternative.addCriterion(criterionValue);
            }
            System.out.println("created new alternative");
            return alternative;
        });

        List<Alternative> profiles = new ArrayList<>();
        int size = alternatives.size();
        Random random = new Random();
        int firstIndex = random.nextInt(size);
        int secondIndex = random.nextInt(size);
        profiles.add(alternatives.get(firstIndex));
        profiles.add(alternatives.get(secondIndex));
        profiles.add(alternatives.get((alternatives.size() / 2)));

//        ElectreTri electreTri = new ElectreTri(alternatives, profiles);
//        electreTri.solve();
//        System.out.println("Result: " + MCDACommons.printCollection(electreTri.getResult()));
    }

    public void onVCDRSAChosen(File file, Pair<String, Boolean> separatorAndFirstLineSkip) {
        throw new UnsupportedOperationException("not yet implemented!");
    }

    public String getFirstFewLines(File file) {
        String filePreview = DataReader.getAFewLinesOnly(file, 5);
        String lines[] = filePreview.split("\n");
        try {
            firstTwoLines = new String[]{lines[0], lines[1]};
        } catch (Exception outOfBoundsOrNPE) {
            System.err.println(String.valueOf(outOfBoundsOrNPE));
        }

        return filePreview;
    }

    public String[] getPropertyNames(boolean skipFirstLine, String separator) {
        if (skipFirstLine) {
            return firstTwoLines[0].split(separator);
        } else {
            int count = firstTwoLines[0].split(separator).length;
            return DataReader.getPropertyNamesByLazyImpl(null, separator, count);
        }

    }

    public Collection<DefaultAlternativeAdapter> getAllConverters(String[] attributeNames,
                                                                  OptimizationDirection[] directions,
                                                                  double[] weights,
                                                                  double[][] thresholds) {
        List<DefaultAlternativeAdapter> converters = new ArrayList<>();
        converters.add(new CarsAdapter(attributeNames, directions, weights, thresholds));
        converters.add(new CreditsAdapter(attributeNames, directions, weights, thresholds));
        converters.add(new CreditsWith_A_Adapter(attributeNames, directions, weights, thresholds));
        converters.add(new InsuranceAdapter(attributeNames, directions, weights, thresholds));
        return converters;
    }

    DefaultAlternativeAdapter match(String name) {
        for (DefaultAlternativeAdapter adapter : getAllConverters(null, null, null, null)) {
            if (adapter.name().equalsIgnoreCase(name)) return adapter;
        }
        return null;
    }

    public void prepare(File currentFile, String currentConverterName) {

        DefaultAlternativeAdapter adapter = match(currentConverterName);
        if (adapter != null) {

            parseData(currentFile, adapter);

        } else {
            appCallbacks.showError("Cannot parse data");
        }

    }

    private void parseData(File currentFile, DefaultAlternativeAdapter readStrategy) {

        DataReader<Alternative> alternativeDataReader = new DataReader<>(false);

        List<Alternative> alternativesParsed = alternativeDataReader.read(currentFile, readStrategy);
        System.out.println("Alternatives parsed: "+alternativesParsed.size());
        appCallbacks.showLoadedData(alternativesParsed,readStrategy);
    }
}
