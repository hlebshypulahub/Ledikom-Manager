package org.openjfx.ledicom.controllers.facility;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseEnumsController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FacilityEditVController extends FacilityDataForm {

    @FXML
    private TextField searchEmployeeTF;
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
    private VBox contractVBox;
    @FXML
    private ComboBox<String> contractTypeCB;
    @FXML
    private DatePicker contractStartDate;
    @FXML
    private DatePicker contractEndDate;

    @FXML
    public void updateFacility(ActionEvent event) throws IOException, SQLException {
        Global.setFacility(new Facility(Global.getFacility().getId(), nameTF.getText(), statusCB.getValue(), categoryCB.getValue(), scheduleTF.getText(),
                cityTF.getText(), addressTF.getText(), phoneTF.getText(), emailTF.getText()));

        if (DatabaseFacilityController.updateFacility()) {
            MyAlert.showAndWait("INFORMATION", "", "Объект отредактирован.", "");

            Global.setFacility(DatabaseFacilityController.getFacility(Global.getFacility().getId()));

            FacilityPanel.showFacilityDetails();
            FacilityPanel.showFacilityEdit();
        }
    }

    @FXML
    public void showContractBox(MouseEvent event) {
        if (employeeTable.getSelectionModel().getSelectedIndex() != -1) {
            contractVBox.setVisible(true);
        }
    }

    @FXML
    public void addEmployeeToFacility(ActionEvent event) throws SQLException, IOException {
        Global.setEmployee(employeeTable.getSelectionModel().getSelectedItem());
        if (DatabaseEmployeeController.addEmployeeToFacility(new EmployeeContract(contractTypeCB.getValue(), contractStartDate.getValue(), contractEndDate.getValue()))) {
            MyAlert.showAndWait("INFORMATION", "", "Сотрудник " + Global.getEmployee().getShortName()
                    + " добавлен на объект " + Global.getFacility().getName(), "");
            FacilityPanel.showFacilityEdit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        nameTF.setText(Global.getFacility().getName());
        statusCB.setValue(Global.getFacility().getStatus());
        categoryCB.setValue(Global.getFacility().getCategory());
        addressTF.setText(Global.getFacility().getAddress());
        emailTF.setText(Global.getFacility().getEmail());
        phoneTF.setText(Global.getFacility().getPhone());
        cityTF.setText(Global.getFacility().getCity());
        scheduleTF.setText(Global.getFacility().getSchedule());

        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        contractTypeCB.setItems(DatabaseEnumsController.getContractTypes());

        /// Sorting
        FilteredList<Employee> filteredData = new FilteredList<>(DatabaseEmployeeController.employeeForNotFacility(), p -> true);

        searchEmployeeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return ((employee.getFirstName().toLowerCase().contains(lowerCaseFilter))
                        || (employee.getLastName().toLowerCase().contains(lowerCaseFilter)))
                        || (employee.getPatronymic().toLowerCase().contains(lowerCaseFilter));
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }
}
