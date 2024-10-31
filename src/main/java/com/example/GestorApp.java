package com.example;

import com.example.dao.UsuarioDAO;
import com.example.dto.CopyDTO;
import com.example.models.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GestorApp extends Application {

    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/loggin-view.fxml","Movie Pro Manager - Login");
        stage.show();
    }

    public static void loadFXML(String view, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(GestorApp.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
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