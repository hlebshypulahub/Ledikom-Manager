package org.openjfx.ledicom.controllers.device;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.database.DatabaseEnumsController;
import org.openjfx.utilities.database.DatabaseFacilityController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeviceAddInterface implements Initializable {
    @FXML
    protected ComboBox<String> typeCB;
    @FXML
    protected Button newTypeAddButton;
    @FXML
    protected TextField newTypeAddTF;
    @FXML
    protected Button addNewTypeButton;
    @FXML
    protected TextField nameTF;
    @FXML
    protected TextField temperatureRangeTF;
    @FXML
    protected TextField numberTF;
    @FXML
    protected ComboBox<String> quaterCB;
    @FXML
    protected ComboBox<String> yearCB;
    @FXML
    protected TextField intervalTF;
    @FXML
    protected ComboBox<String> nextQuaterCB;
    @FXML
    protected ComboBox<String> nextYearCB;
    @FXML
    protected ComboBox<Facility> facilityCB;
    @FXML
    protected TextField locationTF;

    @FXML
    void addNewType(ActionEvent event) throws SQLException {
        newTypeAddButton.setVisible(true);
        addNewTypeButton.setVisible(false);
        newTypeAddTF.setVisible(false);
        typeCB.getItems().clear();
        typeCB.getItems().addAll(DatabaseEnumsController.addNewDeviceType(newTypeAddTF.getText()));
        typeCB.setValue(newTypeAddTF.getText());
    }

    @FXML
    void showNewTypeAdd(ActionEvent event) {
        newTypeAddTF.setVisible(true);
        addNewTypeButton.setVisible(true);
        newTypeAddButton.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Facility> observableList;
            observableList = DatabaseFacilityController.allFacilityList();
            observableList.add(0, new Facility("Резервные средства измерений", -1));
            facilityCB.setItems(observableList);
            typeCB.setItems(DatabaseEnumsController.getDeviceTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        quaterCB.setItems(FXCollections.observableArrayList("I кв.", "II кв.", "III кв.", "IV кв."));
        nextQuaterCB.setItems(FXCollections.observableArrayList("I кв.", "II кв.", "III кв.", "IV кв."));

        yearCB.setItems(FXCollections.observableArrayList("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));
        nextYearCB.setItems(FXCollections.observableArrayList("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"));
    }
}
