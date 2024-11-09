package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.PeliculaDAO;
import com.example.models.Pelicula;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {

    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Spinner<Integer> detailYear;
    @javafx.fxml.FXML
    private WebView idTeaser;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;
    @javafx.fxml.FXML
    private Button btnAddMovie;

    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,2024,1));
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }

    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    @javafx.fxml.FXML
    public void addMovie(ActionEvent actionEvent) {
        CurrentSession.movieSelected = new Pelicula();
        CurrentSession.movieSelected.setTitulo(detailTitle.getText());
        CurrentSession.movieSelected.setGenero(detailGenre.getText());
        CurrentSession.movieSelected.setAño(detailYear.getValue());
        CurrentSession.movieSelected.setDirector(detailDirector.getText());
        CurrentSession.movieSelected.setDescripcion(detailDescrip.getText());
        try{
            Image imgPeli = new Image(new FileInputStream("covers/"+CurrentSession.movieSelected.getImageUrl()));
            //CurrentSession.movieSelected.setImageUrl(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



        peliDAO.save(CurrentSession.movieSelected);

        detailTitle.clear();
        detailGenre.clear();
        detailYear.getValueFactory().setValue(2024);
        detailDirector.clear();
        detailDescrip.clear();
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }

    @javafx.fxml.FXML
    public void addPoster(Event event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Añadir póster de la película");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos permitidos", "*.*"));


    }
}
