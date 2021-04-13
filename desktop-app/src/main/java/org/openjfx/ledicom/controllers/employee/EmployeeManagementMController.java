package org.openjfx.ledicom.controllers.employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseEmployeeController;
import org.openjfx.utilities.docs.EmployeeInfoDoc;
import org.openjfx.utilities.panels.EmployeePanel;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManagementMController implements Initializable {

    @FXML
    public void createPersonalCard(ActionEvent event) throws Exception {
        EmployeeInfoDoc.createPersonalCard();
    }

    @FXML
    public void deleteEmployee(ActionEvent event) throws Exception {

        if (MyAlert.showAndWaitWarning("", "Вы уыерены, что хотите удалить сотрудника " + Global.getEmployee().getShortName() + " ?", "")) {
            if (DatabaseEmployeeController.deleteEmployee()) {
                MyAlert.showAndWait("INFORMATION", "", "Сотрудник " + Global.getEmployee().getShortName() + " удалён!", "");
            }
            Global.setEmployee(null);
            EmployeePanel.showAllEmployee();
            Global.getDetailsPane().getChildren().clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
