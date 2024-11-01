package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.PeliculaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.Copia;
import com.example.models.Pelicula;
import com.example.models.dto.CopyDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
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

    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloPeli"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<>("estadoCopia"));
        tablaSoporte.setCellValueFactory(new PropertyValueFactory<>("soporteCopia"));
        CurrentSession.listDTOselected = userDAO.findAllUserCopies(CurrentSession.userSelected);
        tableCopies.getItems().clear();
        tableCopies.getItems().addAll(CurrentSession.listDTOselected);


    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }


}
