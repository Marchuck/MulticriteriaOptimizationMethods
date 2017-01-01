package pl.marczak.view;

/**
 * Queues
 *
 * @author Lukasz Marczak
 * @since 30 gru 2016.
 * 14 : 15
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import java.io.File;

public class App extends Application implements AppCallbacks {

    public static void main(String[] args) {
        launch(args);
    }

    GuiPresenter presenter;

    ProgressBar progressIndicator;

    Button fileChooseButton;

    Label fileChosenLabel;

    CheckBox firstLineCheckBox;

    Label separatorName;
    TextField separator;

    Button runElectreButton;

    Button runVCDRSAButton;

    Subject<Pair<String, Boolean>, Pair<String, Boolean>> lastPropertiesSubject = BehaviorSubject.create();
    Subject<File, File> fileSubject = BehaviorSubject.create();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("start");

        setupViews(primaryStage);

        presenter = new GuiPresenter(this);

        lastPropertiesSubject.onNext(new Pair<>(",", true));

        Observable.combineLatest(
                RxViews.textChanges(separator), RxViews.checks(firstLineCheckBox), (lastSeparator, firstLineSkipped) -> {

                    Pair<String, Boolean> pair = new Pair<>(lastSeparator, firstLineSkipped);
                    lastPropertiesSubject.onNext(pair);
                    return false;
                }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });

        Observable.zip(fileSubject,
                lastPropertiesSubject,
                RxViews.clicks(runElectreButton),
                (file, separatorAndFirstLineSkip, clicked) -> {
                    presenter.onElectreTriChosen(file, separatorAndFirstLineSkip);
                    return true;
                }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });

    }


    private void setupViews(Stage primaryStage) {

        BorderPane root = new BorderPane();

        VBox box = new VBox();

        addInputFileButton(primaryStage, box);
        addProgressBar(primaryStage, box);
        addSeparatorAndRunButton(box);
        root.setCenter(box);

        primaryStage.setTitle("Multicriteria optimization methods");

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private void addSeparatorAndRunButton(Pane pane) {

        firstLineCheckBox = new CheckBox("skip first line");

        pane.getChildren().add(firstLineCheckBox);

        separatorName = new Label("separator");
        separator = new TextField(",");

        HBox hbox = new HBox(separatorName, separator);

        pane.getChildren().add(hbox);

        runElectreButton = new Button("Run Electre");
        runVCDRSAButton = new Button("Run DRSA");
        pane.getChildren().add(runElectreButton);
        pane.getChildren().add(runVCDRSAButton);

    }

    private void addProgressBar(Stage primaryStage, Pane parent) {
        progressIndicator = new ProgressBar(0);
        progressIndicator.setVisible(false);
        progressIndicator.setMinSize(80, 20);
        progressIndicator.setPrefSize(80, 20);
        parent.getChildren().add(progressIndicator);
    }

    private void addInputFileButton(Stage primaryStage, Pane parent) {
        fileChooseButton = new Button();
        fileChosenLabel = new Label("");
        fileChooseButton.setText("Open input file");
        fileChooseButton.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open input data");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                fileChosenLabel.setText(file.getAbsolutePath());
                fileSubject.onNext(file);
            }
        });
        HBox hBox = new HBox(fileChooseButton, fileChosenLabel);
        parent.getChildren().add(hBox);
    }

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("init");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (presenter != null) {
            presenter.stop();
            presenter = null;
        }
        System.out.println("stop!");
    }

    @Override
    public void showResults(Results results) {
        System.out.println("RESULTS : " + String.valueOf(results));
    }

    @Override
    public void showProgress(double value) {
        if (progressIndicator == null) return;

        progressIndicator.setVisible(true);
        progressIndicator.setProgress(value);
    }

    @Override
    public void hideProgress() {
        progressIndicator.setVisible(false);
    }

    @Override
    public void showError(String errorMsg) {
        System.out.println("Error occurred");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("WTF");
//        alert.setHeaderText("Error occurred");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    @Override
    public void showSomethingElse() {
        System.out.println("SOMETHING ELSE");
    }
}
