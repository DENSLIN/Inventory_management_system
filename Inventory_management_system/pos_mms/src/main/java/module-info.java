module com.project.pos_mms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires tablesaw.core;
    requires joinery.dataframe;
    requires com.opencsv;
    requires java.desktop;
    requires java.sql;

    opens com.project.pos_mms to javafx.fxml;
    exports com.project.pos_mms;
}