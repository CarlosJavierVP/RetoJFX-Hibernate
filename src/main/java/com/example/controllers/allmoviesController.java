package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.PeliculaDAO;
import com.example.models.Pelicula;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class allmoviesController implements Initializable {
    @javafx.fxml.FXML
    private TableView <Pelicula> tableMovies;
    @javafx.fxml.FXML
    private TableColumn <String, String>tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn <String, String> tablaGenero;
    @javafx.fxml.FXML
    private TableColumn <String, String> tablaFecha;
    @javafx.fxml.FXML
    private TableColumn <String, String> tablaDirector;
    private List<Pelicula> allMovies;
    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());



    @Deprecated
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        tablaGenero.setCellValueFactory(new PropertyValueFactory<>("Genero"));
        tablaFecha.setCellValueFactory(new PropertyValueFactory<>("Año"));
        tablaDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));

        allMovies = peliDAO.findAll();
        allMovies.forEach(peli ->{
            tableMovies.getItems().add(peli);
        });


        tableMovies.getSelectionModel().selectedItemProperty().addListener(e ->{

        });


    }


    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        GestorApp.loadFXML("views/loggin-view.fxml","Movie Pro Manager - Login");
    }


    @javafx.fxml.FXML
    public void btnMyCopies(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
}
