package org.openjfx.ledicom.controllers.device;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseDeviceController;
import org.openjfx.utilities.panels.DevicePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeviceEditVController extends DeviceDataForm {

    @FXML
    public void deviceEdit(ActionEvent event) throws SQLException, IOException {
        if (facilityCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать местоположение средства измерения!", "");
        } else if (typeCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать тип средства измерения!", "");
        } else if (quaterCB.getValue() == null || yearCB.getValue() == null || nextQuaterCB.getValue() == null || nextYearCB.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо указать даты поверок!", "");
        } else {
            Global.setDevice(new Device(Global.getDevice().getId(), facilityCB.getValue(), typeCB.getValue(), nameTF.getText(), Objects.toString(quaterCB.getValue(), "") + " " + Objects.toString(yearCB.getValue(), ""),
                    Objects.toString(nextQuaterCB.getValue(), "") + " " + Objects.toString(nextYearCB.getValue(), ""), intervalTF.getText(), locationTF.getText(), numberTF.getText(), temperatureRangeTF.getText()));
            DatabaseDeviceController.editDevice(Global.getDevice());
            MyAlert.showAndWait("INFORMATION", "", "Средство измерения изменено!", nameTF.getText() + " (" + typeCB.getValue() + ")");
            DevicePanel.showDeviceDetails();
            DevicePanel.showDeviceEdit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        typeCB.setValue(Global.getDevice().getType());
        nameTF.setText(Global.getDevice().getName());
        temperatureRangeTF.setText(Global.getDevice().getTemperatureRange());
        numberTF.setText(Global.getDevice().getNumber());
        quaterCB.setValue(Global.getDevice().getVerificationDate().substring(0, Global.getDevice().getVerificationDate().indexOf('.') + 1));
        yearCB.setValue(Global.getDevice().getVerificationDate().substring(Global.getDevice().getVerificationDate().indexOf('.') + 2));
        nextQuaterCB.setValue(Global.getDevice().getNextVerificationDate().substring(0, Global.getDevice().getNextVerificationDate().indexOf('.') + 1));
        nextYearCB.setValue(Global.getDevice().getNextVerificationDate().substring(Global.getDevice().getNextVerificationDate().indexOf('.') + 2));
        intervalTF.setText(Global.getDevice().getVerificationInterval());
        locationTF.setText(Global.getDevice().getLocation());
        facilityCB.setValue(Global.getDevice().getFacility());
    }
}
