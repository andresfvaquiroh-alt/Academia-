package org.example.academiaa.modelo;

import java.math.BigDecimal;

/**
 * Curso personalizado: incorpora sesiones individuales con un profesor,
 * un nivel de referencia requerido y los objetivos particulares del estudiante.
 * <p>
 * El costo de las sesiones con el profesor depende de la tarifa del profesor
 * asignado, por lo que este curso NO conoce a {@code Profesor} directamente
 * (evita acoplar el modelo de dominio con la asignación puntual de una
 * matrícula); ese cálculo combinado se realiza en la capa de servicio/matrícula.
 */
public class CursoPersonalizado extends Curso {

    private int cantidadSesiones;
    private NivelReferencia nivelReferencia;
    private String objetivosEstudiante;

    public CursoPersonalizado(String codigo, String nombre, String idioma, String descripcion,
                              int duracionMeses, BigDecimal valorMensual, EstadoCurso estado,
                              int cantidadSesiones, NivelReferencia nivelReferencia,
                              String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado);
        this.cantidadSesiones = cantidadSesiones;
        this.nivelReferencia = nivelReferencia;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
    }

    public NivelReferencia getNivelReferencia() {
        return nivelReferencia;
    }

    public void setNivelReferencia(NivelReferencia nivelReferencia) {
        this.nivelReferencia = nivelReferencia;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(String objetivosEstudiante) {
        this.objetivosEstudiante = objetivosEstudiante;
    }

    /** Valor base propio del curso (sin el costo de las sesiones del profesor). */
    @Override
    public BigDecimal calcularValorBase() {
        return getValorMensual().multiply(BigDecimal.valueOf(getDuracionMeses()));
    }

    /** Costo adicional de las sesiones, dado el profesor efectivamente asignado. */
    public BigDecimal calcularCostoSesiones(Profesor profesor) {
        if (profesor == null) {
            return BigDecimal.ZERO;
        }
        return profesor.getTarifaPorSesion().multiply(BigDecimal.valueOf(cantidadSesiones));
    }

    @Override
    public String getTipoCurso() {
        return "Personalizado";
    }
}
