module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires lombok;
    requires java.naming;


    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
    opens com.example.views to javafx.fxml;
}