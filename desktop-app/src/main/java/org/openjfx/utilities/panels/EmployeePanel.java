package org.openjfx.utilities.panels;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class EmployeePanel implements ShowPane {
    public static void showEmployeeDetails() throws IOException {
        ShowPane.showDetailsPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeDetailsD.fxml")));
    }

    public static void showEmployeeEdit() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeEditV.fxml")));
    }

    public static void showEmployeesCourses() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeCoursesV.fxml")));
    }

    public static void showEmployeeManagement() throws IOException {
        ShowPane.showFooterPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeManagementM.fxml")));
    }

    public static void showAllEmployee() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeAllV.fxml")));
    }

    public static void showEmployeeAdd() throws IOException {
        ShowPane.showViewPane(new FXMLLoader(EmployeePanel.class.getResource("/org/openjfx/ledicom/controllers/employee/employeeAddV.fxml")));
    }
}
