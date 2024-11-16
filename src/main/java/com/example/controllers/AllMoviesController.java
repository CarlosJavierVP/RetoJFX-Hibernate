package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.JdbcUtil;
import com.example.dao.PeliculaDAO;
import com.example.models.Pelicula;
import com.example.services.ReportService;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class AllMoviesController implements Initializable {
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
    @FXML
    private Button btnAddMovie;
    @FXML
    private Button btnExportPDF;
    private List<Pelicula> allMovies;
    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());

    ObservableList<Pelicula> filter = FXCollections.observableArrayList();
    FilteredList<Pelicula> filterMovie = new FilteredList<>(filter, p -> true); //Inicializar la lista filtrada


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
            btnAddMovie.setVisible(true);
            btnExportPDF.setDisable(false);
            btnExportPDF.setVisible(true);
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
        CurrentSession.movieSelected = null;
        GestorApp.loadFXML("views/addmovie-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
    }

    @FXML
    public void exportPDF(ActionEvent actionEvent) {
        ReportService rs = new ReportService(JdbcUtil.getCon());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Informe Películas");
        alert.setHeaderText("Generar archivo pdf");
        alert.setContentText("¿Quieres exportar el listado de películas a un archivo pdf?");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Optional<ButtonType> resultado = alert.showAndWait();

        if(resultado.isPresent()){
            if(resultado.get() == ButtonType.OK){
                rs.generarInformePeliculas();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Informe generado");
                alert2.setContentText("Archivo pdf creado correctamente");

                alert2.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                alert2.show();
            }
        }
    }
}
