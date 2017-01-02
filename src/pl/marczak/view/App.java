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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import rx.Observable;
import rx.Subscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

import java.io.File;

public class App extends Application implements AppCallbacks {

    public static void main(String[] args) {
        launch(args);
    }

    AppPresenter presenter;

    ProgressBar progressIndicator;

    Button fileChooseButton;

    Label fileChosenLabel;

    CheckBox firstLineCheckBox;

    Label separatorName;
    TextField separator;

    TextArea filePreview;

    Button runElectreButton;

    Button runVCDRSAButton;

    Subject<ElectreBundle, ElectreBundle> lastPropertiesSubject = BehaviorSubject.create();

    Subject<File, File> fileSubject = BehaviorSubject.create();

    @Override
    public void start(Stage primaryStage) {
        System.out.println("start");

        setupViews(primaryStage);

        presenter = new AppPresenter(this);

        lastPropertiesSubject.onNext(new ElectreBundle(",", true, null));

        Observable.combineLatest(
                RxViews.textChanges(separator), RxViews.checks(firstLineCheckBox), (lastSeparator, firstLineSkipped) -> {

                    double[] weights = null;
                    lastPropertiesSubject.onNext(new ElectreBundle(lastSeparator, firstLineSkipped, weights));
                    return false;
                }).subscribe();

        Observable.zip(fileSubject,
                lastPropertiesSubject,
                RxViews.clicks(runElectreButton),
                (file, bundle, clicked) -> {
                    presenter.onElectreTriChosen(file, bundle);
                    return true;
                }).subscribe();

    }


    private void setupViews(Stage primaryStage) {

        BorderPane root = new BorderPane();

        VBox box = new VBox();

        addInputFileButton(primaryStage, box);
        addTextPreview(box);
        addProgressBar(primaryStage, box);
        addSeparatorAndRunButton(box);
        root.setCenter(box);

        primaryStage.setTitle("Multicriteria optimization methods");

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private void addTextPreview(VBox box) {
        filePreview = new TextArea("");
        filePreview.setPrefWidth(300);
        filePreview.setPrefWidth(200);
        box.getChildren().add(filePreview);
    }

    private void addSeparatorAndRunButton(Pane pane) {

        firstLineCheckBox = new CheckBox("skip first line");

        pane.getChildren().add(firstLineCheckBox);

        separatorName = new Label("separator");
        separator = new TextField(",");

        HBox hbox = new HBox();
        hbox.getChildren().add(separatorName);
        hbox.getChildren().add(separator);

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
        fileChooseButton.setText("Open file");
        fileChooseButton.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open input data");
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                filePreview.setText(presenter.getFirstFewLines(file));
                fileChosenLabel.setText(file.getAbsolutePath());
                fileSubject.onNext(file);
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(fileChooseButton);
        hBox.getChildren().add(fileChosenLabel);
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
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("WTF");
//        alert.setHeaderText("Error occurred");
//        alert.setContentText(errorMsg);
//        alert.showAndWait();
    }

    @Override
    public void showSomethingElse() {
        System.out.println("SOMETHING ELSE");
    }
}
