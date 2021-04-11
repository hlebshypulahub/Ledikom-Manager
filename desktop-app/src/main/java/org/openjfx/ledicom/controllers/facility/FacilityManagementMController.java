package org.openjfx.ledicom.controllers.facility;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilityManagementMController implements Initializable {

    @FXML
    public void showInspectionAdd(Event e) throws IOException {
        FacilityPanel.showInspectionAdd();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
