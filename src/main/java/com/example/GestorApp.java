package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestorApp extends Application {

    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/loggin-view.fxml","Film Pro Manager - Login");
        stage.show();
    }

    private static void loadFXML(String view, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(GestorApp.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 320, 380);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}