package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.openjfx.ledicom.entities.Course;
import org.openjfx.ledicom.entities.Edu;
import org.openjfx.utilities.CheckPharmPosition;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseCourseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseEnumsController;
import org.openjfx.utilities.panels.EmployeePanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeDataForm implements Initializable {

    @FXML
    protected TextField lastNameTF;
    @FXML
    protected TextField firstNameTF;
    @FXML
    protected TextField patronymicTF;
    @FXML
    protected DatePicker dobDate;
    @FXML
    protected TextField phoneTF;
    @FXML
    protected TextField addressTF;
    @FXML
    protected ComboBox<String> positionCB;
    @FXML
    protected ComboBox<String> categoryCB;
    @FXML
    protected TextField categoryNumTF;
    @FXML
    protected TextField salaryTF;
    @FXML
    protected DatePicker ppeDate;
    @FXML
    protected TextField eduNameTF;
    @FXML
    protected DatePicker eduGraduationDate;
    @FXML
    protected DatePicker categoryAssignmentDate;
    @FXML
    protected DatePicker hiringDate;
    @FXML
    protected DatePicker courseDeadlineDate;
    @FXML
    protected Button newPositionAddButton;
    @FXML
    protected TextField newPositionAddTF;
    @FXML
    protected Button addNewPositionButton;
    @FXML
    protected VBox courseVBox;
    @FXML
    protected TextField courseNameTF;
    @FXML
    protected TextField courseDescriptionTF;
    @FXML
    protected DatePicker courseStartDate;
    @FXML
    protected DatePicker courseEndDate;
    @FXML
    protected TextField courseHoursTF;
    @FXML
    protected DatePicker maternityStartDate;
    @FXML
    protected DatePicker maternityEndDate;
    @FXML
    protected DatePicker fiveYearStartDate;
    @FXML
    protected DatePicker fiveYearEndDate;
    @FXML
    protected TextField courseHoursSumTF;
    @FXML
    protected ComboBox<Integer> childrenNumberCB;
    @FXML
    protected TextField childrenDobTF;
    @FXML
    protected TextField noteTF;

    @FXML
    public void showNewPositionAdd(ActionEvent event) {
        newPositionAddButton.setVisible(false);
        addNewPositionButton.setVisible(true);
        newPositionAddTF.setVisible(true);
    }

    @FXML
    public void addNewPosition(ActionEvent event) throws SQLException {
        newPositionAddButton.setVisible(true);
        addNewPositionButton.setVisible(false);
        newPositionAddTF.setVisible(false);
        positionCB.getItems().clear();
        positionCB.getItems().addAll(DatabaseEnumsController.addNewEmployeePosition(newPositionAddTF.getText()));
        positionCB.setValue(newPositionAddTF.getText());
    }

    @FXML
    public void addCourse(ActionEvent event) throws IOException {
        if (courseStartDate.getEditor().getText().equals("") || courseEndDate.getEditor().getText().equals("")) {
            MyAlert.showAndWait("ERROR", "????????????", "?????????????? ???????? ????????????", "");
        } else {
            try {
                DatabaseCourseController.addCourse(new Course(courseNameTF.getText(), courseDescriptionTF.getText(), Integer.parseInt(courseHoursTF.getText()),
                        Validator.validateDate(courseStartDate), Validator.validateDate(courseEndDate)));
                MyAlert.showAndWait("INFORMATION", "", "???????? ????????????????.", "?????????? ?????????????????????? ???????????? ?? ???????? ?????????????????? ????????????????????????????.");
                courseNameTF.clear();
                courseDescriptionTF.clear();
                courseHoursTF.clear();
                courseStartDate.setValue(null);
                courseEndDate.setValue(null);

                Global.setEmployee(DatabaseEmployeeController.getEmployee(Global.getEmployee().getId()));
                EmployeePanel.showEmployeeDetails();
                EmployeePanel.showEmployeeEdit();
            } catch (SQLException e) {
                MyAlert.showAndWait("ERROR", "", "???????????? ???????? ????????????", e.getMessage().split("??????:")[0]);
            }
        }
    }

    @FXML
    public void addEdu(ActionEvent event) throws IOException, SQLException {
        if (eduGraduationDate.getEditor().getText().equals("")) {
            MyAlert.showAndWait("ERROR", "????????????", "?????????????? ???????? ?????????????????? ????????????????????", "");
        } else {
            DatabaseEmployeeController.addEdu(new Edu(eduNameTF.getText(), Validator.validateDate(eduGraduationDate)));
            if (CheckPharmPosition.isPharm()) {
                MyAlert.showAndWait("INFORMATION", "", "?????????????????????? ??????????????????.", "?????????? ?????????????????????? ???????????? ?????????????????? ????????????????????????????.");
            } else {
                MyAlert.showAndWait("INFORMATION", "", "?????????????????????? ??????????????????.", "");
            }
            eduNameTF.clear();
            eduGraduationDate.setValue(null);

            Global.setEmployee(DatabaseEmployeeController.getEmployee(Global.getEmployee().getId()));

            EmployeePanel.showEmployeeDetails();
            EmployeePanel.showEmployeeEdit();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lastNameTF.setTextFormatter(new TextFormatter<>(Validator.nameValidationFormatter));
        firstNameTF.setTextFormatter(new TextFormatter<>(Validator.nameValidationFormatter));
        patronymicTF.setTextFormatter(new TextFormatter<>(Validator.nameValidationFormatter));
        salaryTF.setTextFormatter(new TextFormatter<>(Validator.intValidationFormatter));
        phoneTF.setTextFormatter(new TextFormatter<>(Validator.phoneValidationFormatter));
        try {
            positionCB.getItems().addAll(DatabaseEnumsController.getEmployeePositions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            categoryCB.getItems().addAll(DatabaseEnumsController.getEmployeeCategories());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        childrenNumberCB.getItems().addAll(0,1,2,3,4,5,6,7,8,9);
        childrenNumberCB.setValue(0);
    }
}
