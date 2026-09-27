package org.example.academiaa.service;

import  org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.factory.CursoFactory;
import org.example.academiaa.factory.CursoFactoryProvider;
import org.example.academiaa.factory.DatosCurso;
import org.example.academiaa.factory.TipoCurso;
import org.example.academiaa.modelo.Curso;
import org.example.academiaa.repository.ICursoRepository;

import java.util.List;

/**
 * SRP: valida y coordina el registro de cursos; la construcción concreta del
 * objeto {@link Curso} se delega en la fábrica correspondiente
 * ({@link CursoFactoryProvider}), por lo que este servicio no necesita
 * conocer los detalles internos de cada subtipo de curso (OCP).
 */
public class CursoService {

    private final ICursoRepository repository;

    public CursoService(ICursoRepository repository) {
        this.repository = repository;
    }

    public Curso registrar(TipoCurso tipo, DatosCurso datos) {
        if (datos.codigo == null || datos.codigo.isBlank()) {
            throw new ValidacionException("El código del curso es obligatorio");
        }
        if (repository.existe(datos.codigo)) {
            throw new ValidacionException("Ya existe un curso con el código " + datos.codigo);
        }
        CursoFactory factory = CursoFactoryProvider.obtenerFactory(tipo);
        Curso curso = factory.crearCurso(datos);
        repository.guardar(curso);
        return curso;
    }

    public List<Curso> listarTodos() {
        return repository.listarTodos();
    }
}
