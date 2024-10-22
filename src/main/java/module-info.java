module com.example.retoconjunto2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;


    opens com.example to javafx.fxml;
    exports com.example;
}