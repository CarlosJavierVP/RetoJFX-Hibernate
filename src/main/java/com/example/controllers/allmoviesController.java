package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.PeliculaDAO;
import com.example.models.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TableColumn <Pelicula, String>tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn <Pelicula, String> tablaGenero;
    @javafx.fxml.FXML
    private TableColumn <Pelicula, String> tablaFecha;
    @javafx.fxml.FXML
    private TableColumn <Pelicula, String> tablaDirector;
    @javafx.fxml.FXML
    private TextField search;
    private List<Pelicula> allMovies;
    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());

    ObservableList<Pelicula> filter = FXCollections.observableArrayList();
    FilteredList<Pelicula> filterMovie = new FilteredList<>(filter, p -> true); //Inicializar la lista filtrada
    @FXML
    private Button btnAddMovie;


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
            filter.add(peli);
        });

        tableMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            CurrentSession.movieSelected = newValue;
            GestorApp.loadFXML("views/detailmovie-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
        });

        search();

        if (CurrentSession.userSelected.getIsAdmin() == 1){
            btnAddMovie.setDisable(false);
        }

    }

    @FXML
    private void search() {
        //filtrar por películas
        tableMovies.setEditable(true);
        tableMovies.setItems(filter);

        search.textProperty().addListener((observable, oldValue, newValue) ->{
            filterMovie.setPredicate(filter ->{
                if (newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if(filter.getTitulo().toLowerCase().contains(searchKey)){
                    return true;
                } else if (filter.getGenero().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (filter.getAño().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (filter.getDirector().toLowerCase().contains(searchKey)) {
                    return true;
                }else {
                    return false; //no hay coincidencias
                }
            });
        });

        SortedList<Pelicula> busquedaOrdenada = new SortedList<>(filterMovie);
        busquedaOrdenada.comparatorProperty().bind(tableMovies.comparatorProperty()); //sincronización - la tabla muestra los datos sincronizados
        tableMovies.setItems(busquedaOrdenada); //aplicar filtro con los datos ordenados en la tabla
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

    @FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    @FXML
    public void addMovie(ActionEvent actionEvent) {

    }
}
