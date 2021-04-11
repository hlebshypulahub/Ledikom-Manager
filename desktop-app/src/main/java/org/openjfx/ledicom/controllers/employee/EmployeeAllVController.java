package org.openjfx.ledicom.controllers.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.controllers.interfaces.EmployeeControllerInterface;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.panels.EmployeePanel;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.comparators.EmployeeDobComparator;
import org.openjfx.utilities.database.DatabaseEmployeeController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    public void showFullInfo(MouseEvent event) throws IOException {
        showEmployeeDetails(table);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patronymicCol.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        dobCol.setComparator(new EmployeeDobComparator());
        table.setItems(DatabaseEmployeeController.allEmployeeList());

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
