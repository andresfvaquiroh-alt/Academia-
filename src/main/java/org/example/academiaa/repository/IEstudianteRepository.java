package org.example.academiaa.repository;

import org.example.academiaa.modelo.Estudiante;
import java.util.Optional;

/**
 * ISP: además de las operaciones CRUD genéricas, únicamente se agrega la
 * operación específica que el dominio realmente necesita para Estudiante
 * (búsqueda por documento de identidad), sin mezclar operaciones de otras
 * entidades.
 */
public interface IEstudianteRepository extends IRepository<Estudiante, String> {

    Optional<Estudiante> buscarPorDocumento(String documentoIdentidad);
}
