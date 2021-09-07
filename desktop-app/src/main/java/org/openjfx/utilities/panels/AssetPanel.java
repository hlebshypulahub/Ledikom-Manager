package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public final class AssetPanel implements ShowPane {
    private AssetPanel() {

    }

    public static void showAddAsset() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/asset/assetAddV.fxml")));
    }

    public static void showAllAssets() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(NotificationPanel.class.getResource("/org/openjfx/ledicom/controllers/asset/assetAllV.fxml")));
    }
}
