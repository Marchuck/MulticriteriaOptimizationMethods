package pl.marczak.view;

/**
 * Queues
 *
 * @author Lukasz Marczak
 * @since 30 gru 2016.
 * 14 : 15
 */

import MCDA.methods.dominace.StudentsData;
import MCDA.methods.outranking.ElectreTri;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.marczak.vc_drsa.DRSADominanceDialog;
import pl.marczak.view.electreTri.ElectreResultsDialog;

import java.io.File;

public class App extends Application implements AppCallbacks {

    private static File initialDirectoryPath;

    public static void main(String[] args) {
        initialDirectoryPath = new File("C://Users//Admin//Documents//semsetr2//opt_wlkryt//data");
        launch(args);
    }

    AppPresenter presenter;

    ProgressBar progressIndicator;

    Button fileChooseButton;

    Label fileChosenLabel;

    CheckBox firstLineCheckBox;

    Label separatorName;
    Button editWeightsButton;

    TextField separator;

    TextArea filePreview;

    Button runElectreButton;

    Button runVCDRSAButton;

    File currentFile = null;
    double[] currentWeights = null;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("start");

        setupViews(primaryStage);

        presenter = new AppPresenter(this);

        runElectreButton.setOnAction((event) -> {
            if (currentFile == null) {
                showSelectFileFirstDialog();
                return;
            }
            String separatorValue = separator.getText();
            boolean firstLineSkipped = firstLineCheckBox.isSelected();

            new ElectreResultsDialog(ElectreTri.demo()).show();
        });


    }

    private void showSelectFileFirstDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WTF");
        alert.setHeaderText("Cannot calculate");
        alert.setContentText("Select input file first!");
        alert.showAndWait();
    }


    private void setupViews(Stage primaryStage) {

        BorderPane root = new BorderPane();

        VBox box = new VBox();

        addInputFileButton(primaryStage, box);
        addTextPreview(box);
        addProgressBar(box);
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

        editWeightsButton = new Button("edit weights");
        editWeightsButton.setOnAction((x) -> invokeEditWeightsDialog());
        pane.getChildren().add(editWeightsButton);

        runElectreButton = new Button("Run Electre");
        runVCDRSAButton = new Button("Run VCDRSA");
        runVCDRSAButton.setOnAction((x)-> {
            new DRSADominanceDialog(new StudentsData().get());
        });
        pane.getChildren().add(runElectreButton);
        pane.getChildren().add(runVCDRSAButton);

    }

    private void invokeEditWeightsDialog() {
        if (currentFile == null) {
            showError("Open file with data first!");
        } else {
            boolean skipFirstLine = firstLineCheckBox.isSelected();
            String separatorValue = separator.getText();
            String propertyNames[] = presenter.getPropertyNames(skipFirstLine, separatorValue);
            new EditWeightsDialog(propertyNames, currentWeights).show(weights -> {
                currentWeights = weights;
            });
        }
    }

    private void addProgressBar(Pane parent) {
        progressIndicator = new ProgressBar(0);
        progressIndicator.setVisible(false);
        progressIndicator.setMinSize(80, 20);
        progressIndicator.setPrefSize(80, 20);
        parent.getChildren().add(progressIndicator);
    }

    private void addInputFileButton(Stage primaryStage, Pane parent) {
        fileChooseButton = new Button();
        fileChosenLabel = new Label("");
        fileChooseButton.setText("...");
        fileChooseButton.setOnAction(event -> {

            File file = openFile(primaryStage);

            if (file != null) {
                filePreview.setText(presenter.getFirstFewLines(file));
                fileChosenLabel.setText(file.getAbsolutePath());
                currentFile = file;
            }
        });
        HBox hBox = new HBox();
        hBox.getChildren().add(fileChooseButton);
        hBox.getChildren().add(fileChosenLabel);
        parent.getChildren().add(hBox);
    }

    private File openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file;
        try {
            if (initialDirectoryPath != null) fileChooser.setInitialDirectory(initialDirectoryPath);
            file = fileChooser.showOpenDialog(primaryStage);
        } catch (Exception x) {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            file = fileChooser.showOpenDialog(primaryStage);
        }
        return file;
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
        alert.setHeaderText("Error occurred");
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    @Override
    public void showSomethingElse() {
        System.out.println("SOMETHING ELSE");
    }
}
