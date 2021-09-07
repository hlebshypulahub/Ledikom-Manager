package org.openjfx.ledicom.controllers.asset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.openjfx.ledicom.entities.Asset;
import org.openjfx.ledicom.entities.AssetType;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.utilities.MyAlert;
import org.openjfx.utilities.database.DatabaseAssetController;
import org.openjfx.utilities.database.DatabaseFacilityController;
import org.openjfx.utilities.panels.AssetPanel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AssetAddVController implements Initializable {

    @FXML
    private TextField nameTF;
    @FXML
    private ComboBox<AssetType> typeCB;
    @FXML
    private Button newTypeAddButton;
    @FXML
    private TextField newTypeAddTF;
    @FXML
    private Button addNewTypeButton;
    @FXML
    private ComboBox<Facility> facilityCB;


    @FXML
    void addAsset(ActionEvent event) throws SQLException, IOException {
        if (!(facilityCB.getValue() == null || typeCB.getValue() == null || nameTF.getText().isEmpty())) {
            Asset asset = DatabaseAssetController.addAsset(new Asset(facilityCB.getValue(), typeCB.getValue(), nameTF.getText()));
            MyAlert.showAndWait("INFORMATION", "Успешно!", "Инвентарный объект '" + asset.getName() + "' добавлен.\n" + "Инвентарный номер:\n" + asset.getNumber(), "Необходимо промаркировать объект.");
            AssetPanel.showAddAsset();
        } else {
            MyAlert.showAndWait("ERROR", "Ошибка!", "Необходимо заполнить все поля!", "");
        }
    }

    @FXML
    void addNewType(ActionEvent event) throws SQLException {
        newTypeAddButton.setVisible(true);
        newTypeAddTF.setVisible(false);
        addNewTypeButton.setVisible(false);
        typeCB.getItems().clear();
        typeCB.getItems().addAll(DatabaseAssetController.addNewType(newTypeAddTF.getText()));
    }

    @FXML
    void showNewTypeAdd(ActionEvent event) {
        newTypeAddButton.setVisible(false);
        newTypeAddTF.setVisible(true);
        addNewTypeButton.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeCB.getItems().addAll(DatabaseAssetController.getAssetTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            facilityCB.getItems().addAll(DatabaseFacilityController.allFacilityList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
