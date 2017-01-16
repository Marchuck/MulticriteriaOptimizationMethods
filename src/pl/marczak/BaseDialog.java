package pl.marczak;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.util.Optional;

/**
 * Created by Łukasz Marczak on 2017-01-04.
 */
public class BaseDialog {
    public Dialog<Boolean> dialog;

    public BaseDialog(String title, String headerText, Node content) {
        dialog = new Dialog<>();

        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton);

        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(anything -> true);
        Optional<Boolean> result = dialog.showAndWait();
        result.ifPresent(X -> dialog.close());
    }
}
