package pl.marczak.view;

import java.util.List;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public interface ResultsDialogCallbacks {
    String getTitle();
    String[] subtitles();
    double[][][] resultValues();
}
