package org.openjfx.ledicom.controllers.device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseDeviceController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.docs.DeviceFacilityDoc;
import org.openjfx.utilities.docs.DeviceScheduleDoc;
import org.openjfx.utilities.panels.DevicePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public void printForFacility(ActionEvent event) throws IOException {
        DeviceFacilityDoc.createTable(table.getItems(), facilityCB.getValue());
    }

    @FXML
    public void printSchedule(ActionEvent event) throws IOException, SQLException {
        DeviceScheduleDoc.createDocument(DatabaseDeviceController.getDevices().stream().filter(d -> d.getNextVerificationDate().substring(d.getNextVerificationDate().indexOf('.') + 2).equals(yearCB.getValue()))
                                                                                      .collect(Collectors.toCollection(FXCollections::observableArrayList)), yearCB.getValue());
    }

    @FXML
    public void showDetails(MouseEvent event) throws IOException {
        if(table.getSelectionModel().getSelectedIndex() >= 0) {
            Global.setDevice(table.getSelectionModel().getSelectedItem());
            DevicePanel.showDeviceDetails();
        }
    }

    @FXML
    public void reset(ActionEvent event) throws IOException {
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
        yearCB.setItems(FXCollections.observableArrayList("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));

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

        table.setRowFactory(event -> {
            TableRow<Device> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setDevice(table.getSelectionModel().getSelectedItem());
                        DevicePanel.showDeviceEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
