package org.example.academiaa.repository.impl;

import org.example.academiaa.modelo.ServicioAdicional;
import org.example.academiaa.repository.IServicioAdicionalRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;

public class ServicioAdicionalRepositoryMemoria implements IServicioAdicionalRepository {

    private final Map<String, ServicioAdicional> datos = new LinkedHashMap<>();

    @Override
    public void guardar(ServicioAdicional entidad) {
        datos.put(entidad.getCodigo(), entidad);
    }

    @Override
    public Optional<ServicioAdicional> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<ServicioAdicional> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(datos.values()));
    }

    @Override
    public boolean existe(String id) {
        return datos.containsKey(id);
    }
}
