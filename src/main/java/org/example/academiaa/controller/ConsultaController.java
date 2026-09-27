package org.example.academiaa.controller;

import org.example.academiaa.exception.EntidadNoEncontradaException;
import org.example.academiaa.modelo.Academia;
import org.example.academiaa.modelo.Estudiante;
import org.example.academiaa.service.EstudianteService;
import org.example.academiaa.service.ReporteService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

/**
 * Controlador de las dos consultas exigidas por el enunciado:
 * búsqueda de un estudiante por documento y cálculo de ingresos por periodo.
 */
public class ConsultaController {

    @FXML private TextField txtDocumentoBuscar;
    @FXML private Label lblResultadoBusqueda;

    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private Label lblIngresos;

    private final EstudianteService estudianteService =
            new EstudianteService(Academia.obtenerInstancia().getEstudianteRepository());
    private final ReporteService reporteService =
            new ReporteService(Academia.obtenerInstancia().getMatriculaRepository());

    @FXML
    private void buscarEstudiante() {
        try {
            Estudiante estudiante = estudianteService.buscarPorDocumento(txtDocumentoBuscar.getText().trim());
            lblResultadoBusqueda.getStyleClass().removeAll("label-error");
            if (!lblResultadoBusqueda.getStyleClass().contains("label-ok")) {
                lblResultadoBusqueda.getStyleClass().add("label-ok");
            }
            lblResultadoBusqueda.setText(String.format(
                    "Encontrado: %s | Tel: %s | Correo: %s | Edad: %d | Registrado: %s",
                    estudiante.getNombreCompleto(), estudiante.getTelefono(),
                    estudiante.getCorreoElectronico(), estudiante.getEdad(), estudiante.getFechaRegistro()));
        } catch (EntidadNoEncontradaException ex) {
            lblResultadoBusqueda.getStyleClass().removeAll("label-ok");
            if (!lblResultadoBusqueda.getStyleClass().contains("label-error")) {
                lblResultadoBusqueda.getStyleClass().add("label-error");
            }
            lblResultadoBusqueda.setText(ex.getMessage());
        }
    }

    @FXML
    private void calcularIngresos() {
        if (dpDesde.getValue() == null || dpHasta.getValue() == null) {
            lblIngresos.setText("Selecciona ambas fechas del periodo.");
            return;
        }
        BigDecimal total = reporteService.calcularIngresosPorPeriodo(dpDesde.getValue(), dpHasta.getValue());
        lblIngresos.setText("Ingresos totales del periodo: $" + total);
    }
}
