package org.example.academiaa.controller;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.Academia;
import org.example.academiaa.modelo.Estudiante;
import org.example.academiaa.service.EstudianteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

/**
 * Controlador (capa "C" de MVC): traduce los eventos de la vista
 * {@code estudiante-view.fxml} en llamadas al {@link EstudianteService} y
 * refresca la tabla con el resultado. No contiene reglas de negocio: toda
 * validación de dominio vive en el servicio (SRP).
 */
public class EstudianteController {

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;
    @FXML private DatePicker dpFechaRegistro;
    @FXML private Label lblMensaje;

    @FXML private TableView<Estudiante> tablaEstudiantes;
    @FXML private TableColumn<Estudiante, String> colDocumento;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colTelefono;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, String> colEdad;
    @FXML private TableColumn<Estudiante, String> colFechaRegistro;

    private final EstudianteService estudianteService =
            new EstudianteService(Academia.obtenerInstancia().getEstudianteRepository());

    private final ObservableList<Estudiante> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colDocumento.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getDocumentoIdentidad()));
        colNombre.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getNombreCompleto()));
        colTelefono.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTelefono()));
        colCorreo.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getCorreoElectronico()));
        colEdad.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getEdad())));
        colFechaRegistro.setCellValueFactory(e -> new SimpleStringProperty(String.valueOf(e.getValue().getFechaRegistro())));

        tablaEstudiantes.setItems(datosTabla);
        dpFechaRegistro.setValue(LocalDate.now());
        refrescarTabla();
    }

    @FXML
    private void registrarEstudiante() {
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            estudianteService.registrar(
                    txtDocumento.getText().trim(),
                    txtNombre.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    edad,
                    dpFechaRegistro.getValue()
            );
            mostrarOk("Estudiante registrado correctamente.");
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException ex) {
            mostrarError("La edad debe ser un número entero válido.");
        } catch (ValidacionException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtDocumento.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        dpFechaRegistro.setValue(LocalDate.now());
    }

    private void refrescarTabla() {
        datosTabla.setAll(estudianteService.listarTodos());
    }

    private void mostrarOk(String mensaje) {
        lblMensaje.getStyleClass().removeAll("label-error");
        if (!lblMensaje.getStyleClass().contains("label-ok")) {
            lblMensaje.getStyleClass().add("label-ok");
        }
        lblMensaje.setText(mensaje);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.getStyleClass().removeAll("label-ok");
        if (!lblMensaje.getStyleClass().contains("label-error")) {
            lblMensaje.getStyleClass().add("label-error");
        }
        lblMensaje.setText(mensaje);
    }
}
