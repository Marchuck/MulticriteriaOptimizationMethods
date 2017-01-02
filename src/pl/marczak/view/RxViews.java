package pl.marczak.view;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import rx.Observable;

/**
 * Created by Łukasz Marczak on 2017-01-01.
 */
public class RxViews {

    public static Observable<Boolean> checks(CheckBox checkBox) {
        return Observable.create(observableEmitter -> {

            ChangeListener<? super Boolean> listener = (ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (oldValue.booleanValue() != newValue.booleanValue())
                    observableEmitter.onNext(newValue);
            };
            checkBox.selectedProperty().addListener(listener);

//            observableEmitter.addSu(() -> {
//                System.out.println("Cancel");
//                checkBox.selectedProperty().removeListener(listener);
//            });
        });
    }

    public static Observable<Boolean> clicks(Button button) {
        return Observable.create(observableEmitter -> {
            EventHandler<ActionEvent> eventHandler = event -> observableEmitter.onNext(true);
            button.setOnAction(eventHandler);
//            observableEmitter.setCancellable(() -> button.setOnAction(null));
        });
    }

    public static Observable<String> textChanges(TextField textField) {
        return Observable.create(observableEmitter -> {
            ChangeListener<String> listener = (observable, oldValue, newValue) -> {
                if (!oldValue.contentEquals(newValue)) observableEmitter.onNext(newValue);
            };
            textField.textProperty().addListener(listener);
//            observableEmitter.setCancellable(() -> textField.textProperty().removeListener(listener));
        });
    }
}
