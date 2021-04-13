package org.openjfx.ledicom.controllers.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.EmployeeValue;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.comparators.DateComparator;
import org.openjfx.utilities.comparators.IntComparator;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeAllVController implements Initializable, EmployeeControllerInterface {

    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> lastNameCol;
    @FXML
    private TableColumn<Employee, String> firstNameCol;
    @FXML
    private TableColumn<Employee, String> patronymicCol;
    @FXML
    private TableColumn<Employee, String> dobCol;
    @FXML
    private TableColumn<Employee, String> positionCol;
    @FXML
    private TableColumn<Employee, Integer> salaryCol;
    @FXML
    private ComboBox<Facility> facilityCB;
    @FXML
    private ComboBox<EmployeeValue> valueCB;

    @FXML
    private TableView<Employee> filteredTable;
    @FXML
    private TableColumn<Employee, String> lastNameColF;
    @FXML
    private TableColumn<Employee, String> firstNameColF;
    @FXML
    private TableColumn<Employee, String> patronymicColF;
    @FXML
    private TableColumn<Employee, String> valueColF;

    @FXML
    private TableView<Employee> filteredTableInt;
    @FXML
    private TableColumn<Employee, String> lastNameColFInt;
    @FXML
    private TableColumn<Employee, String> firstNameColFInt;
    @FXML
    private TableColumn<Employee, String> patronymicColFInt;
    @FXML
    private TableColumn<Employee, Integer> valueColFInt;

    ObservableList<Employee> employeeList;

    @FXML
    public void showFullInfo(MouseEvent event) throws IOException {
        showEmployeeDetails(table);
        showEmployeeManagement(table);
    }

    @FXML
    public void showFullInfoFiltered(MouseEvent event) throws IOException {
        showEmployeeDetails(filteredTable);
        showEmployeeManagement(filteredTable);
    }

    @FXML
    public void filterTable(ActionEvent event) {
        Facility facility = facilityCB.getSelectionModel().getSelectedItem();

        employeeList = facility == null ? DatabaseEmployeeController.allEmployeeList()
                : DatabaseEmployeeController.getEmployeesForFacility(facility.getId());

        table.setItems(employeeList);

        if (valueCB.getValue() != null) {
            switch (valueCB.getValue()) {
                case CHILDREN_NUMBER:
                case SALARY:
                    valueColFInt.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColFInt.setText(valueCB.getValue().getName());

                    filteredTableInt.setItems(employeeList);
                    filteredTableInt.setVisible(true);

                    filteredTable.setVisible(false);

                    valueColFInt.setComparator(new IntComparator());
                    break;
                case DOB:
                case PPE:
                case HIRING_DATE:
                    valueColF.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColF.setText(valueCB.getValue().getName());

                    filteredTable.setItems(employeeList);
                    filteredTable.setVisible(true);

                    filteredTableInt.setVisible(false);

                    valueColF.setComparator(new DateComparator());
                    break;
                default:
                    valueColF.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColF.setText(valueCB.getValue().getName());

                    filteredTable.setItems(employeeList);
                    filteredTable.setVisible(true);

                    filteredTableInt.setVisible(false);

                    valueColF.setComparator(null);
            }
        }
    }

    @FXML
    public void reset(ActionEvent event) throws IOException {
        EmployeePanel.showAllEmployee();
        Global.getDetailsPane().getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        firstNameColF.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColF.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicColF.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        firstNameColFInt.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColFInt.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicColFInt.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        dobCol.setComparator(new DateComparator());
        employeeList = DatabaseEmployeeController.allEmployeeList();
        table.setItems(employeeList);
        table.setPlaceholder(new Label("Данные не найдены"));
        filteredTable.setPlaceholder(new Label("Данные не найдены"));
        filteredTableInt.setPlaceholder(new Label("Данные не найдены"));

        facilityCB.setItems(DatabaseFacilityController.allFacilityList());
        valueCB.setItems(Arrays.stream(EmployeeValue.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

        table.setRowFactory(event -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setEmployee(table.getSelectionModel().getSelectedItem());
                        EmployeePanel.showEmployeeEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
