package pl.marczak.view;


import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by Łukasz Marczak on 2017-01-01.
 */
public class RxViews {

    public static Observable<Boolean> checks(CheckBox checkBox) {
        return Observable.create(observableEmitter -> {

            ChangeListener<? super Boolean> listener = (ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (oldValue.booleanValue() != newValue.booleanValue()) {
                    System.out.println("onNext -> " + newValue + "checkBox change!");
                    observableEmitter.onNext(newValue);
                }
            };
            checkBox.selectedProperty().addListener(listener);

//            observableEmitter.addSu(() -> {
//                System.out.println("Cancel");
//                checkBox.selectedProperty().removeListener(listener);
//            });
        });
    }

    public static Observable<Object> clicks(Button button) {
        return Observable.create(observableEmitter -> {

            button.setOnAction(event -> {
                System.out.println("onNext -> click!");

                observableEmitter.onNext(new Object());
            });
//            observableEmitter.setCancellable(() -> button.setOnAction(null));
        }).throttleFirst(1, TimeUnit.SECONDS);
    }

    public static Observable<String> textChanges(TextField textField) {
        return Observable.create(observableEmitter -> {

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!oldValue.contentEquals(newValue)) {
                    System.out.println("onNext -> \"" + newValue + "\" text change");
                    observableEmitter.onNext(newValue);
                }
            });
//            observableEmitter.setCancellable(() -> textField.textProperty().removeListener(listener));
        });
    }
}
