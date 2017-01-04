package pl.marczak.view.electreTri;

import MCDA.methods.outranking.ElectreTri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.marczak.view.ProfileViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public class ElectreResultsDialog {
    ElectreTri.Result result;

    public ElectreResultsDialog(ElectreTri electreTri) {

        this.result = electreTri.solve();
    }

    public void show() {
        Dialog<Boolean> dialog = new Dialog<>();

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        dialog.setTitle("Electre Tri results");


        double[][] table = result.table;
        double[][] rank = result.rank;
        System.out.println("\n" + ElectreTri.doubleArrayToString(rank) + "\n");

        GridPane rankingGridPane = new GridPane();
        rankingGridPane.setHgap(10);
        rankingGridPane.setVgap(10);
        rankingGridPane.setPadding(new Insets(30, 30, 30, 30));

        for (int i = 0; i < rank.length; i++) {
            for (int j = 0; j < rank[i].length; j++) {
                rankingGridPane.add(new Label(String.format("%.3f", rank[i][j])), i, j);
            }
        }

        GridPane tableGridPane = new GridPane();
        tableGridPane.setHgap(10);
        tableGridPane.setVgap(10);
        tableGridPane.setPadding(new Insets(30, 30, 30, 30));

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                tableGridPane.add(new Label(String.format("%.3f", table[i][j])), i, j);
            }
        }

        VBox rankingLayout = new VBox();
        VBox tableLayout = new VBox();
        tableLayout.getChildren().addAll(new Label("TABLE"), tableGridPane);

        rankingLayout.getChildren().addAll(new Label("RANKING"), rankingGridPane);

        HBox rootView = new HBox();
        rootView.getChildren().add(tableLayout);
        rootView.getChildren().add(rankingLayout);

        dialog.getDialogPane().setContent(rootView);

        dialog.setResultConverter(dialogButton -> true);
        Optional<Boolean> _result = dialog.showAndWait();
        _result.ifPresent(any -> dialog.close());
    }

}
