package org.example.academiaa.builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.academiaa.modelo.*;

/**
 * Patrón creacional <b>Builder</b>: la construcción de una {@link Matricula}
 * involucra varios pasos opcionales (profesor solo si el curso es
 * personalizado, servicios adicionales de a uno, descuento, etc.) por lo que
 * un constructor telescópico sería difícil de leer y de mantener. El builder
 * permite construirla de forma fluida y solo entrega el objeto ya validado
 * y coherente al llamar {@link #build()}.
 * <p>
 * SRP: esta clase se encarga exclusivamente de ensamblar una Matrícula válida;
 * no decide reglas de negocio de precios (esas viven en {@code Curso}/{@code Matricula})
 * ni persiste nada (eso es responsabilidad del repositorio).
 */
public class MatriculaBuilder {

    private String id = UUID.randomUUID().toString();
    private Estudiante estudiante;
    private Curso curso;
    private Profesor profesorAsignado;
    private LocalDate fechaMatricula = LocalDate.now();
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private BigDecimal descuento = BigDecimal.ZERO;

    public static MatriculaBuilder nueva() {
        return new MatriculaBuilder();
    }

    public MatriculaBuilder conId(String id) {
        this.id = id;
        return this;
    }

    public MatriculaBuilder conEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        return this;
    }

    public MatriculaBuilder conCurso(Curso curso) {
        this.curso = curso;
        return this;
    }

    /** Solo tiene efecto real cuando el curso es {@link CursoPersonalizado}. */
    public MatriculaBuilder conProfesor(Profesor profesor) {
        this.profesorAsignado = profesor;
        return this;
    }

    public MatriculaBuilder conFecha(LocalDate fecha) {
        this.fechaMatricula = fecha;
        return this;
    }

    public MatriculaBuilder agregarServicio(ServicioAdicional servicio) {
        if (servicio != null) {
            this.servicios.add(servicio);
        }
        return this;
    }

    public MatriculaBuilder conDescuento(BigDecimal descuento) {
        this.descuento = descuento == null ? BigDecimal.ZERO : descuento;
        return this;
    }

    /**
     * Valida la coherencia mínima del dominio y construye la matrícula.
     * Regla de negocio: un profesor asignado solo tiene sentido si el curso
     * es personalizado (así se identifica la relación estudiante-curso-profesor).
     */
    public Matricula build() {
        if (estudiante == null) {
            throw new IllegalStateException("La matrícula requiere un estudiante");
        }
        if (curso == null) {
            throw new IllegalStateException("La matrícula requiere un curso");
        }
        if (profesorAsignado != null && !(curso instanceof CursoPersonalizado)) {
            throw new IllegalStateException(
                    "Solo los cursos personalizados admiten profesor asignado");
        }
        return new Matricula(id, estudiante, curso, profesorAsignado, fechaMatricula, servicios, descuento);
    }
}
