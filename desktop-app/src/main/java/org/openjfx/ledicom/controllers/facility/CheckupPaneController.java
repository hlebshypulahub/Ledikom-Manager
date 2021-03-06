package org.openjfx.ledicom.controllers.facility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckupPaneController implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Text questionText;
    @FXML
    private ComboBox<String> checkupAnswer;
    @FXML
    private TextField checkupNote;
    @FXML
    private VBox violationVBox;
    @FXML
    private TextField violationEmployee;
    @FXML
    private DatePicker correctionTerm;
    @FXML
    private DatePicker correctionDate;
    @FXML
    private TextField violationDescription;
    @FXML
    private Button violationButton;
    @FXML
    private TextField violationActionPlan;

    @FXML
    public void addViolation(ActionEvent event) {
        violationVBox.setVisible(true);
        violationButton.setDisable(true);
    }

    @FXML
    public void cancelViolation(ActionEvent event) {
        violationVBox.setVisible(false);
        violationButton.setDisable(false);
    }

    public TextField getViolationActionPlan() {
        return violationActionPlan;
    }

    public void setViolationActionPlan(TextField violationActionPlan) {
        this.violationActionPlan = violationActionPlan;
    }

    public Button getViolationButton() {
        return violationButton;
    }

    public void setViolationButton(Button violationButton) {
        this.violationButton = violationButton;
    }

    public ComboBox<String> getCheckupAnswer() {
        return checkupAnswer;
    }

    public void setCheckupAnswer(ComboBox<String> checkupAnswer) {
        this.checkupAnswer = checkupAnswer;
    }

    public TextField getCheckupNote() {
        return checkupNote;
    }

    public void setCheckupNote(TextField checkupNote) {
        this.checkupNote = checkupNote;
    }

    public VBox getViolationVBox() {
        return violationVBox;
    }

    public void setViolationVBox(VBox violationVBox) {
        this.violationVBox = violationVBox;
    }

    public TextField getViolationEmployee() {
        return violationEmployee;
    }

    public void setViolationEmployee(TextField violationEmployee) {
        this.violationEmployee = violationEmployee;
    }

    public DatePicker getCorrectionTerm() {
        return correctionTerm;
    }

    public void setCorrectionTerm(DatePicker correctionTerm) {
        this.correctionTerm = correctionTerm;
    }

    public DatePicker getCorrectionDate() {
        return correctionDate;
    }

    public void setCorrectionDate(DatePicker correctionDate) {
        this.correctionDate = correctionDate;
    }

    public TextField getViolationDescription() {
        return violationDescription;
    }

    public void setViolationDescription(TextField violationDescription) {
        this.violationDescription = violationDescription;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Text getQuestionText() {
        return questionText;
    }

    public void setQuestionText(Text questionText) {
        this.questionText = questionText;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
