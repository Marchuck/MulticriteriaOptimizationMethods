package pl.marczak.view.electreTri;

import MCDA.methods.outranking.ElectreTri;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.Arrays;
import java.util.Optional;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 16 sty 2017.
 * 21 : 25
 */
public class WrappedElectreTriResult {
    public WrappedElectreTriResult(ElectreTri.Result result) {

        Dialog<Boolean> dialog = new Dialog<>();
        Button clipBoard = new Button("Copy to clipboard");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        clipBoard.setOnAction(c -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            StringBuilder sb = new StringBuilder();
            sb.append("RANK:\n[\n");
            for (double[] s : result.rank) {
                sb.append(Arrays.toString(s)).append(",\n");
            }
            sb.append("]\n\ntable\n[\n");
            for (double[] s : result.table) {
                sb.append(Arrays.toString(s)).append(",\n");
            }
            sb.append("]");

            content.putString(sb.toString());
            clipboard.setContent(content);

            System.out.println("copied to clipboard");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WTF");
            alert.setContentText("Electre Tri Results copied!");
            alert.showAndWait();
        });
        dialog.setTitle("Electre Tri results available on clipboard");
        dialog.getDialogPane().setContent(clipBoard);

        dialog.setResultConverter(dialogButton -> true);
        Optional<Boolean> _result = dialog.showAndWait();
        _result.ifPresent(any -> dialog.close());
    }
}
