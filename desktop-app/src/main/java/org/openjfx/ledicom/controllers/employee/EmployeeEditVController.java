package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.EmployeeContract;
import org.openjfx.utilities.CheckPharmPosition;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.converters.StringToIntegerConverter;
import org.openjfx.utilities.converters.StringToLocalDateConverter;
import org.openjfx.utilities.database.DatabaseCourseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseEnumsController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeEditVController extends EmployeeDataForm {

    @FXML
    private ListView<EmployeeContract> contractLV;
    @FXML
    private Pane contractFormPane;
    @FXML
    private ComboBox<String> contractTypeCB;
    @FXML
    private DatePicker contractStartDate;
    @FXML
    private DatePicker contractEndDate;

    @FXML
    public void updateEmployee(ActionEvent event) throws IOException, SQLException {
        if (!(categoryCB.getSelectionModel().getSelectedItem() == null || categoryCB.getValue().equals("")) && categoryAssignmentDate.getEditor().getText().isEmpty()) {
            MyAlert.showAndWait("ERROR", "Ошибка", "Введите дату получения категории.", "");
            return;
        }

        Global.setEmployee(new Employee(Global.getEmployee().getId(), Validator.validateName(lastNameTF.getText(), lastNameTF), Validator.validateName(firstNameTF.getText(), firstNameTF),
                Validator.validateName(patronymicTF.getText(), patronymicTF), Validator.validateDate(dobDate), phoneTF.getText(), addressTF.getText(),
                StringToIntegerConverter.convert(salaryTF.getText()), Validator.validateDate(ppeDate), Validator.validateDate(hiringDate), positionCB.getValue(),
                categoryCB.getValue(), categoryNumTF.getText(), Validator.validateDate(categoryAssignmentDate), Validator.validateDate(maternityStartDate), Validator.validateDate(maternityEndDate),
                Validator.validateDate(fiveYearStartDate), Validator.validateDate(fiveYearEndDate), Validator.validateDate(courseDeadlineDate),
                DatabaseCourseController.getRequiredCourseHours(Global.getEmployee().getPosition()) - StringToIntegerConverter.convert(courseHoursSumTF.getText()),
                childrenNumberCB.getValue() + (childrenDobTF.getText().length() > 0 ? " " + childrenDobTF.getText() : ""), noteTF.getText()));
        if (DatabaseEmployeeController.updateEmployee()) {
            MyAlert.showAndWait("INFORMATION", "", "Сотрудник отредактирован.", "");

            Global.setEmployee(DatabaseEmployeeController.getEmployee(Global.getEmployee().getId()));

            EmployeePanel.showEmployeeDetails();
            EmployeePanel.showEmployeeEdit();
        }
    }

    @FXML
    public void fillContractForm(MouseEvent event) {
        if (contractLV.getSelectionModel().getSelectedIndex() >= 0) {
            contractFormPane.setDisable(false);
            contractTypeCB.setValue(contractLV.getSelectionModel().getSelectedItem().getType());
            contractStartDate.setValue(StringToLocalDateConverter.convert(contractLV.getSelectionModel().getSelectedItem().getStartDate().equals("Не задано") ? null
                    : contractLV.getSelectionModel().getSelectedItem().getStartDate()));
            contractEndDate.setValue(StringToLocalDateConverter.convert(contractLV.getSelectionModel().getSelectedItem().getEndDate().equals("Не задано") ? null
                    : contractLV.getSelectionModel().getSelectedItem().getEndDate()));
        }
    }

    @FXML
    public void updateContract(ActionEvent event) throws IOException, SQLException {
        EmployeeContract contract = contractLV.getSelectionModel().getSelectedItem();

        contract.setType(contractTypeCB.getValue());
        contract.setStartDate(Validator.validateDate(contractStartDate));
        contract.setEndDate(Validator.validateDate(contractEndDate));

        DatabaseEmployeeController.updateContract(contract);

        EmployeePanel.showEmployeeDetails();
        EmployeePanel.showEmployeeEdit();
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
        courseDeadlineDate.setValue(StringToLocalDateConverter.convert(Global.getEmployee().getCourseDeadlineDate()));
        courseHoursSumTF.setText(String.valueOf(DatabaseCourseController.getRequiredCourseHours(Global.getEmployee().getPosition())
                - Global.getEmployee().getCourseHoursSum()));
        childrenNumberCB.setValue(Global.getEmployee().getChildrenData() != null && !Global.getEmployee().getChildrenData().equals("") ? Integer.parseInt(Global.getEmployee().getChildrenData().substring(0, 1)) : 0);
        childrenDobTF.setText(Global.getEmployee().getChildrenData() != null && Global.getEmployee().getChildrenData().length() > 1
                ? Global.getEmployee().getChildrenData().substring(2) : "");
        noteTF.setText(Global.getEmployee().getNote());

        Edu edu = DatabaseEmployeeController.getEmployeeEdu();
        assert edu != null;
        eduNameTF.setText(edu.getName());
        eduGraduationDate.setValue(StringToLocalDateConverter.convert(edu.getGraduationDate()));

        try {
            contractTypeCB.setItems(DatabaseEnumsController.getContractTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        contractLV.setItems(DatabaseEmployeeController.employeeContractList());
    }
}
