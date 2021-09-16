module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls;
    requires org.postgresql.jdbc;
    requires org.apache.commons.io;
    requires javafx.web;

    opens org.openjfx.ledicom.controllers.employee to javafx.fxml;
    opens org.openjfx.ledicom.controllers.notifications to javafx.fxml;
    opens org.openjfx.ledicom.controllers to javafx.fxml;
    opens org.openjfx.ledicom.entities to javafx.base;
    opens org.openjfx.ledicom.controllers.facility to javafx.fxml;
    opens org.openjfx.ledicom.controllers.asset to javafx.fxml;
    opens org.openjfx.ledicom.controllers.device to javafx.fxml;
    opens org.openjfx.ledicom.entities.inspection to javafx.base;

    exports org.openjfx;
    opens org.openjfx;
}