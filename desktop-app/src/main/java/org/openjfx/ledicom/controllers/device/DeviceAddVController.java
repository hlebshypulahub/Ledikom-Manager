package org.openjfx.ledicom.controllers.device;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseDeviceController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeviceAddVController extends DeviceAddInterface {
    @FXML
    void addDevice(ActionEvent event) throws SQLException {
        if(facilityCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать местоположение средства измерения!", "");
        } else if (typeCB.getValue() == null) {
                MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать тип средства измерения!", "");
        }
        else {
            DatabaseDeviceController.addDevice(new Device(facilityCB.getValue(), typeCB.getValue(), nameTF.getText(), Objects.toString(quaterCB.getValue(), "") + " " + Objects.toString(yearCB.getValue(),""),
                    Objects.toString(nextQuaterCB.getValue(), "") + " " + Objects.toString(nextYearCB.getValue(), ""), intervalTF.getText(), locationTF.getText(), numberTF.getText(), temperatureRangeTF.getText()));
            MyAlert.showAndWait("INFORMATION", "", "Средство измерения добавлено!", nameTF.getText() + " (" + typeCB.getValue() + ")");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }
}
