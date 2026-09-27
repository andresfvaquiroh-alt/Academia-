package org.example.academiaa.repository.impl;

import org.example.academiaa.modelo.Estudiante;
import org.example.academiaa.repository.IEstudianteRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Implementación concreta en memoria. Los servicios NUNCA referencian esta
 * clase directamente: siempre trabajan contra {@link IEstudianteRepository}
 * (DIP), de modo que esta clase podría sustituirse por una basada en JDBC
 * o JPA sin romper el resto de la aplicación.
 */
public class EstudianteRepositoryMemoria implements IEstudianteRepository {

    private final Map<String, Estudiante> datos = new LinkedHashMap<>();

    @Override
    public void guardar(Estudiante entidad) {
        datos.put(entidad.getDocumentoIdentidad(), entidad);
    }

    @Override
    public Optional<Estudiante> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Estudiante> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(datos.values()));
    }

    @Override
    public boolean existe(String id) {
        return datos.containsKey(id);
    }

    @Override
    public Optional<Estudiante> buscarPorDocumento(String documentoIdentidad) {
        return buscarPorId(documentoIdentidad);
    }
}
