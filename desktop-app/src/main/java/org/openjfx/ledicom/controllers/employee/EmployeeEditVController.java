package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextFormatter;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.utilities.CheckPharmPosition;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.converters.StringToIntegerConverter;
import org.openjfx.utilities.converters.StringToLocalDateConverter;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeEditVController extends EmployeeDataForm {

    @FXML
    public void updateEmployee(ActionEvent event) throws IOException, SQLException {
        if (!(categoryCB.getSelectionModel().getSelectedItem() == null || categoryCB.getValue().equals("")) && categoryAssignmentDate.getValue() == null) {
            MyAlert.showAndWait("ERROR", "Ошибка", "Введите дату получения категории.", "");
            return;
        }

        Global.setEmployee(new Employee(Global.getEmployee().getId(), Validator.validateName(lastNameTF.getText(), lastNameTF), Validator.validateName(firstNameTF.getText(), firstNameTF),
                Validator.validateName(patronymicTF.getText(), patronymicTF), Validator.validateDate(dobDate), phoneTF.getText(), addressTF.getText(),
                StringToIntegerConverter.convert(salaryTF.getText()), Validator.validateDate(ppeDate), Validator.validateDate(hiringDate), positionCB.getValue(),
                categoryCB.getValue(), categoryNumTF.getText(), Validator.validateDate(categoryAssignmentDate), Validator.validateDate(maternityStartDate), Validator.validateDate(maternityEndDate),
                Validator.validateDate(fiveYearStartDate), Validator.validateDate(fiveYearEndDate), childrenNumberTF.getText() + " " + childrenDobTF.getText(), noteTF.getText()));
        if (DatabaseEmployeeController.updateEmployee()) {
            MyAlert.showAndWait("INFORMATION", "", "Сотрудник отредактирован.", "");

            Global.setEmployee(DatabaseEmployeeController.getEmployee(Global.getEmployee().getId()));

            EmployeePanel.showEmployeeDetails();
            EmployeePanel.showEmployeeEdit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        courseHoursTF.setTextFormatter(new TextFormatter<>(Validator.intValidationFormatter));
        if (!CheckPharmPosition.isPharm()) {
            courseVBox.setVisible(false);
        }
        lastNameTF.setText(Global.getEmployee().getLastName());
        firstNameTF.setText(Global.getEmployee().getFirstName());
        patronymicTF.setText(Global.getEmployee().getPatronymic());
        dobDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getDOB()));
        phoneTF.setText(Global.getEmployee().getPhone());
        addressTF.setText(Global.getEmployee().getAddress());
        salaryTF.setText(String.valueOf(Global.getEmployee().getSalary()));
        ppeDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getPPE()));
        hiringDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getHiringDate()));
        positionCB.setValue(Global.getEmployee().getPosition());
        categoryCB.setValue(Global.getEmployee().getCategory());
        categoryAssignmentDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getCategoryAssignmentDate()));
        categoryNumTF.setText(Global.getEmployee().getCategoryNum());
        maternityStartDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getMaternityStartDate()));
        maternityEndDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getMaternityEndDate()));
        fiveYearStartDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getFiveYearStart()));
        fiveYearEndDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getFiveYearEnd()));
        childrenNumberTF.setText(Global.getEmployee().getChildrenData() != null && Global.getEmployee().getChildrenData().length() > 1
                ? Global.getEmployee().getChildrenData().substring(0, 1) : "");
        childrenDobTF.setText(Global.getEmployee().getChildrenData() != null && Global.getEmployee().getChildrenData().length() > 1
                ? Global.getEmployee().getChildrenData().substring(2) : "");
        noteTF.setText(Global.getEmployee().getNote());

        Edu edu = DatabaseEmployeeController.getEmployeeEdu();
        assert edu != null;
        eduNameTF.setText(edu.getName());
        eduGraduationDate.setValue(StringToLocalDateConverter.convert(edu.getGraduationDate()));
    }
}
