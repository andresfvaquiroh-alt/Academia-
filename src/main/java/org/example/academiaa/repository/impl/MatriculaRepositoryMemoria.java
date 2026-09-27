package org.example.academiaa.repository.impl;

import org.example.academiaa.modelo.Matricula;
import org.example.academiaa.repository.IMatriculaRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collections;

public class MatriculaRepositoryMemoria implements IMatriculaRepository {

    private final Map<String, Matricula> datos = new LinkedHashMap<>();

    @Override
    public void guardar(Matricula entidad) {
        datos.put(entidad.getId(), entidad);
    }

    @Override
    public Optional<Matricula> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Matricula> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(datos.values()));
    }

    @Override
    public boolean existe(String id) {
        return datos.containsKey(id);
    }

    @Override
    public List<Matricula> buscarPorPeriodo(LocalDate desde, LocalDate hasta) {
        return datos.values().stream()
                .filter(m -> !m.getFechaMatricula().isBefore(desde) && !m.getFechaMatricula().isAfter(hasta))
                .collect(Collectors.toList());
    }
}
