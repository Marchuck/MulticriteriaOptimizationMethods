package pl.marczak.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import rx.annotations.Experimental;

import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
@Experimental
public class SemiGenericResultsDialog {

    final ResultsDialogCallbacks callbacks;

    public SemiGenericResultsDialog(ResultsDialogCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void show() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle(callbacks.getTitle());
//        dialog.setHeaderText("Set weights before algorithm run");

        ButtonType loginButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        double[][][] results = callbacks.resultValues();

        TableView[] tables = new TableView[results.length];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = new TableView();

            TableView tableView = tables[i];
            tableView.setPadding(new Insets(10, 150, 10, 10));
            double[][] nextTable = results[i];
//            TableColumn[] columns =
        }


//        dialog.getDialogPane().setContent(scrollPane);

        dialog.setResultConverter(anything -> null);

        Optional<Void> result = dialog.showAndWait();

        result.ifPresent(anyResult -> dialog.close());
    }
}
