package org.example.academiaa.service;

import org.example.academiaa.exception.ValidacionException;
import org.example.academiaa.modelo.ServicioAdicional;
import org.example.academiaa.repository.IServicioAdicionalRepository;

import java.math.BigDecimal;
import java.util.List;

public class ServicioAdicionalService {

    private final IServicioAdicionalRepository repository;

    public ServicioAdicionalService(IServicioAdicionalRepository repository) {
        this.repository = repository;
    }

    public ServicioAdicional registrar(String codigo, String nombre, String descripcion,
                                        BigDecimal precio, boolean disponible) {
        if (codigo == null || codigo.isBlank()) {
            throw new ValidacionException("El código del servicio es obligatorio");
        }
        if (repository.existe(codigo)) {
            throw new ValidacionException("Ya existe un servicio con código " + codigo);
        }
        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponible);
        repository.guardar(servicio);
        return servicio;
    }

    public List<ServicioAdicional> listarTodos() {
        return repository.listarTodos();
    }

    public List<ServicioAdicional> listarDisponibles() {
        return repository.listarTodos().stream().filter(ServicioAdicional::isDisponible).toList();
    }
}
