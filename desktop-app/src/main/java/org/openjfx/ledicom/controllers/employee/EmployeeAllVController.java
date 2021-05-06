package org.openjfx.ledicom.controllers.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.EmployeeTableValue;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.TableDoubleClickSetter;
import org.openjfx.utilities.comparators.ChildrenDataComparator;
import org.openjfx.utilities.comparators.DateComparator;
import org.openjfx.utilities.comparators.IntComparator;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.docs.EmployeeTableDoc;
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
    private ComboBox<EmployeeTableValue> valueCB;

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

    @FXML
    private TextField findTF;
    @FXML
    private Button printButton;


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
            table.setVisible(false);
            printButton.setDisable(false);
            switch (valueCB.getValue()) {
                case SALARY:
                    valueColFInt.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColFInt.setText(valueCB.getValue().getName());

                    filteredTableInt.setItems(employeeList);
                    filteredTableInt.setVisible(true);

                    filteredTable.setVisible(false);

                    valueColFInt.setComparator(new IntComparator());

                    findTF.setText("");
                    setEmployeeTable(filteredTableInt);
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

                    findTF.setText("");
                    setEmployeeTable(filteredTable);
                    break;
                case CHILDREN_DATA:
                    valueColF.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColF.setText(valueCB.getValue().getName());

                    filteredTable.setItems(employeeList);
                    filteredTable.setVisible(true);

                    filteredTableInt.setVisible(false);

                    valueColF.setComparator(new ChildrenDataComparator());

                    findTF.setText("");
                    setEmployeeTable(filteredTable);
                    break;
                default:
                    valueColF.setCellValueFactory(new PropertyValueFactory<>(valueCB.getValue().getValue()));
                    valueColF.setText(valueCB.getValue().getName());

                    filteredTable.setItems(employeeList);
                    filteredTable.setVisible(true);

                    filteredTableInt.setVisible(false);

                    valueColF.setComparator(null);

                    findTF.setText("");
                    setEmployeeTable(filteredTable);
            }
        }
    }

    public void setEmployeeTable(TableView<Employee> table) {
        FilteredList<Employee> filteredData = new FilteredList<>(employeeList, p -> true);

        findTF.textProperty().addListener((observable, oldValue, newValue) -> {
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
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    public void reset(ActionEvent event) throws IOException {
        EmployeePanel.showAllEmployee();
        Global.getDetailsPane().getChildren().clear();
    }

    @FXML
    public void printTable(ActionEvent event) throws IOException {
        if (filteredTable.isVisible()) {
            EmployeeTableDoc.createTable(filteredTable.getItems(), valueCB.getValue(), facilityCB.getValue());
        } else if (filteredTableInt.isVisible()) {
            EmployeeTableDoc.createTable(filteredTableInt.getItems(), valueCB.getValue(), facilityCB.getValue());
        }
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
        valueCB.setItems(Arrays.stream(EmployeeTableValue.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

        TableDoubleClickSetter.setEmployeeTable(table);
        TableDoubleClickSetter.setEmployeeTable(filteredTableInt);
        TableDoubleClickSetter.setEmployeeTable(filteredTable);

        setEmployeeTable(table);
    }
}
