package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.openjfx.utilities.Global;

import java.io.IOException;

interface ShowPane {
    static void showDetailsPane(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Global.getDetailsPane().getChildren().clear();
        Global.getDetailsPane().getChildren().addAll(root);
    }

    static void showFooterPane(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Global.getFooterPane().getChildren().clear();
        Global.getFooterPane().getChildren().addAll(root);
    }

    static void showViewPane(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Global.getViewPane().getChildren().clear();
        Global.getViewPane().getChildren().addAll(root);
    }
}
