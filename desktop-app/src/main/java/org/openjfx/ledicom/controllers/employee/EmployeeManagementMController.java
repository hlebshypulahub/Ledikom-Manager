package org.openjfx.ledicom.controllers.employee;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementMController implements Initializable {

    @FXML
    public void employeeAdd(Event e) throws IOException {
        EmployeePanel.showEmployeeAdd();
        Global.getDetailsPane().getChildren().clear();
    }

    @FXML
    public void employeeAll(Event e) throws IOException {
        EmployeePanel.showAllEmployee();
        Global.getDetailsPane().getChildren().clear();
    }

    @FXML
    public void employeeCourses(Event e) throws IOException {
        EmployeePanel.showEmployeesCourses();
        Global.getDetailsPane().getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
