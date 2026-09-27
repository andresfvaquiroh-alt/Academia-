package org.example.academiaa.repository.impl;

import org.example.academiaa.modelo.Profesor;
import org.example.academiaa.repository.IProfesorRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;

public class ProfesorRepositoryMemoria implements IProfesorRepository {

    private final Map<String, Profesor> datos = new LinkedHashMap<>();

    @Override
    public void guardar(Profesor entidad) {
        datos.put(entidad.getIdentificacion(), entidad);
    }

    @Override
    public Optional<Profesor> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Profesor> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(datos.values()));
    }

    @Override
    public boolean existe(String id) {
        return datos.containsKey(id);
    }
}
