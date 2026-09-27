package org.example.academiaa.controller;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.factory.DatosCurso;
import org.example.academiaa.factory.TipoCurso;
import org.example.academiaa.modelo.*;
import org.example.academiaa.service.CursoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

/**
 * Controlador de la vista de cursos. Delega en {@link CursoService}, que a su
 * vez usa el patrón Factory Method para instanciar el subtipo de
 * {@link Curso} correcto según el {@link TipoCurso} elegido en pantalla.
 */
public class CursoController {

    @FXML private ComboBox<TipoCurso> cbTipoCurso;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtValorMensual;
    @FXML private ComboBox<EstadoCurso> cbEstado;
    @FXML private TextField txtPorcentajeRecargo;
    @FXML private TextField txtCantidadSesiones;
    @FXML private ComboBox<NivelReferencia> cbNivelReferencia;
    @FXML private TextField txtObjetivos;
    @FXML private CheckBox chkPlataforma;
    @FXML private CheckBox chkMaterial;
    @FXML private CheckBox chkClub;
    @FXML private Label lblMensaje;

    @FXML private TableView<Curso> tablaCursos;
    @FXML private TableColumn<Curso, String> colCodigo;
    @FXML private TableColumn<Curso, String> colNombreCurso;
    @FXML private TableColumn<Curso, String> colTipo;
    @FXML private TableColumn<Curso, String> colIdioma;
    @FXML private TableColumn<Curso, String> colDuracion;
    @FXML private TableColumn<Curso, String> colValorMensual;
    @FXML private TableColumn<Curso, String> colValorBase;
    @FXML private TableColumn<Curso, String> colEstado;

    private final CursoService cursoService =
            new CursoService(Academia.obtenerInstancia().getCursoRepository());

    private final ObservableList<Curso> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cbTipoCurso.setItems(FXCollections.observableArrayList(TipoCurso.values()));
        cbTipoCurso.getSelectionModel().selectFirst();
        cbEstado.setItems(FXCollections.observableArrayList(EstadoCurso.values()));
        cbEstado.getSelectionModel().selectFirst();
        cbNivelReferencia.setItems(FXCollections.observableArrayList(NivelReferencia.values()));

        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombreCurso.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipoCurso()));
        colIdioma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdioma()));
        colDuracion.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuracionMeses())));
        colValorMensual.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getValorMensual().toString()));
        colValorBase.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().calcularValorBase().toString()));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));

        tablaCursos.setItems(datosTabla);
        refrescarTabla();
    }

    @FXML
    private void registrarCurso() {
        try {
            DatosCurso datos = new DatosCurso();
            datos.codigo = txtCodigo.getText().trim();
            datos.nombre = txtNombre.getText().trim();
            datos.idioma = txtIdioma.getText().trim();
            datos.descripcion = txtDescripcion.getText().trim();
            datos.duracionMeses = Integer.parseInt(txtDuracion.getText().trim());
            datos.valorMensual = new BigDecimal(txtValorMensual.getText().trim());
            datos.estado = cbEstado.getValue();

            TipoCurso tipo = cbTipoCurso.getValue();
            if (tipo == TipoCurso.INTENSIVO && !txtPorcentajeRecargo.getText().isBlank()) {
                datos.porcentajeRecargo = new BigDecimal(txtPorcentajeRecargo.getText().trim());
            }
            if (tipo == TipoCurso.PERSONALIZADO) {
                datos.cantidadSesiones = Integer.parseInt(txtCantidadSesiones.getText().trim());
                datos.nivelReferencia = cbNivelReferencia.getValue();
                datos.objetivosEstudiante = txtObjetivos.getText().trim();
            }

            Curso curso = cursoService.registrar(tipo, datos);
            if (chkPlataforma.isSelected()) curso.agregarBeneficio(Beneficio.PLATAFORMA_VIRTUAL);
            if (chkMaterial.isSelected()) curso.agregarBeneficio(Beneficio.MATERIAL_DIDACTICO);
            if (chkClub.isSelected()) curso.agregarBeneficio(Beneficio.CLUB_CONVERSACION);

            mostrarOk("Curso registrado correctamente como " + tipo + ".");
            limpiarFormulario();
            refrescarTabla();
        } catch (NumberFormatException ex) {
            mostrarError("Revisa que duración, valor mensual, recargo o sesiones sean numéricos.");
        } catch (ValidacionException ex) {
            mostrarError(ex.getMessage());
        }
    }

    @FXML
    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtIdioma.clear();
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        txtPorcentajeRecargo.clear();
        txtCantidadSesiones.clear();
        txtObjetivos.clear();
        chkPlataforma.setSelected(false);
        chkMaterial.setSelected(false);
        chkClub.setSelected(false);
        cbEstado.getSelectionModel().selectFirst();
        cbTipoCurso.getSelectionModel().selectFirst();
        cbNivelReferencia.getSelectionModel().clearSelection();
    }

    private void refrescarTabla() {
        datosTabla.setAll(cursoService.listarTodos());
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
