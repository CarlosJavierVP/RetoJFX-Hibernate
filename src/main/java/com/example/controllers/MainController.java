package com.example.controllers;

import com.example.*;
import com.example.dao.PeliculaDAO;
import com.example.dao.UsuarioDAO;
import com.example.models.dto.CopyDTO;
import com.example.services.ReportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @javafx.fxml.FXML
    private TableView<CopyDTO> tableCopies;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaTitulo;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaEstado;
    @javafx.fxml.FXML
    private TableColumn<CopyDTO, String> tablaSoporte;
    @javafx.fxml.FXML
    private TextField search;
    @javafx.fxml.FXML
    private Button btnExportPDF;
    UsuarioDAO userDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());
    PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    ObservableList<CopyDTO> filter = FXCollections.observableArrayList();
    FilteredList<CopyDTO> filterMovie = new FilteredList<>(filter, p -> true); //Inicializar la lista filtrada

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablaTitulo.setCellValueFactory(new PropertyValueFactory<>("tituloPeli"));
        tablaEstado.setCellValueFactory(new PropertyValueFactory<>("estadoCopia"));
        tablaSoporte.setCellValueFactory(new PropertyValueFactory<>("soporteCopia"));
        CurrentSession.listDTOselected = userDAO.findAllUserCopies(CurrentSession.userSelected);
        CurrentSession.listDTOselected.forEach(copyDTO -> {
            tableCopies.getItems().add(copyDTO);
            filter.add(copyDTO);
        });

        tableCopies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            CurrentSession.copyDTOselected = newValue;
            CurrentSession.movieSelected = peliDAO.findByTitle(newValue.getTituloPeli());
            GestorApp.loadFXML("views/detailcopy-view.fxml","Movie Pro Manager - "+CurrentSession.userSelected.getNombreUsuario());
        });

        search();

    }

    @javafx.fxml.FXML
    private void search() {
        //filtrar por copiasDTO
        tableCopies.setEditable(true);
        tableCopies.setItems(filter);

        search.textProperty().addListener((observable, oldValue, newValue) ->{
            filterMovie.setPredicate(filter ->{
                if (newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKey = newValue.toLowerCase();

                if(filter.getTituloPeli().toLowerCase().contains(searchKey)){
                    return true;
                } else if (filter.getEstadoCopia().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (filter.getSoporteCopia().toLowerCase().contains(searchKey)) {
                    return true;
                }else {
                    return false; //no hay coincidencias
                }
            });
        });

        SortedList<CopyDTO> busquedaOrdenada = new SortedList<>(filterMovie);
        busquedaOrdenada.comparatorProperty().bind(tableCopies.comparatorProperty()); //sincronización - la tabla muestra los datos sincronizados
        tableCopies.setItems(busquedaOrdenada); //aplicar filtro con los datos ordenados en la tabla
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
    @javafx.fxml.FXML
    public void btnAllMovies(ActionEvent actionEvent) {
        GestorApp.loadFXML("views/allmovies-view.fxml", "Movie Pro Manager - "+ CurrentSession.userSelected.getNombreUsuario());
    }
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        System.exit(0);
    }

    @javafx.fxml.FXML
    public void ventanaLog(ActionEvent actionEvent) {
        CurrentSession.setParamsToNull();
        GestorApp.loadFXML("views/loggin-view.fxml","Movie Pro Manager - Login");
    }

    @FXML
    public void exportPDF(ActionEvent actionEvent) {
        ReportService rs = new ReportService(JdbcUtil.getCon());

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Informe de copias");
        alert.setContentText("Elige como quieres el informe");

        ChoiceBox<String> soportes = new ChoiceBox<>();
        soportes.getItems().addAll("DVD", "Blu-ray");
        soportes.setValue("DVD");
        ChoiceBox<String> estados = new ChoiceBox<>();
        estados.getItems().addAll("bueno","dañado");
        estados.setValue("bueno");

        RadioButton allCopies = new RadioButton("Por defecto");
        allCopies.setSelected(true);
        RadioButton estado = new RadioButton("Estado");
        RadioButton soporte = new RadioButton("Soporte");

        allCopies.setOnAction(e ->{
            if (allCopies.isSelected()){
                estado.setSelected(false);
                soporte.setSelected(false);
            }
        });
        estado.setOnAction(e ->{
            if (estado.isSelected()){
                allCopies.setSelected(false);
            }
        });
        soporte.setOnAction(e ->{
            if(soporte.isSelected()){
                allCopies.setSelected(false);
            }
        });

        VBox contenedor = new VBox();
        HBox condicion = new HBox();
        HBox formato = new HBox();

        condicion.getChildren().addAll(estado, estados);
        formato.getChildren().addAll(soporte,soportes);
        contenedor.getChildren().addAll(allCopies,condicion,formato);
        alert.getDialogPane().setContent(contenedor);

        ButtonType btnInforme = new ButtonType("Generar informe");
        ButtonType btnCancel = new ButtonType("Cancelar", ButtonType.CANCEL.getButtonData());
        alert.getButtonTypes().addAll(btnCancel,btnInforme);

        alert.getDialogPane().getContent().getStyleClass().add("/css/style.css");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent()){
            if (resultado.get() == btnInforme){
                if (allCopies.isSelected()){
                    rs.generarInformeCopias();
                }
                if (estado.isSelected() && soporte.isSelected()){
                    //el valor que se pasa es el del choiceBox
                    rs.informeCopiasSoporteEstado(soportes.getValue(), estados.getValue());
                } else if (estado.isSelected() && !soporte.isSelected()) {
                    rs.informeCopias(estados.getValue());
                } else if (!estado.isSelected() && soporte.isSelected()) {
                    rs.informeCopias(soportes.getValue());
                }

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Informe Copias");
                alert2.setContentText("El informe de tus copias ha sido creado");
                alert2.show();
            }
        }

    }
}
