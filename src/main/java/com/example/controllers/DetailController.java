package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailController implements Initializable {

    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Label titleMovie;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;
    @javafx.fxml.FXML
    private Spinner<Integer> detailYear;
    @javafx.fxml.FXML
    private Button btnAddCopy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,2024,1));

        titleMovie.setText(CurrentSession.movieSelected.getTitulo());
        detailTitle.setText(CurrentSession.movieSelected.getTitulo());
        detailGenre.setText(CurrentSession.movieSelected.getGenero());
        //detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,CurrentSession.movieSelected.getAño(),1));
        detailYear.getValueFactory().setValue(CurrentSession.movieSelected.getAño());
        detailDirector.setText(CurrentSession.movieSelected.getDirector());
        detailDescrip.setText(CurrentSession.movieSelected.getDescripcion());
        try {
            Image imgPeli = new Image(new FileInputStream("covers/"+CurrentSession.movieSelected.getImageUrl()));
            img.setImage(imgPeli);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }

    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }
}
