package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.CopiaDAO;
import com.example.dao.UsuarioDAO;
import com.example.dto.CopyDTO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private TableView tableCopies;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaEstado;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaSoporte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tablaTitulo.setCellValueFactory(new PropertyValueFactory<CopyDTO, String>("titulo"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<CopyDTO, String>("estado"));
        tablaSoporte.setCellValueFactory(new PropertyValueFactory<CopyDTO, String>("soporte"));

        tableCopies.getSelectionModel().selectedItemProperty().addListener( (newValue) ->{
            if (newValue == null) return;
            CurrentSession.copySelected = (CopyDTO) newValue;
            GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+((CopyDTO) newValue).getPeli().getTitulo());
        } );
        tableRefresh();

    }

    //lazily por defecto
    private void tableRefresh(){
        tableCopies.getItems().clear();
        new UsuarioDAO(HibernateUtil.getSessionFactory()).findById(CurrentSession.userSelected.getId()).getMisCopias().forEach((copies)->{
            tableCopies.getItems().add(copies);
        });

    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onClose(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
    }
}
