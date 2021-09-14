package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class DevicePanel implements ShowPane {
        private DevicePanel() {

        }

        public static void showAddDevice() throws IOException {
            ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/device/deviceAddV.fxml")));
        }

        public static void showAllDevices() throws IOException {
            ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/device/deviceAllV.fxml")));
        }
}
