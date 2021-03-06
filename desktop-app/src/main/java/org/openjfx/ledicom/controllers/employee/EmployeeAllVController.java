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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EmployeeAllVController implements Initializable, EmployeeControllerInterface {

    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, String> fullNameCol;
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
    private TableColumn<Employee, String> fullNameColF;
    @FXML
    private TableColumn<Employee, String> valueColF;

    @FXML
    private TableView<Employee> filteredTableInt;
    @FXML
    private TableColumn<Employee, String> fullNameColFInt;
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
        if (filteredTable.isVisible()) {
            showEmployeeDetails(filteredTable);
        } else if (filteredTableInt.isVisible()) {
            showEmployeeDetails(filteredTableInt);
        }
        showEmployeeManagement(filteredTable);
    }

    @FXML
    public void filterTable(ActionEvent event) throws IOException {
        Facility facility = facilityCB.getSelectionModel().getSelectedItem();

        employeeList = facility == null ? DatabaseEmployeeController.allEmployeeList()
                : DatabaseEmployeeController.getEmployeesForFacility(facility.getId());

        table.setItems(employeeList);

        if (valueCB.getValue() != null) {
            table.setVisible(false);
            printButton.setDisable(false);
            switch (valueCB.getValue()) {
                case SALARY:
                    Global.getDetailsPane().getChildren().clear();
                    Global.getFooterPane().getChildren().clear();
                    EmployeePanel.showEmployeeCharts();

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
                    Global.getDetailsPane().getChildren().clear();
                    Global.getFooterPane().getChildren().clear();
                    EmployeePanel.showEmployeeCharts();

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
                    Global.getDetailsPane().getChildren().clear();
                    Global.getFooterPane().getChildren().clear();
                    EmployeePanel.showEmployeeChildrenNumber();

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
                    Global.getDetailsPane().getChildren().clear();
                    Global.getFooterPane().getChildren().clear();
                    EmployeePanel.showEmployeeCharts();

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
        Global.getDetailsPane().getChildren().clear();
        Global.getFooterPane().getChildren().clear();
        try {
            EmployeePanel.showEmployeeCharts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColF.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColFInt.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        dobCol.setComparator(new DateComparator());
        employeeList = DatabaseEmployeeController.allEmployeeList();
        table.setItems(employeeList);
        table.setPlaceholder(new Label("???????????? ???? ??????????????"));
        filteredTable.setPlaceholder(new Label("???????????? ???? ??????????????"));
        filteredTableInt.setPlaceholder(new Label("???????????? ???? ??????????????"));

        try {
            facilityCB.setItems(DatabaseFacilityController.allFacilityList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        valueCB.setItems(Arrays.stream(EmployeeTableValue.values()).collect(Collectors.toCollection(FXCollections::observableArrayList)));

        TableDoubleClickSetter.setEmployeeTable(table);
        TableDoubleClickSetter.setEmployeeTable(filteredTableInt);
        TableDoubleClickSetter.setEmployeeTable(filteredTable);

        setEmployeeTable(table);
    }

    public void selectEmployee() throws IOException {
        table.requestFocus();
        for (int i = 0; i < table.getItems().toArray().length; i++) {
            if (table.getItems().get(i).getId() == Global.getEmployee().getId()) {
                table.getSelectionModel().select(i);
                table.getFocusModel().focus(i);
                table.scrollTo(i);
                EmployeePanel.showEmployeeDetails();
                EmployeePanel.showEmployeeManagement();
                break;
            }
        }
    }
}
