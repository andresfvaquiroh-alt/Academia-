package org.example.academiaa.service;

import   org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.Profesor;
import org.example.academiaa.repository.IProfesorRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProfesorService {

    private final IProfesorRepository repository;

    public ProfesorService(IProfesorRepository repository) {
        this.repository = repository;
    }

    public Profesor registrar(String identificacion, String nombre, String idioma,
                               String telefono, BigDecimal tarifaPorSesion) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new ValidacionException("La identificación del profesor es obligatoria");
        }
        if (repository.existe(identificacion)) {
            throw new ValidacionException("Ya existe un profesor con identificación " + identificacion);
        }
        Profesor profesor = new Profesor(identificacion, nombre, idioma, telefono, tarifaPorSesion);
        repository.guardar(profesor);
        return profesor;
    }

    public List<Profesor> listarTodos() {
        return repository.listarTodos();
    }
}
