package org.openjfx.ledicom.controllers.employee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.openjfx.utilities.database.DatabaseEmployeeController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EmployeeChildrenNumberDController implements Initializable {

    @FXML
    private Label childrenNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<String> data = new ArrayList<>();

        try {
            data = DatabaseEmployeeController.getAllChildrenData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        int count = 0;
        for (String r : data) {
            if (r != null && r.length() > 0) {
                count += Integer.parseInt(r.substring(0, 1));
            }
        }

        childrenNumber.setText(String.valueOf(count));

    }
}
