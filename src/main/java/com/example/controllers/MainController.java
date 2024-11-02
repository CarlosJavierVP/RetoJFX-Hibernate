package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.UsuarioDAO;
import com.example.models.Pelicula;
import com.example.models.dto.CopyDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private TableView<CopyDTO> tableCopies;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaEstado;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaSoporte;
    @javafx.fxml.FXML
    private TextField search;

    ObservableList<CopyDTO> filter = FXCollections.observableArrayList();
    FilteredList<CopyDTO> filterMovie = new FilteredList<>(filter, p -> true); //Inicializar la lista filtrada

    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloPeli"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<>("estadoCopia"));
        tablaSoporte.setCellValueFactory(new PropertyValueFactory<>("soporteCopia"));
        CurrentSession.listDTOselected = userDAO.findAllUserCopies(CurrentSession.userSelected);
        tableCopies.getItems().clear();
        tableCopies.getItems().addAll(CurrentSession.listDTOselected);

        search();
    }

    private void search() {
        //filtrar por películas
        tableCopies.setEditable(true);
        tableCopies.setItems(filter);

        search.textProperty().addListener((observable, oldValue, newValue) ->{
            filterMovie.setPredicate(filter ->{
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if(filter.getTituloPeli().toLowerCase().contains(searchKey)){
                    return true;
                } else if (filter.getEstadoCopia().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (filter.getSoporteCopia().toLowerCase().contains(searchKey)) {
                    return true;
                }else {
                    return false; //no hay coincidencias
                }
            });
        });

        SortedList<CopyDTO> busquedaOrdenada = new SortedList<>(filterMovie);
        busquedaOrdenada.comparatorProperty().bind(tableCopies.comparatorProperty()); //sincronización - la tabla muestra los datos sincronizados
        tableCopies.setItems(busquedaOrdenada); //aplicar filtro con los datos ordenados en la tabla
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
    @javafx.fxml.FXML
    public void btnAllMovies(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }
}
