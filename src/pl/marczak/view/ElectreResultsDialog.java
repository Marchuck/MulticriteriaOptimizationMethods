package pl.marczak.view;

import MCDA.methods.outranking.ElectreTri;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public class ElectreResultsDialog {

    public ElectreResultsDialog(Pane veryRoot, ElectreTri electreTri) {
//        Dialog<Void> dialog = new Dialog<>();

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

//        dialog.setTitle("Electre Tri results");
        ElectreTri.Result result = electreTri.solve();
        double[][] table = result.table;
        double[][] rank = result.rank;
        System.out.println("\n" + ElectreTri.doubleArrayToString(rank) + "\n");

        TableView<ProfileViewHolder> tableViewOfRanking = new TableView<>();


        List<ProfileViewHolder> rankingData = wrapToProfilesList(rank);

        rankingData.add(new ProfileViewHolder(Arrays.asList("a", "b")));
        rankingData.add(new ProfileViewHolder(Arrays.asList("c", "d")));
        rankingData.add(new ProfileViewHolder(Arrays.asList("c", "d")));
        rankingData.add(new ProfileViewHolder(Arrays.asList("c", "d")));

        ObservableList<ProfileViewHolder> dataForRanking = FXCollections.observableArrayList(rankingData);


        tableViewOfRanking.setEditable(true);

        TableColumn<ProfileViewHolder, String> firstProfileColumn = new TableColumn<>("Profile 1");
        firstProfileColumn.setMinWidth(100);
        firstProfileColumn.setCellValueFactory(cellData -> cellData.getValue().profile1Property());
        firstProfileColumn.setCellValueFactory(new PropertyValueFactory<ProfileViewHolder, String>("Profile 1"));

        TableColumn<ProfileViewHolder, String> secondProfileColumn = new TableColumn<>("Profile 2");
        secondProfileColumn.setMinWidth(100);
        secondProfileColumn.setCellValueFactory(cellData -> cellData.getValue().profile1Property());
        secondProfileColumn.setCellValueFactory(new PropertyValueFactory<ProfileViewHolder, String>("Profile 2"));


        tableViewOfRanking.setItems(dataForRanking);

        tableViewOfRanking.getColumns().addAll(firstProfileColumn, secondProfileColumn);

        HBox rootView = new HBox();
        rootView.getChildren().add(tableViewOfRanking);
//        rootView.getChildren().add(tableViewOfRanking);

//        dialog.getDialogPane().setContent(rootView);


//        dialog.setResultConverter(dialogButton -> null);
//        Optional<Void> _result = dialog.showAndWait();
//        _result.ifPresent(any -> dialog.close());

        veryRoot.getChildren().add(rootView);
    }

    private List<ProfileViewHolder> wrapToProfilesList(double[][] rank) {
        List<ProfileViewHolder> collection = new ArrayList<>();
        for (double[] aRank : rank) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < rank[0].length; j++) {
                row.add(String.valueOf(aRank[j]));
            }
            collection.add(new ProfileViewHolder(row));
        }
        return collection;
    }
}
