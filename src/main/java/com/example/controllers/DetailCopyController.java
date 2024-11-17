package com.example.controllers;

import com.example.CurrentSession;
import com.example.GestorApp;
import com.example.HibernateUtil;
import com.example.dao.CopiaDAO;
import com.example.dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Clase DetailCopyController controla la vista para visualizar el detalle de la copia
 * @author Carlos Javier
 */
public class DetailCopyController implements Initializable {
    @javafx.fxml.FXML
    private TextField detailDirector;
    @javafx.fxml.FXML
    private Button btnEditCopy;
    @javafx.fxml.FXML
    private ImageView img;
    @javafx.fxml.FXML
    private Spinner<Integer> detailYear;
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
    @javafx.fxml.FXML
    private Label idEstado;
    @javafx.fxml.FXML
    private Label idFormato;
    @javafx.fxml.FXML
    private Button btnDelete;
    CopiaDAO copyDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

    /**
     * Metodo initialize para inicializar la ventana y sus métodos
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        detailYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1975,2024,2024,0));

        titleCopy.setText(CurrentSession.copyDTOselected.getTituloPeli());
        idEstado.setText(CurrentSession.copyDTOselected.getEstadoCopia() );
        idFormato.setText(CurrentSession.copyDTOselected.getSoporteCopia());
        detailTitle.setText(CurrentSession.movieSelected.getTitulo());
        detailGenre.setText(CurrentSession.movieSelected.getGenero());
        detailYear.getValueFactory().setValue(CurrentSession.movieSelected.getAño());
        detailDirector.setText(CurrentSession.movieSelected.getDirector());
        detailDescrip.setText(CurrentSession.movieSelected.getDescripcion());
        try {
            Image imgPeli = new Image(new FileInputStream("covers/"+CurrentSession.movieSelected.getImageUrl()));
            img.setImage(imgPeli);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        idTeaser.getEngine().load(CurrentSession.movieSelected.getTeaserUrl());
    }

    /**
     * Metodo onBack para regresar a la ventana principal, el listado de copias del usuario
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }

    /**
     * Metodo cerrar para desconectar de la sesion y cerrar la app
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    /**
     * Metodo updateCopy del boton para actualizar los datos de la copia
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void updateCopy(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Actualizar copia");
        alert.setContentText("¿Desea modificar la copia "+CurrentSession.copyDTOselected.getTituloPeli()+"?");

        ChoiceBox<String> soportes = new ChoiceBox<>();
        soportes.getItems().addAll("DVD", "Blu-ray");
        soportes.setValue("DVD");
        ChoiceBox<String> estados = new ChoiceBox<>();
        estados.getItems().addAll("bueno", "dañado");
        estados.setValue("bueno");

        //Añadir los choiceBox a la alerta
        VBox contenedor = new VBox();
        HBox formato = new HBox();
        HBox condicion = new HBox();
        Label est = new Label("Estado");
        Label spt = new Label("Soporte");
        formato.getChildren().addAll(spt,soportes);
        condicion.getChildren().addAll(est,estados);

        contenedor.getChildren().addAll(condicion, formato);
        alert.getDialogPane().setContent(contenedor);

        ButtonType btnUpdate = new ButtonType("Actualizar Copia");
        ButtonType btnCancel = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().addAll(btnCancel, btnUpdate);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent()){
            if(resultado.get() == btnUpdate){
                String estadoCopia = estados.getValue();
                String soporteCopia = soportes.getValue();
                CurrentSession.copySelected = copyDAO.findById(CurrentSession.copyDTOselected.getIdMyCopy());
                CurrentSession.copySelected.setEstado(estadoCopia);
                CurrentSession.copySelected.setIdPelicula(CurrentSession.movieSelected.getIdPelicula());
                CurrentSession.copySelected.setSoporte(soporteCopia);
                CurrentSession.copySelected.setUser(CurrentSession.userSelected);
                copyDAO.update(CurrentSession.copySelected);
                GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
            }
        }
    }

    /**
     * Metodo deleteCopia del boton para eliminar la copia
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void deleteCopia(ActionEvent actionEvent) {
        CurrentSession.copySelected = copyDAO.findById(CurrentSession.copyDTOselected.getIdMyCopy());
        copyDAO.delete(CurrentSession.copySelected);
        CurrentSession.copySelected = null;
        GestorApp.loadFXML("views/main-view.fxml","Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
}
