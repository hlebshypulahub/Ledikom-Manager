package org.openjfx.ledicom.controllers.facility;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.ledicom.entities.inspection.Inspection;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseFacilityController;
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
    private ComboBox<Facility> facilityCB;

    @FXML
    public void edit(ActionEvent event) throws SQLException, IOException {
        if (table.getSelectionModel().getSelectedIndex() >= 0) {
            Global.setInspection(DatabaseInspectionController.getInspection(table.getSelectionModel().getSelectedItem()));
            FacilityPanel.showInspectionAdd();
        }
    }

    @FXML
    public void filter(ActionEvent event) {
        try {
            ObservableList<Inspection> inspections = DatabaseInspectionController.getInspections(facilityCB.getValue());
            table.setItems(inspections);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facility"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        try {
            ObservableList<Inspection> inspections = DatabaseInspectionController.getInspections(facilityCB.getValue());
            table.setItems(inspections);

            facilityCB.setItems(DatabaseFacilityController.allFacilityList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
