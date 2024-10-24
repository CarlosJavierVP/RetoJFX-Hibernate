package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LogginController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnLog;
    @FXML
    private PasswordField pass;
    @FXML
    private TextField txtUser;
    @FXML
    private Label infoLabel;

    private UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());
    @FXML
    public void onConectar(ActionEvent actionEvent) {
        Usuario user = new UsuarioDAO(userDAO.validateUser(txtUser.getText(), pass.getText());

        if (user != null){
            infoLabel.setText("Usuario conectado");
            CurrentSession.userSelected = user;
            CurrentSession.copySelected = null; // implementar en UsuarioDAO una lista de CopyDTO que devuelva todas las copias del usuario
            GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+user.getNombreUsuario());
        }else{
            infoLabel.setText("Usuario no encontrado");
        }

    }
    @FXML
    public void onCancelar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}