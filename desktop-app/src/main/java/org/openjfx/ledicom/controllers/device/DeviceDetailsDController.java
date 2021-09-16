package org.openjfx.ledicom.controllers.device;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseDeviceController;
import org.openjfx.utilities.panels.DevicePanel;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeviceDetailsDController implements Initializable {
    @FXML
    private Text typeText;
    @FXML
    private Text nameText;
    @FXML
    private Text temperatureRangeText;
    @FXML
    private Text numberText;
    @FXML
    private Text dateText;
    @FXML
    private Text intervalText;
    @FXML
    private Text nextDateText;
    @FXML
    private Text locationText;
    @FXML
    private Text facilityText;

    @FXML
    void deviceDelete(ActionEvent event) throws IOException, SQLException {
        if(MyAlert.showAndWaitWarning("Внимание", "Вы действительно хотите удалить средство измерения?",  Global.getDevice().getName() + " (" + Global.getDevice().getType() + ")")) {
            DatabaseDeviceController.deleteDevice(Global.getDevice());
            MyAlert.showAndWait("INFORMATION", "Успешно", "Средство измерения удалено!",  Global.getDevice().getName() + " (" + Global.getDevice().getType() + ")");
            Global.setDevice(null);
            DevicePanel.showAllDevices();
            FacilityPanel.showFacilityCharts();
        }
    }

    @FXML
    void deviceEdit(ActionEvent event) throws IOException {
        DevicePanel.showDeviceEdit();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeText.setText(Global.getDevice().getType());
        nameText.setText(Global.getDevice().getName());
        temperatureRangeText.setText(Global.getDevice().getTemperatureRange());
        numberText.setText(Global.getDevice().getNumber());
        dateText.setText(Global.getDevice().getVerificationDate());
        nextDateText.setText(Global.getDevice().getNextVerificationDate());
        intervalText.setText(Global.getDevice().getVerificationInterval());
        locationText.setText(Global.getDevice().getLocation());
        facilityText.setText(Global.getDevice().getFacility().getName() + ", " + Global.getDevice().getFacility().getFullAddress());
    }
}
