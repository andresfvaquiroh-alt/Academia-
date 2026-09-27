package org.example.academiaa.service;

import org.example.academiaa.builder.MatriculaBuilder;
import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.*;
import org.example.academiaa.repository.IMatriculaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * SRP: orquesta el caso de uso "matricular estudiante" apoyándose en
 * {@link MatriculaBuilder} (patrón Builder) para ensamblar el objeto y en el
 * repositorio para persistirlo; no calcula precios por sí mismo (esa
 * responsabilidad vive en {@link Matricula}/{@link Curso}).
 */
public class MatriculaService {

    private final IMatriculaRepository repository;

    public MatriculaService(IMatriculaRepository repository) {
        this.repository = repository;
    }

    public Matricula matricular(Estudiante estudiante, Curso curso, Profesor profesor,
                                 List<ServicioAdicional> servicios, BigDecimal descuento,
                                 LocalDate fecha) {
        if (curso.getEstado() != EstadoCurso.ACTIVO) {
            throw new ValidacionException("Solo se puede matricular a cursos en estado ACTIVO");
        }
        if (curso instanceof CursoPersonalizado && profesor == null) {
            throw new ValidacionException("Los cursos personalizados requieren un profesor asignado");
        }

        MatriculaBuilder builder = MatriculaBuilder.nueva()
                .conEstudiante(estudiante)
                .conCurso(curso)
                .conProfesor(profesor)
                .conFecha(fecha == null ? LocalDate.now() : fecha)
                .conDescuento(descuento);

        if (servicios != null) {
            servicios.forEach(builder::agregarServicio);
        }

        Matricula matricula = builder.build();
        repository.guardar(matricula);
        return matricula;
    }

    public List<Matricula> listarTodas() {
        return repository.listarTodos();
    }
}
