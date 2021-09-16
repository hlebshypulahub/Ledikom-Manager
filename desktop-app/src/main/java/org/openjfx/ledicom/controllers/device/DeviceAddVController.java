package org.openjfx.ledicom.controllers.device;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseDeviceController;
import org.openjfx.utilities.panels.DevicePanel;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeviceAddVController extends DeviceDataForm {
    @FXML
    void addDevice(ActionEvent event) throws SQLException, IOException {
        if (facilityCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать местоположение средства измерения!", "");
        } else if (typeCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать тип средства измерения!", "");
        } else if (quaterCB.getValue() == null || yearCB.getValue() == null || nextQuaterCB.getValue() == null || nextYearCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать даты поверок!", "");
        } else {
            DatabaseDeviceController.addDevice(new Device(facilityCB.getValue(), typeCB.getValue(), nameTF.getText(), Objects.toString(quaterCB.getValue(), "") + " " + Objects.toString(yearCB.getValue(), ""),
                    Objects.toString(nextQuaterCB.getValue(), "") + " " + Objects.toString(nextYearCB.getValue(), ""), intervalTF.getText(), locationTF.getText(), numberTF.getText(), temperatureRangeTF.getText()));
            MyAlert.showAndWait("INFORMATION", "", "Средство измерения добавлено!", nameTF.getText() + " (" + typeCB.getValue() + ")");
            DevicePanel.showAllDevices();
            FacilityPanel.showFacilityCharts();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
}
