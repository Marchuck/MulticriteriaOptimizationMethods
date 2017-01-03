package pl.marczak.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-03.
 */
public class EditWeightsDialog {

    public interface Callback {
        void onWeightsUpdated(double[] weights);
    }

    String[] propertyNames;
    double[] currentWeights;

    public EditWeightsDialog(String[] propertyNames, double[] currentWeights) {
        this.propertyNames = propertyNames;
        this.currentWeights = adjustWeights(currentWeights);
    }

    private double[] adjustWeights(double[] currentWeights) {
        if (currentWeights == null) {
            this.currentWeights = new double[propertyNames.length];
            for (int i = 0; i < propertyNames.length; i++) {
                this.currentWeights[i] = 1;
            }
        } else this.currentWeights = currentWeights;
        return this.currentWeights;
    }

    public void show(Callback callback) {
        // Create the custom dialog.
        Dialog<double[]> dialog = new Dialog<>();
        dialog.setTitle("Edit weights");
        dialog.setHeaderText("Set weights before algorithm run");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

//        List<Pair<String, Double>> model = new ArrayList<>();
//        for (int i = 0; i < propertyNames.length; i++) {
//            model.add(new Pair<>(propertyNames[i], currentWeights[i]));
//        }
//        ObservableList<Pair<String, Double>> listModel = FXCollections.observableArrayList(model);
//        ListView<Pair<String, Double>> listView = new ListView<>(listModel);
//        listView.setCellFactory(param -> new EditFieldCell());

        TextField[] weightsInput = new TextField[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            weightsInput[i] = new TextField();
            weightsInput[i].setPromptText(propertyNames[i]);
            weightsInput[i].setText(String.valueOf(currentWeights[i]));
            grid.add(new Label(propertyNames[i]), 0, i);
            grid.add(weightsInput[i], 1, i);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(400, 400);
        scrollPane.setContent(grid);

        dialog.getDialogPane().setContent(scrollPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    double[] newWeights = new double[propertyNames.length];
                    for (int i = 0; i < weightsInput.length; i++) {
                        newWeights[i] = Double.parseDouble(weightsInput[i].getText());
                    }
                    return newWeights;
                } catch (Exception x) {
                    System.err.println(String.valueOf(x));
                    return null;
                }
            } else {
                dialog.close();
            }
            return null;
        });

        Optional<double[]> result = dialog.showAndWait();

        result.ifPresent(updatedWeigths -> {
            dialog.close();
            callback.onWeightsUpdated(updatedWeigths);
        });
    }
}
