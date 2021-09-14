package org.openjfx.ledicom.controllers.device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseDeviceController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.panels.DevicePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeviceAllVController implements Initializable {

    @FXML
    private TableView<Device> table;
    @FXML
    private TableColumn<Device, String> dateCol;
    @FXML
    private TableColumn<Device, String> nameCol;
    @FXML
    private TableColumn<Device, String> numberCol;
    @FXML
    private TableColumn<Device, Facility> facilityCol;
    @FXML
    private ComboBox<Facility> facilityCB;
    @FXML
    private Button schedulePrintButton;
    @FXML
    private Button facilityDevicesPrintButton;
    @FXML
    private ComboBox<String> yearCB;

    @FXML
    void printForFacility(ActionEvent event) {

    }

    @FXML
    void printSchedule(ActionEvent event) {

    }

    @FXML
    void reset(ActionEvent event) throws IOException {
        DevicePanel.showAllDevices();
        Global.getDetailsPane().getChildren().clear();
    }

    @FXML
    public void filter(ActionEvent event) throws SQLException {
        if(facilityCB.getValue() != null) {
            facilityDevicesPrintButton.setDisable(false);
        }
        if(yearCB.getValue() != null) {
            schedulePrintButton.setDisable(false);
        }

        table.setItems(DatabaseDeviceController.getDevices(facilityCB.getValue(), yearCB.getValue()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yearCB.setItems(FXCollections.observableArrayList("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));

        dateCol.setCellValueFactory(new PropertyValueFactory<>("nextVerificationDate"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facility"));

        table.setPlaceholder(new Label("Данные не найдены"));

        try {
            ObservableList<Facility> observableList;
            observableList = DatabaseFacilityController.allFacilityList();
            observableList.add(0, new Facility("Резервные средства измерений", -1));
            facilityCB.setItems(observableList);

            table.setItems(DatabaseDeviceController.getDevices());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
