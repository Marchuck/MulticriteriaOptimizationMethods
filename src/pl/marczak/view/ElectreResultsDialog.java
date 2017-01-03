package pl.marczak.view;

import MCDA.methods.outranking.ElectreTri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public class ElectreResultsDialog {

    public ElectreResultsDialog(ElectreTri electreTri) {
        Dialog<Void> dialog = new Dialog<>();

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dialog.setTitle("Electre Tri results");
        ElectreTri.Result result = electreTri.solve();
        double[][] table = result.table;
        double[][] rank = result.rank;

        TableView<String[]> tableViewOfRanking = new TableView<>();

        Collection<String[]> rankingData = wrapToStringArray(rank);
        ObservableList<String[]> dataForRanking = FXCollections.observableArrayList(rankingData);

        tableViewOfRanking.setEditable(true);
        TableColumn firstProfile = new TableColumn("Profile 1");
        firstProfile.setCellValueFactory(new PropertyValueFactory<double[], String>("Profile 1"));
        TableColumn secondProfile = new TableColumn("Profile 2");
        secondProfile.setCellValueFactory(new PropertyValueFactory<double[], String>("Profile 2"));

        tableViewOfRanking.setItems(dataForRanking);

        tableViewOfRanking.getColumns().addAll(firstProfile, secondProfile);

        VBox rootView = new VBox();
        rootView.getChildren().add(tableViewOfRanking);

        dialog.getDialogPane().setContent(rootView);

        dialog.setResultConverter(dialogButton -> null);
        Optional<Void> _result = dialog.showAndWait();
        _result.ifPresent(any -> dialog.close());

    }

    private Collection<String[]> wrapToStringArray(double[][] rank) {
        List<String[]> collection = new ArrayList<>();
        for (int i = 0; i < rank.length; i++) {
            String[] row = new String[rank[0].length];
            for (int j = 0; j < rank[0].length; j++) {
                row[j] = String.valueOf(rank[i][j]);
            }
            collection.add(row);
        }
        return collection;
    }
}
