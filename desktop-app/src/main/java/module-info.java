module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires org.kordamp.ikonli.remixicon;
    requires java.sql;
    requires java.desktop;
    requires docx4j.core;
    requires docx4j.openxml.objects;
    requires java.xml.bind;
    requires docx4j.export.fo;
    requires org.controlsfx.controls;
    requires org.postgresql.jdbc;
    requires org.apache.commons.io;

    opens org.openjfx.ledicom.controllers.employee to javafx.fxml;
    opens org.openjfx.ledicom.controllers.notifications to javafx.fxml;
    opens org.openjfx.ledicom.controllers to javafx.fxml;
    opens org.openjfx.ledicom.entities to javafx.base;
    opens org.openjfx.ledicom.controllers.facility to javafx.fxml;

    exports org.openjfx;
    opens org.openjfx;
}