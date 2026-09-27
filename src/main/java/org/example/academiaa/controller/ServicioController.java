package org.example.academiaa.controller;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.Academia;
import org.example.academiaa.modelo.ServicioAdicional;
import org.example.academiaa.service.ServicioAdicionalService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class ServicioController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private CheckBox chkDisponible;
    @FXML private Label lblMensaje;

    @FXML private TableView<ServicioAdicional> tablaServicios;
    @FXML private TableColumn<ServicioAdicional, String> colCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colNombreServicio;
    @FXML private TableColumn<ServicioAdicional, String> colDescripcion;
    @FXML private TableColumn<ServicioAdicional, String> colPrecio;
    @FXML private TableColumn<ServicioAdicional, String> colDisponible;

    private final ServicioAdicionalService servicioService =
            new ServicioAdicionalService(Academia.obtenerInstancia().getServicioRepository());

    private final ObservableList<ServicioAdicional> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getCodigo()));
        colNombreServicio.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getNombre()));
        colDescripcion.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getDescripcion()));
        colPrecio.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().getPrecio().toString()));
        colDisponible.setCellValueFactory(s -> new SimpleStringProperty(s.getValue().isDisponible() ? "Sí" : "No"));

        tablaServicios.setItems(datosTabla);
        refrescarTabla();
    }

    @FXML
    private void registrarServicio() {
        try {
            servicioService.registrar(
                    txtCodigo.getText().trim(),
                    txtNombre.getText().trim(),
                    txtDescripcion.getText().trim(),
                    new BigDecimal(txtPrecio.getText().trim()),
                    chkDisponible.isSelected()
            );
            mostrarOk("Servicio registrado correctamente.");
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException ex) {
            mostrarError("El precio debe ser un valor numérico.");
        } catch (ValidacionException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        chkDisponible.setSelected(true);
    }

    private void refrescarTabla() {
        datosTabla.setAll(servicioService.listarTodos());
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
