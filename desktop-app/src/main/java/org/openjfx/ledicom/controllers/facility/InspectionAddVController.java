package org.openjfx.ledicom.controllers.facility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.inspection.*;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.converters.StringToLocalDateConverter;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseInspectionController;
import org.openjfx.utilities.docs.InspectionDoc;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class InspectionAddVController implements Initializable {

    @FXML
    private AnchorPane viewPane;
    @FXML
    private Label inspectionLabel;
    @FXML
    private DatePicker inspectionDate;
    @FXML
    private TextField inspectionEmployee;
    @FXML
    private TextField inspectionNote;
    @FXML
    private Button addButton;
    private ObservableList<Employee> employeeList;
    private ObservableList<String> checkupAnswers;
    private ObservableList<CheckupType> checkupTypeList;
    private ObservableList<Question> checkupQuestionList;
    private ObservableList<Checkup> checkupList;
    private ObservableList<Checkup> checkupResultList;
    private ObservableList<CheckupPaneController> checkupPaneControllerList;
    private Inspection inspection;
    int layoutY;
    int layoutX = 14;
    int padding = 10;

    public void setInspection() throws IOException, SQLException {
        layoutY = 220;

        getDataFromDB();
        prepareCheckupList();

        for (int i = 0; i < checkupTypeList.size(); i++) {
            int finalI = i;
            ObservableList<Checkup> checkupTempList = checkupList.stream().filter(checkup -> checkup.getQuestion().getCheckupType().getId() == checkupTypeList.get(finalI).getId())
                                                                 .collect(Collectors.toCollection(FXCollections::observableArrayList));

            FXMLLoader checkupTypeLabel = new FXMLLoader(getClass().getResource("/org/openjfx/ledicom/controllers/facility/checkupTypeLabel.fxml"));
            Parent root2 = checkupTypeLabel.load();
            CheckupTypeLabelController checkupTypeLabelController = checkupTypeLabel.getController();
            viewPane.getChildren().add(root2);
            viewPane.layout();
            viewPane.applyCss();

            checkupTypeLabelController.getLabel().setLayoutX(layoutX);
            checkupTypeLabelController.getLabel().setLayoutY(layoutY);
            layoutY += checkupTypeLabelController.getHeight() + padding;

            checkupTypeLabelController.getLabel().setText((i + 1) + ". " + checkupTypeList.get(i).getTypeName());

            for (int j = 0; j < checkupTempList.size(); j++) {
                FXMLLoader checkupPane = new FXMLLoader(getClass().getResource("/org/openjfx/ledicom/controllers/facility/checkupPane.fxml"));
                Parent root1 = checkupPane.load();
                CheckupPaneController checkupPaneController = checkupPane.getController();

                viewPane.getChildren().add(root1);
                viewPane.layout();
                viewPane.applyCss();

                checkupPaneController.getQuestionText().setText((i + 1) + "." + (j + 1) + " " + checkupTempList.get(j).getQuestion().getQuestion());
                checkupPaneController.getCheckupAnswer().setItems(checkupAnswers);
                checkupPaneController.getCheckupAnswer().setValue("????");
                TextFields.bindAutoCompletion(checkupPaneController.getViolationEmployee(), employeeList);

                checkupPaneController.getPane().setLayoutX(layoutX);
                checkupPaneController.getPane().setLayoutY(layoutY);
                layoutY += (int) (Math.max(checkupPaneController.getPane().getHeight(),
                        checkupPaneController.getQuestionText().getBoundsInLocal().getHeight())) + padding;

                checkupPaneControllerList.add(checkupPaneController);
                checkupResultList.add(checkupTempList.get(j));
            }
        }
        viewPane.setPrefHeight(layoutY + padding * 4);
        addButton.setLayoutX(layoutX);
        addButton.setLayoutY(layoutY);

        inspection = new Inspection(checkupResultList);
        inspection.setFacility(Global.getFacility());
    }

    public void setInspection(Inspection insp) throws SQLException, IOException {
        layoutY = 220;

        getDataFromDB();

        inspection = insp;

        checkupPaneControllerList = FXCollections.observableArrayList();
        checkupResultList = FXCollections.observableArrayList();

        for (int i = 0; i < checkupTypeList.size(); i++) {
            int finalI = i;
            ObservableList<Checkup> checkupTempList = inspection.getCheckupList().stream().filter(checkup -> checkup.getQuestion().getCheckupType().getId() == checkupTypeList.get(finalI).getId())
                                                                 .collect(Collectors.toCollection(FXCollections::observableArrayList));

            FXMLLoader checkupTypeLabel = new FXMLLoader(getClass().getResource("/org/openjfx/ledicom/controllers/facility/checkupTypeLabel.fxml"));
            Parent root2 = checkupTypeLabel.load();
            CheckupTypeLabelController checkupTypeLabelController = checkupTypeLabel.getController();
            viewPane.getChildren().add(root2);
            viewPane.layout();
            viewPane.applyCss();

            checkupTypeLabelController.getLabel().setLayoutX(layoutX);
            checkupTypeLabelController.getLabel().setLayoutY(layoutY);
            layoutY += checkupTypeLabelController.getHeight() + padding;

            checkupTypeLabelController.getLabel().setText((i + 1) + ". " + checkupTypeList.get(i).getTypeName());

            for (int j = 0; j < checkupTempList.size(); j++) {
                FXMLLoader checkupPane = new FXMLLoader(getClass().getResource("/org/openjfx/ledicom/controllers/facility/checkupPane.fxml"));
                Parent root1 = checkupPane.load();
                CheckupPaneController checkupPaneController = checkupPane.getController();

                viewPane.getChildren().add(root1);
                viewPane.layout();
                viewPane.applyCss();

                checkupPaneController.getQuestionText().setText((i + 1) + "." + (j + 1) + " " + checkupTempList.get(j).getQuestion().getQuestion());
                checkupPaneController.getCheckupAnswer().setItems(checkupAnswers);
                checkupPaneController.getCheckupAnswer().setValue(checkupTempList.get(j).getAnswer());
                checkupPaneController.getCheckupNote().setText(checkupTempList.get(j).getNote());

                if(checkupTempList.get(j).getViolation() != null) {
                    checkupPaneController.getViolationButton().setDisable(true);
                    checkupPaneController.getViolationVBox().setVisible(true);
                    checkupPaneController.getViolationEmployee().setText(checkupTempList.get(j).getViolation().getEmployee().getFullName());
                    checkupPaneController.getCorrectionTerm().setValue(StringToLocalDateConverter.convert(checkupTempList.get(j).getViolation().getCorrectionTerm()));
                    checkupPaneController.getCorrectionDate().setValue(StringToLocalDateConverter.convert(checkupTempList.get(j).getViolation().getCorrectionDate()));
                    checkupPaneController.getViolationDescription().setText(checkupTempList.get(j).getViolation().getDescription());
                    checkupPaneController.getViolationActionPlan().setText(checkupTempList.get(j).getViolation().getActionPlan());
                }

                TextFields.bindAutoCompletion(checkupPaneController.getViolationEmployee(), employeeList);

                checkupPaneController.getPane().setLayoutX(layoutX);
                checkupPaneController.getPane().setLayoutY(layoutY);
                layoutY += (int) (Math.max(checkupPaneController.getPane().getHeight(),
                        checkupPaneController.getQuestionText().getBoundsInLocal().getHeight())) + padding;

                checkupPaneControllerList.add(checkupPaneController);
                checkupResultList.add(checkupTempList.get(j));
            }
        }
        viewPane.setPrefHeight(layoutY + padding * 4);
        addButton.setLayoutX(layoutX);
        addButton.setLayoutY(layoutY);

        inspection.setCheckupList(checkupResultList);
    }

    public void getDataFromDB() throws SQLException {
        checkupAnswers = DatabaseInspectionController.getCheckupAnswers();
        checkupTypeList = DatabaseInspectionController.CheckupTypes();
        checkupQuestionList = DatabaseInspectionController.CheckupQuestions();
    }

    public void prepareCheckupList() {
        checkupList = FXCollections.observableArrayList();
        checkupResultList = FXCollections.observableArrayList();
        checkupPaneControllerList = FXCollections.observableArrayList();
        for (Question question : checkupQuestionList) {
            checkupList.add(new Checkup(question));
        }
    }

    public void addInspection() throws IOException {
        inspection.setDate(Validator.validateDate(inspectionDate));

        Optional<Employee> employee = employeeList.stream().filter(e
                -> e.getFullName().equals(inspectionEmployee.getText())).findFirst();
        if (employee.isPresent()) {
            inspection.setEmployee(employee.get());
        } else {
            MyAlert.showAndWait("ERROR", "", "???????????????????? ??????????????, ?????? ???????????????? ??????????!", "");
            inspectionEmployee.requestFocus();
            return;
        }

        if (inspectionDate.getValue() == null) {
            MyAlert.showAndWait("ERROR", "", "???????????????????? ?????????????? ???????? ???????????????????? ??????????????????????????!", "");
            inspectionDate.requestFocus();
            return;
        }

        inspection.setNote(inspectionNote.getText());
        inspection.setFacility(Global.getFacility() == null ? inspection.getFacility() : Global.getFacility());

        for (int i = 0; i < inspection.getCheckupList().size(); i++) {
            if (checkupPaneControllerList.get(i).getCheckupAnswer().getValue() == null) {
                MyAlert.showAndWait("ERROR", "", "???????????????? ???? ?????? ??????????????!", "");
            }

            inspection.getCheckupList().get(i).setAnswer(checkupPaneControllerList.get(i).getCheckupAnswer().getValue());
            inspection.getCheckupList().get(i).setNote(checkupPaneControllerList.get(i).getCheckupNote().getText());
            if (checkupPaneControllerList.get(i).getViolationButton().isDisable()) {
                int finalI = i;
                Optional<Employee> violationEmployee = employeeList.stream().filter(e
                        -> e.getFullName().equals(checkupPaneControllerList.get(finalI).getViolationEmployee().getText())).findFirst();
                if (violationEmployee.isPresent()) {
                    inspection.getCheckupList().get(i).setViolation(new Violation(violationEmployee.get().getId(), violationEmployee.get(),
                            checkupPaneControllerList.get(i).getViolationDescription().getText(), checkupPaneControllerList.get(i).getViolationActionPlan().getText(),
                            Validator.validateDate(checkupPaneControllerList.get(i).getCorrectionTerm()), Validator.validateDate(checkupPaneControllerList.get(i).getCorrectionDate())));
                } else {
                    MyAlert.showAndWait("ERROR", "", "???????????????????? ?????????????? ???????????????????????????? ?????? ???????????? ??????????????????!", "");
                    checkupPaneControllerList.get(i).getViolationEmployee().requestFocus();
                    return;
                }
            } else {
                inspection.getCheckupList().get(i).setViolation(null);
            }
        }

        if (DatabaseInspectionController.addInspection(inspection)) {
            MyAlert.showAndWait("INFORMATION", "", "?????????????????????????? ???? \"" + inspection.getFacility().getName() + "\" ??????????????????!", "");
            InspectionDoc.createDocument(inspection);
            Global.setInspection(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Global.getInspection() == null) {
            inspectionLabel.setText(inspectionLabel.getText() + "\"" + Global.getFacility().getName() + "\"");
        } else {
            inspectionLabel.setText(inspectionLabel.getText() + "\"" + Global.getInspection().getFacility() + "\"");
        }

        employeeList = DatabaseEmployeeController.allEmployeeList();

        TextFields.bindAutoCompletion(inspectionEmployee, employeeList);

        try {
            if(Global.getInspection() == null) {
                setInspection();
            } else {
                inspectionEmployee.setText(Global.getInspection().getEmployee().getFullName());
                inspectionNote.setText(Global.getInspection().getNote());
                inspectionDate.setValue(StringToLocalDateConverter.convert(Global.getInspection().getDate()));
                setInspection(Global.getInspection());
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}






















































