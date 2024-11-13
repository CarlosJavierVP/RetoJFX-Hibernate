package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.PeliculaDAO;
import com.example.models.Pelicula;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {

    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Spinner<Integer> detailYear;
    @javafx.fxml.FXML
    private WebView idTeaser;
    @javafx.fxml.FXML
    private TextField detailGenre;
    @javafx.fxml.FXML
    private TextField detailTitle;
    @javafx.fxml.FXML
    private TextArea detailDescrip;
    @javafx.fxml.FXML
    private Button btnAddMovie;
    @javafx.fxml.FXML
    private TextField newUrl;
    @javafx.fxml.FXML
    private Button btnAddPoster;

    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,2024,1));

        addPoster();

    }

    private void addPoster() {
        btnAddPoster.setOnAction(e ->{
            FileChooser fc = new FileChooser();
            fc.setTitle("Añadir póster de la película");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos permitidos", "*.*"));

            File imgFile = fc.showOpenDialog(null);
            if (imgFile != null){
                Image newImg = new Image("covers:"+imgFile.getAbsolutePath());
                img.setImage(newImg);
            }

        });
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

    @javafx.fxml.FXML
    public void addMovie(ActionEvent actionEvent) {
        CurrentSession.movieSelected = new Pelicula();
        CurrentSession.movieSelected.setTitulo(detailTitle.getText());
        CurrentSession.movieSelected.setGenero(detailGenre.getText());
        CurrentSession.movieSelected.setAño(detailYear.getValue());
        CurrentSession.movieSelected.setDirector(detailDirector.getText());
        CurrentSession.movieSelected.setDescripcion(detailDescrip.getText());
        String urlCadena = newUrl.getText();
        CurrentSession.movieSelected.setTeaserUrl(urlCadena);
        CurrentSession.movieSelected.setImageUrl(String.valueOf(img));

        if (CurrentSession.movieSelected.getTitulo().isBlank() || CurrentSession.movieSelected.getGenero().isBlank()
                || CurrentSession.movieSelected.getDescripcion().isBlank() || CurrentSession.movieSelected.getDirector().isBlank()
                || CurrentSession.movieSelected.getImageUrl() == null || CurrentSession.movieSelected.getTeaserUrl() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Añadir película");
            alert.setContentText("Tiene que completar todos los campos");
            alert.show();
        }else{
            peliDAO.save(CurrentSession.movieSelected);
            detailTitle.clear();
            detailGenre.clear();
            detailYear.getValueFactory().setValue(2024);
            detailDirector.clear();
            detailDescrip.clear();
            newUrl.clear();
            GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
        }


    }

}
