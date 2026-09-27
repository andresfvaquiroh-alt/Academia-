package org.example.academiaa.service;

import  org.example.academiaa.exception.EntidadNoEncontradaException;
import  org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.Estudiante;
import org.example.academiaa.repository.IEstudianteRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * SRP: coordina las reglas de negocio propias de Estudiante (validaciones,
 * búsqueda por documento) delegando el almacenamiento en el repositorio.
 * DIP: depende de {@link IEstudianteRepository}, no de una implementación concreta.
 */
public class EstudianteService {

    private final IEstudianteRepository repository;

    public EstudianteService(IEstudianteRepository repository) {
        this.repository = repository;
    }

    public Estudiante registrar(String documento, String nombre, String telefono,
                                 String correo, int edad, LocalDate fechaRegistro) {
        if (documento == null || documento.isBlank()) {
            throw new ValidacionException("El documento de identidad es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre completo es obligatorio");
        }
        if (repository.existe(documento)) {
            throw new ValidacionException("Ya existe un estudiante con el documento " + documento);
        }
        Estudiante estudiante = new Estudiante(documento, nombre, telefono, correo, edad, fechaRegistro);
        repository.guardar(estudiante);
        return estudiante;
    }

    /** Consulta requerida por el enunciado: buscar un estudiante por su documento. */
    public Estudiante buscarPorDocumento(String documento) {
        return repository.buscarPorDocumento(documento)
                .orElseThrow(() -> new EntidadNoEncontradaException(
                        "No existe un estudiante con documento " + documento));
    }

    public List<Estudiante> listarTodos() {
        return repository.listarTodos();
    }
}
