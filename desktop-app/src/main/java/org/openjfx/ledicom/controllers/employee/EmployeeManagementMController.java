package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.openjfx.utilities.docs.EmployeeInfoDoc;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementMController implements Initializable {

    @FXML
    public void createPersonalCard(ActionEvent event) throws Exception {
        EmployeeInfoDoc.createPersonalCard();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
