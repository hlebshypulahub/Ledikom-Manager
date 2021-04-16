package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.converters.StringToIntegerConverter;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.exceptions.EmployeeException;
import org.openjfx.utilities.exceptions.TextException;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeAddVController extends EmployeeDataForm {

    @FXML
    public void addEmployee(ActionEvent event) {
        if(!(categoryCB.getSelectionModel().getSelectedItem() == null || categoryCB.getValue().equals("")) && categoryAssignmentDate.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка", "Введите дату получения категории.", "");
            return;
        }

        try {
            Global.setEmployee(new Employee(Validator.validateName(lastNameTF.getText(), lastNameTF), Validator.validateName(firstNameTF.getText(), firstNameTF),
                    Validator.validateName(patronymicTF.getText(), patronymicTF), Validator.validateDate(dobDate), phoneTF.getText(), addressTF.getText(),
                    StringToIntegerConverter.convert(salaryTF.getText()), Validator.validateDate(ppeDate), Validator.validateDate(hiringDate), positionCB.getValue(),
                    categoryCB.getValue(), categoryNumTF.getText(), Validator.validateDate(categoryAssignmentDate), Validator.validateDate(maternityStartDate),
                    Validator.validateDate(maternityEndDate), Validator.validateDate(fiveYearStartDate), Validator.validateDate(fiveYearEndDate),
                    StringToIntegerConverter.convert(childrenNumberTF.getText()), noteTF.getText()));

            if (DatabaseEmployeeController.addEmployee(Global.getEmployee())) {
                Global.setEmployeeList(DatabaseEmployeeController.allEmployeeList());
                MyAlert.showAndWait("INFORMATION", "", "Сотрудник " + Global.getEmployee().getFullName() + " добавлен!", "");
                EmployeePanel.showEmployeeAdd();
            }
        } catch (TextException | EmployeeException | SQLException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();
        super.initialize(url, resourceBundle);
        lastNameTF.requestFocus();
    }
}
