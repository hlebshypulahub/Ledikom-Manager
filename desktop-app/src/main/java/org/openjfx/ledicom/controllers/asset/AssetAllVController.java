package org.openjfx.ledicom.controllers.asset;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.ledicom.entities.Asset;
import org.openjfx.ledicom.entities.AssetType;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.database.DatabaseAssetController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.docs.AssetTableDoc;
import org.openjfx.utilities.panels.AssetPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssetAllVController implements Initializable {
    @FXML
    private TableView<Asset> table;
    @FXML
    private TableColumn<Asset, String> numberCol;
    @FXML
    private TableColumn<Asset, String> nameCol;
    @FXML
    private ComboBox<Facility> facilityCB;
    @FXML
    private ComboBox<AssetType> typeCB;
    @FXML
    private Button printButton;
    @FXML
    private Button deleteButton;

    ObservableList<Asset> observableList;

    @FXML
    void deleteAsset(ActionEvent event) throws SQLException, IOException {
        DatabaseAssetController.deleteAsset(table.getSelectionModel().getSelectedItem().getId());
        filterTable(event);
        deleteButton.setDisable(true);
    }

    @FXML
    void filterTable(ActionEvent event) throws SQLException {
        observableList = DatabaseAssetController.getAssets(facilityCB.getValue(), typeCB.getValue());
        table.setItems(observableList);
    }

    @FXML
    void printTable(ActionEvent event) throws IOException {
        AssetTableDoc.createTable(table.getItems(), typeCB.getValue(), facilityCB.getValue());
    }

    @FXML
    void reset(ActionEvent event) throws IOException {
        AssetPanel.showAllAssets();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));

        try {
            observableList = DatabaseAssetController.getAllAssets();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        table.setItems(observableList);
        table.setPlaceholder(new Label("Данные не найдены"));

        try {
            facilityCB.setItems(DatabaseFacilityController.allFacilityList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            typeCB.getItems().addAll(DatabaseAssetController.getAssetTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        table.setOnMouseClicked(event -> {
            deleteButton.setDisable(table.getSelectionModel().getSelectedIndex() < 0);
        });
    }
}
