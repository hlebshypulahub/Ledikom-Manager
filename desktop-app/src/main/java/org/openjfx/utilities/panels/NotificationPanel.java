package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public final class NotificationPanel implements ShowPane {
    private NotificationPanel() {

    }

    public static void showDobNotifications() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/notifications/dobNotificationV.fxml")));
    }

    public static void showContractNotifications() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/notifications/contractNotificationV.fxml")));
    }
}
