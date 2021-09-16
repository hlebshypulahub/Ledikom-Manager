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

    public static void showDeviceDetails() throws IOException {
        ShowPane.showDetailsPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/device/deviceDetailsD.fxml")));
    }

    public static void showDeviceEdit() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/device/deviceEditV.fxml")));
    }
}
