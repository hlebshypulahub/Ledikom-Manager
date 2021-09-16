package org.openjfx.utilities;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Optional;

public final class MyAlert {
    private MyAlert() {

    }

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

    public static void showAndWaitAppUpdate(String type, String header, String link) {
        Stage stage = new Stage();
        WebView webView = new WebView();
        WebEngine engine;
        engine = webView.getEngine();
        engine.setOnAlert(event -> {
            Dialog<Void> alert = new Dialog<>();
            alert.getDialogPane().setContentText(header);
            alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
            alert.showAndWait();
        });
        engine.loadContent("<html><body><p>&nbsp;</p>\n" +
                "<p style=\"text-align: center;\"><strong>Версия программы устарела!</strong></p>\n" +
                "<p style=\"text-align: center;\"><strong>Обновленную версию программы можно скачать тут:</br></a></strong></span></p><p style=\"text-align: center;\"><span style=\"color: #0000ff;\"><strong>" + link + "</strong></span></p></body></html>");

        stage.setTitle("Внимание!");
        stage.setScene(new Scene(new BorderPane(webView), 500, 250));
        stage.show();
    }
}
