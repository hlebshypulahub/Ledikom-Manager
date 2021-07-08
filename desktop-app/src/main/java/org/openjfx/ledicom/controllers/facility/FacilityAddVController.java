package org.openjfx.ledicom.controllers.facility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.Validator;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.exceptions.EmployeeException;
import org.openjfx.utilities.exceptions.TextException;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FacilityAddVController extends FacilityDataForm {

    @FXML
    void addFacility(ActionEvent event) {
        try {
            Global.setFacility(new Facility(Validator.validateName(nameTF.getText(), nameTF), statusCB.getValue(), categoryCB.getValue(), scheduleTF.getText(),
                    cityTF.getText(), addressTF.getText(), phoneTF.getText(), emailTF.getText()));
            if (DatabaseFacilityController.addFacility(Global.getFacility())) {
                MyAlert.showAndWait("INFORMATION", "", "Объект " + Global.getFacility().getName() + " добавлен!", "");
                FacilityPanel.showFacilityAdd();
            }
        } catch (TextException | EmployeeException | SQLException | IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();
        super.initialize(url, resourceBundle);
        nameTF.requestFocus();
    }
}
