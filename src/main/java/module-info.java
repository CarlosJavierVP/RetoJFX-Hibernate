module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires lombok;
    requires java.naming;
    requires javafx.web;
    requires java.sql;
    requires net.sf.jasperreports.core;

    exports com.example.services;
    opens com.example.services;
    exports com.example.models to javafx.fxml;
    opens com.example.models;
    opens com.example;
    exports com.example;
    exports com.example.controllers;
    opens com.example.controllers;
    opens com.example.views;
    exports com.example.models.dto;
    opens com.example.models.dto;
}