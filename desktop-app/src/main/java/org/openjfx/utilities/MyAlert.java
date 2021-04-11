package org.openjfx.utilities;

import javafx.scene.control.Alert;

public class MyAlert {
    public static void showAndWait(String type, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
