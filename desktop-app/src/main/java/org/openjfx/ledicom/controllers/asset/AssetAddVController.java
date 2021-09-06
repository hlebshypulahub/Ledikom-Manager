package org.openjfx.ledicom.controllers.asset;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.openjfx.ledicom.entities.AssetType;
import org.openjfx.ledicom.entities.Facility;

import java.net.URL;
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
    void addAsset(ActionEvent event) {

    }

    @FXML
    void addNewType(ActionEvent event) {

    }

    @FXML
    void showNewTypeAdd(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
