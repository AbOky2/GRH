module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml; // Ensure javafx-fxml module is part of the project dependencies

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.bootstrapfx.core;
    requires java.naming;

    requires java.net.http;
    requires java.sql;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.controller;
}