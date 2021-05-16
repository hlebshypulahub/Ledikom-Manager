package org.openjfx.ledicom;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.database.DatabaseController;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, String> dobCol;

    @FXML
    private TableColumn<Employee, String> ageCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("dobAge"));
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.FRIDAY) {
            table.setPlaceholder(new Label("Сегодня и на выходных дней рождения у сотрудников нет!"));
        } else {
            table.setPlaceholder(new Label("Сегодня дней рождения у сотрудников нет!"));
        }

        ObservableList<Employee> employeeList = DatabaseController.dobNotificationsEmployeeList();
        table.setItems(employeeList);

//        if (employeeList.isEmpty()) {
//            Platform.exit();
//        } else {
//            table.setItems(employeeList);
//        }
    }
}
