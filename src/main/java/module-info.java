module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires lombok;
    requires java.naming;
    requires javafx.web;

    exports com.example.models to javafx.fxml;
    opens com.example.models;
    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
    opens com.example.views to javafx.fxml;
    exports com.example.models.dto;
    opens com.example.models.dto to javafx.fxml;
}