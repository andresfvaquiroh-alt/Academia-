package org.example.academiaa.controller;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.*;
import org.example.academiaa.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador del caso de uso "matricular estudiante". Combina varios
 * servicios (cada uno responsable de su propia entidad) y delega el
 * ensamblaje final en {@link MatriculaService}, que internamente usa el
 * patrón Builder.
 */
public class MatriculaController {

    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<Curso> cbCurso;
    @FXML private ComboBox<Profesor> cbProfesor;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtDescuento;
    @FXML private ListView<ServicioAdicional> listServicios;
    @FXML private Label lblMensaje;

    @FXML private TableView<Matricula> tablaMatriculas;
    @FXML private TableColumn<Matricula, String> colEstudianteMat;
    @FXML private TableColumn<Matricula, String> colCursoMat;
    @FXML private TableColumn<Matricula, String> colProfesorMat;
    @FXML private TableColumn<Matricula, String> colFechaMat;
    @FXML private TableColumn<Matricula, String> colServiciosMat;
    @FXML private TableColumn<Matricula, String> colDescuentoMat;
    @FXML private TableColumn<Matricula, String> colValorFinalMat;

    private final Academia academia = Academia.obtenerInstancia();
    private final EstudianteService estudianteService = new EstudianteService(academia.getEstudianteRepository());
    private final CursoService cursoService = new CursoService(academia.getCursoRepository());
    private final ProfesorService profesorService = new ProfesorService(academia.getProfesorRepository());
    private final ServicioAdicionalService servicioService = new ServicioAdicionalService(academia.getServicioRepository());
    private final MatriculaService matriculaService = new MatriculaService(academia.getMatriculaRepository());

    private final ObservableList<Matricula> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbEstudiante.setItems(FXCollections.observableArrayList(estudianteService.listarTodos()));
        cbCurso.setItems(FXCollections.observableArrayList(cursoService.listarTodos()));
        cbProfesor.setItems(FXCollections.observableArrayList(profesorService.listarTodos()));
        listServicios.setItems(FXCollections.observableArrayList(servicioService.listarDisponibles()));
        listServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dpFecha.setValue(LocalDate.now());

        colEstudianteMat.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getEstudiante().getNombreCompleto()));
        colCursoMat.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getCurso().getNombre()));
        colProfesorMat.setCellValueFactory(m -> new SimpleStringProperty(
                m.getValue().getProfesorAsignado() == null ? "—" : m.getValue().getProfesorAsignado().getNombre()));
        colFechaMat.setCellValueFactory(m -> new SimpleStringProperty(String.valueOf(m.getValue().getFechaMatricula())));
        colServiciosMat.setCellValueFactory(m -> new SimpleStringProperty(String.valueOf(m.getValue().getServiciosUtilizados().size())));
        colDescuentoMat.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getDescuento().toString()));
        colValorFinalMat.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().calcularValorFinal().toString()));

        tablaMatriculas.setItems(datosTabla);
        refrescarTabla();
    }

    @FXML
    private void matricular() {
        try {
            Estudiante estudiante = cbEstudiante.getValue();
            Curso curso = cbCurso.getValue();
            if (estudiante == null || curso == null) {
                mostrarError("Selecciona un estudiante y un curso.");
                return;
            }
            Profesor profesor = curso instanceof CursoPersonalizado ? cbProfesor.getValue() : null;

            BigDecimal descuento = txtDescuento.getText().isBlank()
                    ? BigDecimal.ZERO
                    : new BigDecimal(txtDescuento.getText().trim());

            List<ServicioAdicional> servicios = listServicios.getSelectionModel().getSelectedItems();

            Matricula matricula = matriculaService.matricular(
                    estudiante, curso, profesor, servicios, descuento, dpFecha.getValue());

            mostrarOk("Matrícula registrada. Valor final: $" + matricula.calcularValorFinal());
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException ex) {
            mostrarError("El descuento debe ser un valor numérico (ej. 0.10).");
        } catch (ValidacionException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        cbEstudiante.getSelectionModel().clearSelection();
        cbCurso.getSelectionModel().clearSelection();
        cbProfesor.getSelectionModel().clearSelection();
        txtDescuento.clear();
        listServicios.getSelectionModel().clearSelection();
        dpFecha.setValue(LocalDate.now());
    }

    private void refrescarTabla() {
        cbEstudiante.setItems(FXCollections.observableArrayList(estudianteService.listarTodos()));
        cbCurso.setItems(FXCollections.observableArrayList(cursoService.listarTodos()));
        datosTabla.setAll(matriculaService.listarTodas());
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
