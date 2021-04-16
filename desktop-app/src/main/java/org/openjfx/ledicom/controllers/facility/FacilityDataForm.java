package org.openjfx.ledicom.controllers.facility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseEnumsController;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FacilityDataForm implements Initializable {

    @FXML
    protected TextField nameTF;
    @FXML
    protected ComboBox<String> statusCB;
    @FXML
    protected ComboBox<String> categoryCB;
    @FXML
    protected Button newStatusAddButton;
    @FXML
    protected TextField newStatusAddTF;
    @FXML
    protected Button newStatusShowButton;
    @FXML
    protected TextField scheduleTF;
    @FXML
    protected TextField cityTF;
    @FXML
    protected TextField addressTF;
    @FXML
    protected TextField phoneTF;
    @FXML
    protected TextField emailTF;

    @FXML
    void addNewStatus(ActionEvent event) {
        newStatusAddButton.setVisible(true);
        newStatusShowButton.setVisible(false);
        newStatusAddTF.setVisible(false);
        statusCB.getItems().clear();
        statusCB.getItems().addAll(Objects.requireNonNull(DatabaseEnumsController.addNewFacilityStatus(newStatusAddTF.getText())));
        statusCB.setValue(newStatusAddTF.getText());
    }

    @FXML
    void showNewStatusAdd(ActionEvent event) {
        newStatusAddButton.setVisible(false);
        newStatusShowButton.setVisible(true);
        newStatusAddTF.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusCB.getItems().addAll(DatabaseEnumsController.getFacilityStatuses());
        categoryCB.getItems().addAll(DatabaseEnumsController.getFacilityCategories());
        phoneTF.setTextFormatter(new TextFormatter<>(Validator.phoneValidationFormatter));
    }
}
