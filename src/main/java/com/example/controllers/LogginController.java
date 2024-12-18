package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.UsuarioDAO;
import com.example.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Clase LogginController controla la vista para la visualizacion del loggin, tanto para conectarse como para crear una cuenta nueva
 * @author Carlos Javier
 */
public class LogginController implements Initializable {
    @FXML
    private PasswordField pass;
    @FXML
    private TextField txtUser;
    @FXML
    private Label infoLabel;
    @FXML
    private Button btnAddUser;
    @FXML
    private TextField newUser;
    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField newPass2;
    @FXML
    private Label infoLabel1;
    UsuarioDAO dao = new UsuarioDAO(HibernateUtil.getSessionFactory());

    /**
     * metodo onConectar para conectarse a la cuenta
     * @param actionEvent
     */
    @FXML
    public void onConectar(ActionEvent actionEvent) {
        Usuario user = dao.validateUser(txtUser.getText(), pass.getText());

        if (user != null){
            infoLabel.setText("Usuario conectado");
            CurrentSession.userSelected = user;
            if (CurrentSession.userSelected.getIsAdmin() == 1){
                GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
            }else{
                GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
            }
        }else{
            infoLabel.setText("Usuario no encontrado");
        }
    }

    /**
     * Metodo onRegister para registrar un nuevo usuario
     * @param actionEvent
     */
    @FXML
    public void onRegister(ActionEvent actionEvent) {
        Usuario nuevoUsuario = dao.validateNewUser(newUser.getText());

        if(nuevoUsuario == null){
            if(!newPass2.getText().isBlank() && !newUser.getText().isBlank()){
                if(Arrays.equals(newPass.getText().toCharArray(), newPass2.getText().toCharArray())){
                    nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombreUsuario(newUser.getText());
                    nuevoUsuario.setPassword(newPass.getText());
                    CurrentSession.userSelected = nuevoUsuario;
                    CurrentSession.userSelected.setMisCopias(CurrentSession.listCopySelected = new ArrayList<>(0));
                    CurrentSession.userSelected.setIsAdmin((byte) 0);
                    dao.save(CurrentSession.userSelected);
                    infoLabel1.setText("Usuario registrado correctamente");
                    GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
                }else{
                    infoLabel1.setText("La contraseña no coincide");
                }
            }else {
                infoLabel1.setText("No deje los campos de registro vacíos");
            }

        }else{
            infoLabel1.setText("Ese nombre de usuario no está disponible");
        }
    }

    /**
     * Metodo onCancelar para cerrar la app
     * @param actionEvent
     */
    @FXML
    public void onCancelar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    /**
     * Metodo initialize que inicializa la ventana
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}