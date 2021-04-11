package org.openjfx.ledicom.controllers.facility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FacilityDetailsDController implements Initializable {

    @FXML
    private Text nameText;
    @FXML
    private Text statusText;
    @FXML
    private Text categoryText;
    @FXML
    private Text phoneText;
    @FXML
    private Text emailText;
    @FXML
    private Text addressText;
    @FXML
    private Text scheduleText;
    @FXML
    private Text codeText;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> patronymicCol;
    @FXML
    private TableColumn<Employee, String> positionCol;

    @FXML
    public void facilityEdit(ActionEvent event) throws IOException {
        FacilityPanel.showFacilityEdit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameText.setText(Global.getFacility().getName());
        statusText.setText(Global.getFacility().getStatus());
        categoryText.setText(Global.getFacility().getCategory());
        phoneText.setText(Global.getFacility().getPhone());
        emailText.setText(Global.getFacility().getEmail());
        addressText.setText(Global.getFacility().getFullAddress());
        scheduleText.setText(Global.getFacility().getSchedule());
        codeText.setText(Global.getFacility().getCode());

        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        employeeTable.setItems(DatabaseEmployeeController.employeeForFacility());
    }
}
