package org.openjfx.ledicom.controllers.facility;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.openjfx.ledicom.controllers.interfaces.FacilityControllerInterface;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.panels.FacilityPanel;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class FacilityAllVController implements Initializable, FacilityControllerInterface {

    @FXML
    private TableView<Facility> table;
    @FXML
    private TableColumn<Facility, String> cityCol;
    @FXML
    private TableColumn<Facility, String> nameCol;
    @FXML
    private TableColumn<Facility, String> addressCol;
    @FXML
    private TableColumn<Facility, String> scheduleCol;

    @FXML
    void showFullInfo(MouseEvent event) throws IOException {
        showFacilityDetails(table);
        showFacilityManagement(table);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.getFooterPane().getChildren().clear();

        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        scheduleCol.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        table.setItems(DatabaseFacilityController.allFacilityList());

        nameCol.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Integer r1 = o1.replaceAll("\\D+", "").isEmpty() ? 0 : Integer.parseInt(o1.replaceAll("\\D+", ""));
                Integer r2 = o2.replaceAll("\\D+", "").isEmpty() ? 0 : Integer.parseInt(o2.replaceAll("\\D+", ""));
                return r1.compareTo(r2);
            }
        });

        table.setRowFactory(event -> {
            TableRow<Facility> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Global.setFacility(table.getSelectionModel().getSelectedItem());
                        FacilityPanel.showFacilityEdit();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
