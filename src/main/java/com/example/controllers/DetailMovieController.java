package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.UsuarioDAO;
import com.example.models.Copia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

/**
 * Clase DetailMovieController controla la vista para visualizar el detalle de la película de la base de datos
 * @author Carlos Javier
 */
public class DetailMovieController implements Initializable {
    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Label titleMovie;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;
    @javafx.fxml.FXML
    private Spinner<Integer> detailYear;
    @javafx.fxml.FXML
    private Button btnAddCopy;
    @FXML
    private WebView idTeaser;
    @FXML
    private Button btnVolver;
    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

    /**
     * Metodo initialize para inicializar la ventana y sus métodos
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,2024,0));
        detailTitle.setText(CurrentSession.movieSelected.getTitulo());
        titleMovie.setText(CurrentSession.movieSelected.getTitulo());
        detailGenre.setText(CurrentSession.movieSelected.getGenero());
        //detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,CurrentSession.movieSelected.getAño(),1));
        detailYear.getValueFactory().setValue(CurrentSession.movieSelected.getAño());
        detailDirector.setText(CurrentSession.movieSelected.getDirector());
        detailDescrip.setText(CurrentSession.movieSelected.getDescripcion());
        try {
            Image imgPeli = new Image(new FileInputStream("covers/"+CurrentSession.movieSelected.getImageUrl()));
            img.setImage(imgPeli);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        idTeaser.getEngine().load(CurrentSession.movieSelected.getTeaserUrl());

    }

    /**
     * Metodo addCopy del boton para añadir la película al listado de copias del usuario
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
        Alert alert = new Alert (Alert.AlertType.NONE);
        alert.setTitle("Añadir una copia");
        alert.setContentText("¿Desea añadir la película a tus copias?");

        ChoiceBox<String> soportes = new ChoiceBox<>();
        soportes.getItems().addAll("DVD", "Blu-ray");
        soportes.setValue("DVD");
        ChoiceBox<String> estados = new ChoiceBox<>();
        estados.getItems().addAll("bueno", "dañado");
        estados.setValue("bueno");

        VBox contenedor = new VBox();
        HBox formato = new HBox();
        HBox condicion = new HBox();
        Label est = new Label("Estado");
        Label spt = new Label("Soporte");
        formato.getChildren().addAll(spt,soportes);
        condicion.getChildren().addAll(est,estados);

        contenedor.getChildren().addAll(condicion, formato);
        alert.getDialogPane().setContent(contenedor);

        ButtonType btnSave = new ButtonType("Guardar Copia");
        ButtonType btnCancel = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(btnCancel, btnSave);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent()){
            if (resultado.get() == btnSave){
                String estadoCopia = estados.getValue();
                String soporteCopia = soportes.getValue();
                CurrentSession.copySelected = new Copia();
                CurrentSession.copySelected.setEstado(estadoCopia);
                CurrentSession.copySelected.setIdPelicula(CurrentSession.movieSelected.getIdPelicula());
                CurrentSession.copySelected.setSoporte(soporteCopia);
                CurrentSession.copySelected.setUser(CurrentSession.userSelected);
                CurrentSession.userSelected.addCopy(CurrentSession.copySelected); // *habría que ver si es necesario este método cuando se emplea el cascade
                //save copy y en cascade actualizar el usuario
                userDAO.update(CurrentSession.userSelected);
                CurrentSession.copySelected = null;
                CurrentSession.movieSelected = null;
                var name =CurrentSession.userSelected.getNombreUsuario();
                var pss = CurrentSession.userSelected.getPassword();
                CurrentSession.userSelected = null;
                CurrentSession.userSelected = userDAO.validateUser(name, pss);
                GestorApp.loadFXML("views/main-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
            }
        }
    }

    /**
     * Metodo onBack que regresa a la ventana de la visualizacion de todas las peliculas
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }

    /**
     * Metodo cerrar para desconectar la sesion y cerrar la app
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    /**
     * Metodo volver que regresa a la ventana de la visualizacion de todas las peliculas
     * @param actionEvent
     */
    @FXML
    public void volver(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
}
