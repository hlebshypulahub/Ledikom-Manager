package org.openjfx.utilities;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.openjfx.ledicom.entities.Device;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;
import org.openjfx.ledicom.entities.inspection.Inspection;

public final class Global {

    private static final int APP_VERSION = 1;

    @FXML
    private static AnchorPane viewPane;
    @FXML
    private static AnchorPane detailsPane;
    @FXML
    private static Pane footerPane;

    private static Employee employee;
    private static Facility facility;
    private static Inspection inspection;
    private static Device device;

    private Global() {

    }

    public static Device getDevice() {
        return device;
    }

    public static void setDevice(Device device) {
        Global.device = device;
    }

    public static Facility getFacility() {
        return facility;
    }

    public static void setFacility(Facility facility) {
        Global.facility = facility;
    }

    public static Pane getFooterPane() {
        return footerPane;
    }

    public static void setFooterPane(Pane footerPane) {
        Global.footerPane = footerPane;
    }

    public static AnchorPane getDetailsPane() {
        return detailsPane;
    }

    public static void setDetailsPane(AnchorPane detailsPane) {
        Global.detailsPane = detailsPane;
    }

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        Global.employee = employee;
    }

    public static AnchorPane getViewPane() {
        return viewPane;
    }

    public static void setViewPane(AnchorPane viewPane) {
        Global.viewPane = viewPane;
    }

    public static int getAppVersion() {
        return APP_VERSION;
    }

    public static Inspection getInspection() {
        return inspection;
    }

    public static void setInspection(Inspection inspection) {
        Global.inspection = inspection;
    }
}
