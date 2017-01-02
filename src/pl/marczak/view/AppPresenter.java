package pl.marczak.view;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionDefinition;
import MCDA.definitions.CriterionValue;
import MCDA.definitions.OptimizationDirection;
import MCDA.methods.outranking.DataReader;
import javafx.util.Pair;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

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

    public void onElectreTriChosen(File file, Pair<String, Boolean> separatorAndFirstLineSkip, List<Boolean> optimizationDirections) {
        System.out.println("onElectreTriChosen");
        String separator = separatorAndFirstLineSkip.getKey();
        boolean shouldSkipFirstLine = separatorAndFirstLineSkip.getValue();

        DataReader<Alternative> alternativeDataReader = new DataReader<>(shouldSkipFirstLine);

        List<Alternative> alternatives = alternativeDataReader.read(file, (line, index) -> {

            String[] values = line.split(separator);
            Alternative alternative = new Alternative("S" + index);
            String[] attributeNames = alternativeDataReader.getPropertyNamesByLazy(separator, values.length);
            for (int i = 0; i < values.length; i++) {
                OptimizationDirection direction = optimizationDirections.get(i) ?
                        OptimizationDirection.MAXIMIZE : OptimizationDirection.MINIMIZE;
                CriterionDefinition criterionDefinition = new CriterionDefinition(attributeNames[i], direction, 0, null);
                double criterionDefinitionValue = 0;
                CriterionValue criterionValue = new CriterionValue(criterionDefinition, criterionDefinitionValue);

                alternative.addCriterion(criterionValue);
            }

            return alternative;
        });


    }

    public void onVCDRSAChosen(File file, Pair<String, Boolean> separatorAndFirstLineSkip) {
        throw new UnsupportedOperationException("not yet implemented!");
    }
}
