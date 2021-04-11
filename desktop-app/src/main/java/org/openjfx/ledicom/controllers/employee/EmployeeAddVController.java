package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.utilities.converters.StringToIntegerConverter;
import org.openjfx.utilities.exceptions.EmployeeException;
import org.openjfx.utilities.exceptions.TextException;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseEmployeeController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeAddVController extends EmployeeDataForm {

    @FXML
    public void addEmployee(ActionEvent event) {
        try {
            Global.setEmployee(new Employee(Validator.validateName(lastNameTF.getText(), lastNameTF), Validator.validateName(firstNameTF.getText(), firstNameTF),
                    Validator.validateName(patronymicTF.getText(), patronymicTF), dobDate.getValue(), phoneTF.getText(), addressTF.getText(),
                    StringToIntegerConverter.convert(salaryTF.getText()), ppeDate.getValue(), hiringDate.getValue(), positionCB.getValue(),
                    categoryCB.getValue(), categoryNumTF.getText(), categoryAssignmentDate.getValue(), maternityStartDate.getValue(), maternityEndDate.getValue(),
                    fiveYearStartDate.getValue(), fiveYearEndDate.getValue(), StringToIntegerConverter.convert(childrenNumberTF.getText()), noteTF.getText()));

            if (DatabaseEmployeeController.addEmployee(Global.getEmployee())) {
                Global.setEmployeeList(DatabaseEmployeeController.allEmployeeList());
                MyAlert.showAndWait("INFORMATION", "", "Сотрудник " + Global.getEmployee().getFullName() + " добавлен!", "");
            }
        } catch (TextException | EmployeeException | SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();
        super.initialize(url, resourceBundle);
    }
}
