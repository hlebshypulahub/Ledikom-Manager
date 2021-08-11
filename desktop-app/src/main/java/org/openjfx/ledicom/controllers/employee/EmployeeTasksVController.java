package org.openjfx.ledicom.controllers.employee;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.openjfx.ledicom.entities.Employee;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeTasksVController implements Initializable {
    @FXML
    private TableView<Task> table;
    @FXML
    private TableColumn<Task, Employee> nameCol;
    @FXML
    private TableColumn<Task, String> taskCol;
    @FXML
    private TableColumn<Task, String> dateCol;
    @FXML
    private TextField findTF;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button deleteTaskButton;
    @FXML
    private VBox addTaskVBox;
    @FXML
    private TextField searchEmployeeTF;
    @FXML
    private TableView<?> employeeTable;
    @FXML
    private TableColumn<?, ?> fullNameCol;
    @FXML
    private TableColumn<?, ?> positionCol;
    @FXML
    private DatePicker addDate;
    @FXML
    private TextArea addTaskTA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
