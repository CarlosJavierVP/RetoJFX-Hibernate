package com.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailCopyController implements Initializable {


    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private Button btnEditCopy;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Spinner detailYear;
    @javafx.fxml.FXML
    private WebView idTeaser;
    @javafx.fxml.FXML
    private Label titleCopy;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateCopy(ActionEvent actionEvent) {
    }
}
