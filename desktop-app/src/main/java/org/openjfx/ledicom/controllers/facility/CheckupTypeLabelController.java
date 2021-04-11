package org.openjfx.ledicom.controllers.facility;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckupTypeLabelController implements Initializable {
    @FXML
    private Label checkupTypeLabel;
    private int height = 25;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Label getLabel() {
        return checkupTypeLabel;
    }

    public void setLabel(Label label) {
        this.checkupTypeLabel = label;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
