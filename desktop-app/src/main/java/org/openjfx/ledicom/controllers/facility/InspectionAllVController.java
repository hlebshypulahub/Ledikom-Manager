package org.openjfx.ledicom.controllers.facility;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.ledicom.entities.inspection.Inspection;
import org.openjfx.utilities.database.DatabaseInspectionController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InspectionAllVController implements Initializable {
    @FXML
    private TableView<Inspection> table;
    @FXML
    private TableColumn<Inspection, Facility> facilityCol;
    @FXML
    private TableColumn<Inspection, String> dateCol;
    @FXML
    private Button editButton;

    @FXML
    void edit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facility"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            ObservableList<Inspection> inspections = DatabaseInspectionController.getInspections();
            table.setItems(inspections);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.setOnMouseClicked(event -> {
            editButton.setDisable(table.getSelectionModel().getSelectedIndex() < 0);
        });
    }
}
