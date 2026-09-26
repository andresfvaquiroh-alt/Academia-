package org.example.academiaa.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa la matrícula de un {@link Estudiante} en un {@link Curso}.
 * <p>
 * Es también la clase de asociación que, cuando el curso es personalizado,
 * identifica la relación estudiante-curso-profesor exigida por el enunciado.
 * <p>
 * SRP: Matricula solo agrupa los datos de la inscripción y expone el cálculo
 * del valor final; no sabe cómo se registran estudiantes, cursos o profesores
 * (eso es responsabilidad de los repositorios/servicios).
 * <p>
 * Las instancias de esta clase se construyen únicamente a través de
 * {@link org.example.academiaa.builder.MatriculaBuilder} (patrón Builder),
 * por eso su único constructor es de paquete/privado controlado por el builder.
 */
public class Matricula {

    private final String id;
    private final Estudiante estudiante;
    private final Curso curso;
    private final Profesor profesorAsignado; // solo aplica para CursoPersonalizado
    private final LocalDate fechaMatricula;
    private final List<ServicioAdicional> serviciosUtilizados;
    private final BigDecimal descuento; // porcentaje, ej 0.10 = 10%

    public Matricula(String id, Estudiante estudiante, Curso curso, Profesor profesorAsignado,
                     LocalDate fechaMatricula, List<ServicioAdicional> serviciosUtilizados,
                     BigDecimal descuento) {
        this.id = Objects.requireNonNull(id);
        this.estudiante = Objects.requireNonNull(estudiante, "La matrícula requiere un estudiante");
        this.curso = Objects.requireNonNull(curso, "La matrícula requiere un curso");
        this.profesorAsignado = profesorAsignado;
        this.fechaMatricula = fechaMatricula == null ? LocalDate.now() : fechaMatricula;
        this.serviciosUtilizados = serviciosUtilizados == null
                ? new ArrayList<>()
                : new ArrayList<>(serviciosUtilizados);
        this.descuento = descuento == null ? BigDecimal.ZERO : descuento;
    }

    public String getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public Profesor getProfesorAsignado() {
        return profesorAsignado;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public List<ServicioAdicional> getServiciosUtilizados() {
        return Collections.unmodifiableList(serviciosUtilizados);
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Calcula el valor final de la matrícula:
     * valor base del curso (+ sesiones con profesor si es personalizado)
     * + servicios adicionales utilizados, aplicando el descuento configurado.
     */
    public BigDecimal calcularValorFinal() {
        BigDecimal total = curso.calcularValorBase();

        if (curso instanceof CursoPersonalizado) {
            total = total.add(((CursoPersonalizado) curso).calcularCostoSesiones(profesorAsignado));
        }

        BigDecimal totalServicios = serviciosUtilizados.stream()
                .map(ServicioAdicional::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        total = total.add(totalServicios);

        BigDecimal valorDescuento = total.multiply(descuento);
        total = total.subtract(valorDescuento);

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "Matrícula " + id + " | " + estudiante.getNombreCompleto() + " -> " + curso.getNombre();
    }
}
