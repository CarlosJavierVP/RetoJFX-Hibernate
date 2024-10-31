package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.CopiaDAO;
import com.example.dao.PeliculaDAO;
import com.example.dao.UsuarioDAO;
import com.example.dto.CopyDTO;
import com.example.models.Copia;
import com.example.models.Pelicula;
import com.example.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private TableView<Copia> tableCopies;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn<String, String> tablaEstado;
    @javafx.fxml.FXML
    private TableColumn<String, String> tablaSoporte;

    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaTitulo.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        tablaSoporte.setCellValueFactory(new PropertyValueFactory<>("Soporte"));
        List<CopyDTO> copiesDTO = userDAO.findAllUserCopies(CurrentSession.userSelected);

        /*
        List<Copia>copies = userDAO.findAllUserCopies(CurrentSession.userSelected);
        copies.forEach(c ->{
            tableCopies.getItems().add(c);
        });

         */

        copiesDTO.forEach(copies ->{
            tableCopies.getItems().add(copies.getCopia());
        });


    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
    }


}
