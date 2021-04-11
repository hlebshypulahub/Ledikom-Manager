package org.openjfx.ledicom.controllers.interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;

public interface FacilityControllerInterface {
    @FXML
    default void showFacilityDetails(TableView<Facility> table) throws IOException {
        if (setGlobalFacility(table)) {
            FacilityPanel.showFacilityDetails();
        }
    }

    @FXML
    default void showFacilityManagement(TableView<Facility> table) throws IOException {
        if (setGlobalFacility(table)) {
            FacilityPanel.showFacilityManagement();
        }
    }

    @FXML
    default boolean setGlobalFacility(TableView<Facility> table) throws IOException {
        if(table.getSelectionModel().getSelectedIndex() >= 0) {
            Global.setFacility(table.getSelectionModel().getSelectedItem());
            return true;
        }
        return false;
    }
}
