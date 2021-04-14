package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class NotificationPanel implements ShowPane {
    public static void showDobNotifications() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/notifications/dobNotificationV.fxml")));
    }
}
