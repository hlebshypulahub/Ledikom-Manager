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
import org.openjfx.utilities.database.DatabaseController;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.database.DatabaseInspectionController;
import org.openjfx.utilities.docs.InspectionDoc;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
        layoutY = 160;

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
                checkupPaneController.getCheckupAnswer().setValue("Да");
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
    }

    public void getDataFromDB() throws SQLException {
        Connection conn = DatabaseController.connect();
        checkupAnswers = DatabaseInspectionController.getCheckupAnswers(conn);
        checkupTypeList = DatabaseInspectionController.CheckupTypes(conn);
        checkupQuestionList = DatabaseInspectionController.CheckupQuestions(conn);
        conn.close();
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
        inspection.setDate(inspectionDate.getValue());

        Optional<Employee> employee = employeeList.stream().filter(e
                -> e.getFullName().equals(inspectionEmployee.getText())).findFirst();
        if(employee.isPresent()) {
            inspection.setIdEmployee(employee.get().getId());
        } else {
            MyAlert.showAndWait("ERROR", "", "Необходимо указать, кто заполнил форму!", "");
            return;
        }


        inspection.setNote(inspectionNote.getText());
        inspection.setIdFacility(Global.getFacility().getId());

        for (int i = 0; i < inspection.getCheckupList().size(); i++) {
            if (checkupPaneControllerList.get(i).getCheckupAnswer().getValue() == null) {
                MyAlert.showAndWait("ERROR", "", "Ответьте на все вопросы!", "");
            }

            inspection.getCheckupList().get(i).setAnswer(checkupPaneControllerList.get(i).getCheckupAnswer().getValue());
            inspection.getCheckupList().get(i).setNote(checkupPaneControllerList.get(i).getCheckupNote().getText());
            if (checkupPaneControllerList.get(i).getCheckupAnswer().getValue().equals("Нет")) {
                int finalI = i;
                inspection.getCheckupList().get(i).setViolation(new Violation(employeeList.stream().filter(e
                        -> e.getFullName().equals(checkupPaneControllerList.get(finalI).getViolationEmployee().getText())).findFirst().get().getId(),
                        checkupPaneControllerList.get(i).getViolationNote().getText(), checkupPaneControllerList.get(i).getCorrectionTerm().getValue(),
                        checkupPaneControllerList.get(i).getCorrectionDate().getValue()));
            }
        }

        if (DatabaseInspectionController.addInspection(inspection)) {
            MyAlert.showAndWait("INFORMATION", "", "Самоинспекция на \"" + Global.getFacility().getName() + "\" проведена!", "");
            InspectionDoc.createDocument(inspection);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inspectionLabel.setText(inspectionLabel.getText() + "\"" + Global.getFacility().getName() + "\"");

        employeeList = DatabaseEmployeeController.allEmployeeList();

        TextFields.bindAutoCompletion(inspectionEmployee, employeeList);

        try {
            setInspection();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}






















































