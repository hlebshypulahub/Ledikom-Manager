package org.openjfx.ledicom.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.openjfx.utilities.Global;
import org.openjfx.utilities.panels.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Pane footerPane;
    @FXML
    private AnchorPane viewPane;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private Menu managementMenu;
    @FXML
    private MenuItem managementMenuEmployee;

    @FXML
    public void employeeAdd(Event e) throws IOException {
        EmployeePanel.showEmployeeAdd();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void employeeAll(Event e) throws IOException {
        EmployeePanel.showAllEmployee();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void employeeCourses(Event e) throws IOException {
        EmployeePanel.showEmployeesCourses();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void facilityAdd(Event e) throws IOException {
        FacilityPanel.showFacilityAdd();
        FacilityPanel.showFacilityCharts();
    }

    @FXML
    public void facilityAll(Event e) throws IOException {
        FacilityPanel.showAllFacility();
        FacilityPanel.showFacilityCharts();
    }

    @FXML
    public void showDOBNotifications(ActionEvent e) throws IOException {
        NotificationPanel.showDobNotifications();
        footerPane.getChildren().clear();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void showContractNotifications(ActionEvent e) throws IOException {
        NotificationPanel.showContractNotifications();
        footerPane.getChildren().clear();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void showEmployeeTasks(ActionEvent e) throws IOException {
        EmployeePanel.showEmployeeTasks();
        footerPane.getChildren().clear();
        EmployeePanel.showEmployeeCharts();
    }

    @FXML
    public void showAssetAdd(ActionEvent e) throws IOException {
        AssetPanel.showAddAsset();
        footerPane.getChildren().clear();
        FacilityPanel.showFacilityCharts();
    }

    @FXML
    public void showAllAssets(ActionEvent e) throws IOException {
        AssetPanel.showAllAssets();
        footerPane.getChildren().clear();
        FacilityPanel.showFacilityCharts();
    }

    @FXML
    public void showAllInspections(ActionEvent e) throws IOException {
        FacilityPanel.showInspectionAll();
        footerPane.getChildren().clear();
        FacilityPanel.showFacilityCharts();
    }

    @FXML
    public void showAddDevice(ActionEvent e) throws IOException {
        FacilityPanel.showFacilityCharts();
        footerPane.getChildren().clear();
        DevicePanel.showAddDevice();
    }

    @FXML
    public void showAllDevices(ActionEvent e) throws IOException {
        FacilityPanel.showFacilityCharts();
        footerPane.getChildren().clear();
        detailsPane.getChildren().clear();
        DevicePanel.showAllDevices();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Global.setViewPane(viewPane);
        Global.setDetailsPane(detailsPane);
        Global.setFooterPane(footerPane);
    }
}
