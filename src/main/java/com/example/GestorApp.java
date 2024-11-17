package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Metodo GestorApp donde se gestiona la visualizacion de la aplicacion
 * @author Carlos Javier
 */
public class GestorApp extends Application {
    private static Stage ventana;

    /**
     * Metodo start para inicializar la ventana
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/loggin-view.fxml","Movie Pro Manager - Login");
        stage.show();
    }

    /**
     * Metodo loadFXML para cargar los archivos fxml
     * @param view
     * @param title
     */
    public static void loadFXML(String view, String title){
        FXMLLoader fxmlLoader = new FXMLLoader(GestorApp.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 950, 700);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);
    }

    /**
     * Metodo main para el launch
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}