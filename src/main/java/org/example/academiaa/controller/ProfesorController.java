package org.example.academiaa.controller;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.Academia;
import org.example.academiaa.modelo.Profesor;
import org.example.academiaa.service.ProfesorService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class ProfesorController {

    @FXML private TextField txtIdentificacion;
    @FXML private TextField txtNombre;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtTarifa;
    @FXML private Label lblMensaje;

    @FXML private TableView<Profesor> tablaProfesores;
    @FXML private TableColumn<Profesor, String> colIdentificacion;
    @FXML private TableColumn<Profesor, String> colNombreProfesor;
    @FXML private TableColumn<Profesor, String> colIdiomaProfesor;
    @FXML private TableColumn<Profesor, String> colTelefonoProfesor;
    @FXML private TableColumn<Profesor, String> colTarifa;

    private final ProfesorService profesorService =
            new ProfesorService(Academia.obtenerInstancia().getProfesorRepository());

    private final ObservableList<Profesor> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdentificacion.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getIdentificacion()));
        colNombreProfesor.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNombre()));
        colIdiomaProfesor.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getIdiomaQueEnsenia()));
        colTelefonoProfesor.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTelefono()));
        colTarifa.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getTarifaPorSesion().toString()));

        tablaProfesores.setItems(datosTabla);
        refrescarTabla();
    }

    @FXML
    private void registrarProfesor() {
        try {
            profesorService.registrar(
                    txtIdentificacion.getText().trim(),
                    txtNombre.getText().trim(),
                    txtIdioma.getText().trim(),
                    txtTelefono.getText().trim(),
                    new BigDecimal(txtTarifa.getText().trim())
            );
            mostrarOk("Profesor registrado correctamente.");
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException ex) {
            mostrarError("La tarifa por sesión debe ser un valor numérico.");
        } catch (ValidacionException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtIdioma.clear();
        txtTelefono.clear();
        txtTarifa.clear();
    }

    private void refrescarTabla() {
        datosTabla.setAll(profesorService.listarTodos());
    }

    private void mostrarOk(String mensaje) {
        lblMensaje.getStyleClass().removeAll("label-error");
        if (!lblMensaje.getStyleClass().contains("label-ok")) lblMensaje.getStyleClass().add("label-ok");
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.getStyleClass().removeAll("label-ok");
        if (!lblMensaje.getStyleClass().contains("label-error")) lblMensaje.getStyleClass().add("label-error");
        lblMensaje.setText(mensaje);
    }
}
