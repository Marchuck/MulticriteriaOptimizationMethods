package pl.marczak.vc_drsa;

import MCDA.definitions.Alternative;
import MCDA.definitions.CriterionValue;
import MCDA.definitions.MCDACommons;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.marczak.BaseDialog;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Łukasz Marczak on 2017-01-04.
 */
public class DRSADominanceDialog {

    static class Pair {
        Integer key;
        Boolean value;

        public Pair(Integer key, Boolean value) {
            this.key = key;
            this.value = value;
        }
    }

    String currentStudent = null;

    volatile Set<String> currentAttributesInUse = new HashSet<>();

    int currentDominanceType;

    public DRSADominanceDialog(List<Alternative> alternativeList) {

        VBox content = new VBox();
        GridPane studentsPane = new GridPane();

        List<String> attributeNames = getAttributeNames(alternativeList);
        List<String> studentNames = getStudentsNames(alternativeList);

        fillStudentsPane(studentsPane, alternativeList, attributeNames, studentNames);

        content.getChildren().add(studentsPane);

        ChoiceBox<String> studentsChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(studentNames));
        studentsChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> currentStudent = studentNames.get(newValue.intValue()));

//        ChoiceBox<String> attributesChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(attributeNames));
//        attributesChoiceBox.getSelectionModel().selectedIndexProperty()
//                .addListener((observable, oldValue, newValue) -> currentAttribute = attributeNames.get(newValue.intValue()));

        VBox attributesCheckBoxesLayout = new VBox();
        CheckBox[] boxes = new CheckBox[attributeNames.size()];
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new CheckBox(attributeNames.get(i));
        }
        Subject<Pair, Pair> checkBoxSelectedSubject = BehaviorSubject.create();
        for (int i = 0; i < boxes.length; i++) {
            final int index = i;
            Observable.create(subscriber -> {
                checkBoxSelectedSubject.onNext(new Pair(index, boxes[index].selectedProperty().get()));
                boxes[index].selectedProperty().addListener((o, o1, n) -> {
                    checkBoxSelectedSubject.onNext(new Pair(index, n));
                });
            }).subscribe();
        }
        checkBoxSelectedSubject.subscribe(new Subscriber<Pair>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Pair pair) {
                if (pair.value) {
                    currentAttributesInUse.add(attributeNames.get(pair.key));
                } else currentAttributesInUse.remove(attributeNames.get(pair.key));
            }
        });

        attributesCheckBoxesLayout.getChildren().addAll(boxes);

        ChoiceBox<String> dominanceChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("DOMINATED", "DOMINATING"));
        dominanceChoiceBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> currentDominanceType = newValue.intValue() == 0 ? -1 : 1);

        HBox choiceBoxes = new HBox();

        choiceBoxes.getChildren().add(attributesCheckBoxesLayout);
        choiceBoxes.getChildren().add(studentsChoiceBox);
        choiceBoxes.getChildren().add(dominanceChoiceBox);

        Button btn = new Button("Evaluate");
        btn.setOnAction((c) -> {
            DominanceAlternative dom = new DominanceAlternative(findCandidateByName(alternativeList, currentStudent));
            List<Alternative> alternativesDom = dom.fromDataset(alternativeList)
                    .ofAttributes(currentAttributesInUse)
                    .evaluate(currentDominanceType);
            VBox pane = new VBox();
            pane.getChildren().addAll(new Label(currentStudent+" " + dom.dominanceType()));
            pane.getChildren().addAll(new Label(MCDACommons.printCollectionWith(alternativesDom, alternative -> alternative.name)));

            BaseDialog resultDialog = new BaseDialog("Results", "", pane);

        });

        content.getChildren().addAll(choiceBoxes, btn);

        new BaseDialog("DRSA", "Dominance Students Example", content);
    }

    private Alternative findCandidateByName(List<Alternative> alternativeList, String currentStudent) {
        for (Alternative a : alternativeList) {
            if (a.name.equalsIgnoreCase(currentStudent)) return a;
        }
        return null;
    }

    private Integer attributeIndex(List<String> attrs, String text) {
        for (int i = 0; i < attrs.size(); i++) {
            if (text.equalsIgnoreCase(attrs.get(i))) return i;
        }
        return 0;
    }

    private void fillStudentsPane(GridPane studentsPane, List<Alternative> alternatives, List<String> attributeNames, List<String> names) {
        for (int i = 0; i < alternatives.size(); i++) {
            Alternative alternative = alternatives.get(i);
            List<CriterionValue> values = alternative.getCriteria();
            for (int j = 0; j < values.size(); j++) {
                if (i == 0 && j == 0) continue;
                else if (i == 0) {
                    studentsPane.add(new Label(attributeNames.get(j - 1)), 0, j);
                } else if (j == 0) {
                    studentsPane.add(new Label(names.get(i - 1)), i, 0);
                } else {
                    studentsPane.add(new Label(gradeValue(values.get(j).value)), i, j);
                }
            }
        }
    }

    private String gradeValue(double value) {
        if (value == 0) return "BAD";
        else if (value == 1) return "MEDIUM";
        else if (value == 2) return "GOOD";
        else return String.valueOf(value);
    }

    private List<String> getStudentsNames(List<Alternative> alternativeList) {
        List<String> names = new ArrayList<>();
        for (Alternative a : alternativeList) {
            names.add(a.name);
        }
        return names;
    }

    private List<String> getAttributeNames(List<Alternative> alternativeList) {
        Alternative a = alternativeList.get(0);
        List<CriterionValue> b = a.getCriteria();
        List<String> attributeNames = new ArrayList<>();
        for (CriterionValue value : b) {
            attributeNames.add(value.criterion.name);
        }
        return attributeNames;
    }
}
