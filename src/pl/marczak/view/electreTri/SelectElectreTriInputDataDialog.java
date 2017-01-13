package pl.marczak.view.electreTri;

import MCDA.definitions.Alternative;
import MCDA.methods.outranking.ElectreTri;
import MCDA.methods.outranking.ElectreTriAdapter;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 12 sty 2017.
 * 22 : 13
 */
public class SelectElectreTriInputDataDialog {
    final int size;
    final List<Alternative> alternatives;
    List<Alternative> profiles = new ArrayList<>();
    double cutOff = 0.7;

    public SelectElectreTriInputDataDialog(int size, List<Alternative> alternatives) {
        this.size = size;
        this.alternatives = alternatives;
    }

    public void show() {
        Dialog<ElectreTri.Result> dialog = new Dialog<>();

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        dialog.setTitle("Electre Tri parameters");


        HBox rootView = new HBox();

        VBox left = new VBox();
        VBox right = new VBox();

        left.getChildren().add(new Label("Alternatives"));

        GenericListRenderer<Alternative> alternativesProxy = new GenericListRenderer<>(new GenericListRenderer.Callback<Alternative>() {
            @Override
            public Node recycle(Alternative item) {

                Label label = new Label(item.toString());
                return label;
            }
        });
        alternativesProxy.attach(left, alternatives);

        right.getChildren().add(new Label("Parameters"));

        Label labelForQ = new Label("Indiscernibility Thresholds");
        TextField q = new TextField(random(size));
        HBox q_l = new HBox();
        q_l.getChildren().addAll(labelForQ, q);

        Label labelForP = new Label("Preference Thresholds");
        TextField p = new TextField(random(size));
        HBox p_l = new HBox();
        p_l.getChildren().addAll(labelForP, p);

        Label labelForV = new Label("Vetos");
        TextField v = new TextField(random(size));
        HBox v_l = new HBox();
        v_l.getChildren().addAll(labelForV, v);

        Label labelForWeights = new Label("Weights");
        TextField weights = new TextField(random(size));
        HBox w_l = new HBox();
        w_l.getChildren().addAll(labelForWeights, weights);

        right.getChildren().addAll(q_l, p_l, v_l, w_l);



        rootView.getChildren().add(left);
        rootView.getChildren().add(right);

        dialog.getDialogPane().setContent(rootView);

        dialog.setResultConverter(param -> {
            if (param == okButton) {
                return ElectreTriAdapter.adapt(alternatives, profiles, cutOff).solve();
            }
            return null;
        });

        Optional<ElectreTri.Result> _result = dialog.showAndWait();
        _result.ifPresent(any -> dialog.close());
    }

    Random randy = new Random();

    private String random(int size) {
        StringBuilder sb = new StringBuilder();
        sb.append(randy.nextInt(50));
        for (int i = 0; i < size - 1; i++) {
            sb.append(",").append(randy.nextInt(50));
        }
        return sb.toString();
    }
}
