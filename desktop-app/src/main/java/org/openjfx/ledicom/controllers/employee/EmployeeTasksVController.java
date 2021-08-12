package org.openjfx.ledicom.controllers.employee;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Task;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> shortNameCol;
    @FXML
    private TableColumn<Employee, String> positionCol;
    @FXML
    private DatePicker addDate;
    @FXML
    private TextArea addTaskTA;

    @FXML
    public void showAddTask(ActionEvent event) {
        addTaskButton.setVisible(false);
        addTaskVBox.setVisible(true);

        setEmployeeTable();
    }

    public void addTask(ActionEvent event) throws SQLException, IOException {
        if (employeeTable.getSelectionModel().getSelectedIndex() == -1) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Выберите адресата поручения", "");
            return;
        }

        if (addTaskTA.getText().isEmpty()) {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Поручение не может быть пустым!", "");
            return;
        }

        DatabaseEmployeeController.addTask(new Task(employeeTable.getSelectionModel().getSelectedItem(),
                Validator.validateDate(addDate), addTaskTA.getText()));

        EmployeePanel.showEmployeeTasks();
    }

    public void setTaskTable() throws SQLException {
        FilteredList<Task> filteredData = new FilteredList<>(DatabaseEmployeeController.employeeTasks(), p -> true);

        findTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(task -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return ((task.getEmployeeName().toLowerCase().contains(lowerCaseFilter))
                        || (task.getTask().toLowerCase().contains(lowerCaseFilter)));
            });
        });

        SortedList<Task> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    public void enableDeleteButton(MouseEvent event) {
        if (table.getSelectionModel().getSelectedIndex() != -1) {
            deleteTaskButton.setDisable(false);
        }
    }

    public void setEmployeeTable() {
        FilteredList<Employee> filteredData = new FilteredList<>(DatabaseEmployeeController.allEmployeeList(), p -> true);

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

    @FXML
    public void deleteTask(ActionEvent event) throws IOException, SQLException {
        DatabaseEmployeeController.deleteTask(table.getSelectionModel().getSelectedItem().getId());

        EmployeePanel.showEmployeeTasks();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        findTF.requestFocus();

        shortNameCol.setCellValueFactory(new PropertyValueFactory<>("shortName"));
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        addTaskTA.setWrapText(true);

        taskCol.setCellFactory(tc -> {
            TableCell<Task, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(taskCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        try {
            setTaskTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
