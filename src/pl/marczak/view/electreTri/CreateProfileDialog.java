package pl.marczak.view.electreTri;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionDefinition;
import MCDA.definitions.CriterionValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Optional;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 13 sty 2017.
 * 20 : 09
 */
public class CreateProfileDialog {

    final int propertiesSize;
    final String[] propertyNames;

    public CreateProfileDialog(String[] propertyNames) {
        this.propertyNames = propertyNames;
        propertiesSize = propertyNames.length;
    }

    Alternative alternative = new Alternative("");

    public void show(Callback<Alternative, Void> callback) {
        Dialog<Alternative> dialog = new Dialog<>();

        dialog.setTitle("Add new profile");
        ButtonType okButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        VBox rootView = new VBox();

        TextField[] fields = new TextField[propertiesSize];

        HBox nameRow = new HBox();
        Label nameLable = new Label("Profile name");
        TextField fieldName = new TextField();
        nameRow.getChildren().addAll(nameLable, fieldName);
        rootView.getChildren().add(nameRow);

        for (int i = 0; i < propertiesSize; i++) {

            HBox hBox = new HBox();
            Label label = new Label(propertyNames[i]);
            fields[i] = new TextField("1.0");

            hBox.getChildren().addAll(label, fields[i]);
            rootView.getChildren().add(hBox);
        }

        dialog.getDialogPane().setContent(rootView);
        dialog.setResultConverter(anything -> alternative);
        Optional<Alternative> result = dialog.showAndWait();
        result.ifPresent(X -> {
            alternative.name = fieldName.getText();
            for (int i = 0; i < propertiesSize; i++) {
                double value = Double.parseDouble(fields[i].getText());
                CriterionValue cv = new CriterionValue(CriterionDefinition.EMPTY, value);
                alternative.addCriterion(cv);
            }
            if (callback != null) callback.call(alternative);
            dialog.close();
        });
    }
}
