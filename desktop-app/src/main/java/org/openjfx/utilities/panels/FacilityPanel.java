package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FacilityPanel implements ShowPane{
    public static void showFacilityAdd() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/facilityAddV.fxml")));
    }

    public static void showAllFacility() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/facilityAllV.fxml")));
    }

    public static void showFacilityEdit() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/facilityEditV.fxml")));
    }

    public static void showFacilityManagement() throws IOException {
        ShowPane.showFooterPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/facilityManagementM.fxml")));
    }

    public static void showFacilityDetails() throws IOException {
        ShowPane.showDetailsPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/facilityDetailsD.fxml")));
    }

    public static void showInspectionAdd() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(FacilityPanel.class.getResource("/org/openjfx/ledicom/controllers/facility/inspectionAddV.fxml")));
    }
}
