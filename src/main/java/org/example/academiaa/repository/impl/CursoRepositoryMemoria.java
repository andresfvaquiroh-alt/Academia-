package org.example.academiaa.repository.impl;

import org.example.academiaa.modelo.Curso;
import org.example.academiaa.repository.ICursoRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;

public class CursoRepositoryMemoria implements ICursoRepository {

    private final Map<String, Curso> datos = new LinkedHashMap<>();

    @Override
    public void guardar(Curso entidad) {
        datos.put(entidad.getCodigo(), entidad);
    }

    @Override
    public Optional<Curso> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Curso> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(datos.values()));
    }

    @Override
    public boolean existe(String id) {
        return datos.containsKey(id);
    }
}
