package com.example.controllers;

import com.example.CurrentSession;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailController implements Initializable {

    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private TextField detailYear;
    @javafx.fxml.FXML
    private Label titleMovie;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;
    @javafx.fxml.FXML
    private Button btnAddCopy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleMovie.setText(CurrentSession.movieSelected.getTitulo());
        detailTitle.setText(CurrentSession.movieSelected.getTitulo());
        detailGenre.setText(CurrentSession.movieSelected.getGenero());
        detailYear.setText(String.valueOf(CurrentSession.movieSelected.getAño()));
        detailDirector.setText(CurrentSession.movieSelected.getDirector());
        detailDescrip.setText(CurrentSession.movieSelected.getDescripcion());

        //Image imgPeli = new Image("/covers/"+CurrentSession.movieSelected.getImageUrl());
        //img.setImage(new Image("/covers/"+CurrentSession.movieSelected.getImageUrl()));

    }

    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
    }
}
