package pl.marczak.view;

import MCDA.definitions.Alternative;
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
public class GuiPresenter {

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

    public GuiPresenter(AppCallbacks connector) {
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

    public void onElectreTriChosen(File file, Pair<String, Boolean> separatorAndFirstLineSkip) {
        System.out.println("onElectreTriChosen");
        String separator = separatorAndFirstLineSkip.getKey();
        boolean shouldSkipFirstLine = separatorAndFirstLineSkip.getValue();

        DataReader<Alternative> alternativeDataReader = new DataReader<>(shouldSkipFirstLine, line -> {
            String[] props = line.split(separator);
            Alternative alternative = new Alternative("");
            return alternative;
        });
        List<Alternative> alternatives = alternativeDataReader.read(file);
    }

    public void onVCDRSAChosen(File file, Pair<String, Boolean> separatorAndFirstLineSkip) {
        throw new UnsupportedOperationException("not yet implemented!");
    }
}
