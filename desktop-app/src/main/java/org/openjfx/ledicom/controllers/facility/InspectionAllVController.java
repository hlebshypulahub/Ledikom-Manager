package org.openjfx.ledicom.controllers.facility;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.ledicom.entities.inspection.Inspection;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseInspectionController;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
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
    public void edit(ActionEvent event) throws SQLException, IOException {
        Global.setInspection(DatabaseInspectionController.getInspection(table.getSelectionModel().getSelectedItem()));
        FacilityPanel.showInspectionAdd();
    }

    @FXML
    public void enableButton(MouseEvent event) {
        if (table.getSelectionModel().getSelectedIndex() >= 0)
            editButton.setDisable(false);
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
    }
}
