package org.openjfx.ledicom.controllers.interfaces;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.EmployeeCourseData;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;

public interface EmployeeControllerInterface {
    @FXML
    default void showEmployeeDetails(TableView<Employee> table) throws IOException {
        if (setGlobalEmployee(table)) {
            EmployeePanel.showEmployeeDetails();
        }
    }

    @FXML
    default void showEmployeesCoursesDetails(TableView<EmployeeCourseData> table) throws IOException {
        if(table.getSelectionModel().getSelectedIndex() >= 0) {
            Global.setEmployee(DatabaseEmployeeController.getEmployee(table.getSelectionModel().getSelectedItem().getEmployeeId()));
            EmployeePanel.showEmployeeDetails();
            EmployeePanel.showEmployeeManagement();
        }
    }

//    @FXML
//    default void showEmployeeManagement(TableView<Employee> table) throws IOException {
//        if (setGlobalEmployee(table)) {
//            EmployeePanel.showEmployeeManagement();
//        }
//    }

    @FXML
    default boolean setGlobalEmployee(TableView<Employee> table) throws IOException {
        if(table.getSelectionModel().getSelectedIndex() >= 0) {
            Global.setEmployee(table.getSelectionModel().getSelectedItem());
            return true;
        }
        return false;
    }
}
