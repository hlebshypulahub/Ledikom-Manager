package org.openjfx.utilities;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.openjfx.ledicom.entities.Employee;
import org.openjfx.ledicom.entities.Facility;

public final class Global {

    @FXML
    private static AnchorPane viewPane;
    @FXML
    private static AnchorPane detailsPane;
    @FXML
    private static Pane footerPane;

    private static Employee employee;
    private static Facility facility;

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
}
