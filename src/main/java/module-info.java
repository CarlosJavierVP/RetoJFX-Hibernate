module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires lombok;
    requires java.naming;

    exports com.example.models;
    opens com.example.models;
    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
    opens com.example.views to javafx.fxml;
    exports com.example.dto;
    opens com.example.dto to javafx.fxml;
}