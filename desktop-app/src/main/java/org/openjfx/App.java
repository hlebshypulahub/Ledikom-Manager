package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseNotificationController;
import org.openjfx.utilities.panels.EmployeePanel;
import org.openjfx.utilities.panels.NotificationPanel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Objects;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, SQLException, URISyntaxException {
        scene = new Scene(loadFXML("/org/openjfx/ledicom/controllers/dashboard"));
        stage.setScene(scene);
        stage.setTitle("Ледиком Менеджер");
        stage.show();
        stage.setMaximized(true);
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/org/openjfx/ledicom/icon.png"))));
        stage.setOnCloseRequest(event -> {
            try {
                DatabaseController.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        DatabaseEmployeeController.updateEmployeeDatesOnAppStart();

        EmployeePanel.showAllEmployee();

        if(Global.getAppVersion() != DatabaseController.getActualVersion()) {
            MyAlert.showAndWaitAppUpdate("INFORMATION", "", "https://bit.ly/399N194");
        }

        if (DatabaseNotificationController.getOnAppStart() && !DatabaseEmployeeController.dobNotificationsEmployeeList().isEmpty()) {
            if (MyAlert.showAndWaitButtonType("INFORMATION", "Дни рождения", "У некоторых сотрудников скоро день рождения!", "")) {
                NotificationPanel.showDobNotifications();
            }
        }

        if (DatabaseNotificationController.getContractOnAppStart() && !DatabaseNotificationController.contractNotificationsList().isEmpty()) {
            if (MyAlert.showAndWaitButtonType("INFORMATION", "Контракты", "У некоторых сотрудников скоро заканчивается контракт!", "")) {
                NotificationPanel.showContractNotifications();
            }
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}