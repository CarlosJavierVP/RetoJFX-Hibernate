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
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Clase AddMovieController controla la vista para añadir películas a la base de datos
 * @author Carlos Javier
 */
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
    @javafx.fxml.FXML
    private Button btnCancelar;
    private File imgFile = null;
    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());


    /**
     * Metodo initialize que inicializa la ventana
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975, 2024, 2024, 1));
        addPoster();

    }

    /**
     * Metodo addPoster en el botón para añadir el poster a la nueva película
     */
    private void addPoster() {
        btnAddPoster.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Añadir póster de la película");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Todos los archivos permitidos", "*.*"));
            imgFile = fc.showOpenDialog(null);

            String cadena = String.valueOf(imgFile);
            String[] trozos = cadena.split("\\\\");
            String namePoster = trozos[trozos.length - 1];
            //Image newImg = addImage(namePoster, imgFile);
            img.setFitHeight(412);
            img.setFitWidth(290);
            btnAddPoster.getStyleClass().remove("btnImg");
            btnAddPoster.getStyleClass().add("btnNewImg");
            //se llama al método addImage para copiar el poster y ponerlo en directorio covers y setear la img de la película desde ahí
            img.setImage(addImage(namePoster, imgFile));
        });
    }

    /**
     * Metodo addImage para copiar la imagen seleccionada y ponerla en el directorio covers
     * @param namePoster
     * @param imgFile
     * @return
     */
    private Image addImage(String namePoster, File imgFile) {
        File f = new File("covers/" + namePoster);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Image newImg = null;
        if (imgFile != null) {
            //File f = fc.showSaveDialog(null);
            try {
                fis = new FileInputStream(imgFile);
                fos = new FileOutputStream(f, true);
                int caracter = fis.read();
                while (caracter != -1) {
                    fos.write(caracter);
                    caracter = fis.read();
                }
                newImg = new Image(new FileInputStream("covers/" + namePoster));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (newImg == null){
            GestorApp.loadFXML("views/addmovie-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
        }
        return newImg;
    }

    /**
     * Metodo para regresar a la ventana de todas las películas
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - " + CurrentSession.userSelected.getNombreUsuario());
    }

    /**
     * Metodo para cerrar la sesión de la app
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    /**
     * Metodo cancelar para no guardar los datos de la película y volver a la ventana de todas las películas
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - " + CurrentSession.userSelected.getNombreUsuario());
    }

    /**
     * Metodo addMovie para persistir la nueva película en la base de datos
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void addMovie(ActionEvent actionEvent) {
        CurrentSession.movieSelected = new Pelicula();
        CurrentSession.movieSelected.setTitulo(detailTitle.getText());
        CurrentSession.movieSelected.setGenero(detailGenre.getText());
        CurrentSession.movieSelected.setAño(detailYear.getValue());
        CurrentSession.movieSelected.setDirector(detailDirector.getText());
        CurrentSession.movieSelected.setDescripcion(detailDescrip.getText());
        String urlTeaser = embed(newUrl.getText());
        CurrentSession.movieSelected.setTeaserUrl(urlTeaser);
        if (imgFile != null){
            CurrentSession.movieSelected.setImageUrl(imgFile.getName());
        }


        if (CurrentSession.movieSelected.getTitulo().isBlank() || CurrentSession.movieSelected.getGenero().isBlank()
                || CurrentSession.movieSelected.getDescripcion().isBlank() || CurrentSession.movieSelected.getDirector().isBlank()
                || CurrentSession.movieSelected.getImageUrl() == null || CurrentSession.movieSelected.getTeaserUrl() == null ) {
            alertaError();
        } else {
            peliDAO.save(CurrentSession.movieSelected);
            detailTitle.clear();
            detailGenre.clear();
            detailYear.getValueFactory().setValue(2024);
            detailDirector.clear();
            detailDescrip.clear();
            newUrl.clear();
            GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - " + CurrentSession.userSelected.getNombreUsuario());
        }
    }

    /**
     * Metodo alertaError para lanzar las alertas
     */
    private void alertaError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Añadir película");
        alert.setContentText("Tiene que completar todos los campos");
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        alert.show();
    }

    /**
     * Metodo embed para embeber el link del teaser
     * @param url
     * @return
     */
    private String embed(String url) {
        String resultado= "";
        //Para hacer que el enlace del vídeo sea embebido
        if(url.isBlank()){
            resultado = null;
        }else{
            String idVideo = url.substring(url.indexOf("v=") + 2);
            if (idVideo.contains("&")) {
                idVideo = idVideo.substring(0, idVideo.indexOf("&"));
            }
            resultado = "https://www.youtube.com/embed/" + idVideo;
        }
        return resultado;
    }


}
