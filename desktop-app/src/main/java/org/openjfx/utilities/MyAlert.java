package org.openjfx.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MyAlert {
    public static void showAndWait(String type, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showAndWaitButtonType(String type, String title, String header, String content) {
        boolean out = true;

        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType showButtonType = new ButtonType("Показать", ButtonBar.ButtonData.OK_DONE);
        alert.getDialogPane().getButtonTypes().add(showButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            out = false;
        }

        return out;
    }

    public static boolean showAndWaitWarning(String title, String header, String content) {
        boolean out = false;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType cancelButtonType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(cancelButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            out = true;
        }

        return out;
    }
}
