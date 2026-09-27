package org.example.academiaa.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contrato genérico de persistencia (aquí, en memoria) para cualquier entidad.
 * <p>
 * Dependency Inversion Principle (DIP): los servicios de la capa de negocio
 * dependen de esta abstracción, nunca de una implementación concreta
 * (por ejemplo {@code EstudianteRepositoryMemoria}). Cambiar el mecanismo de
 * almacenamiento (memoria, archivo, base de datos) no obliga a modificar
 * ningún servicio ni controlador.
 *
 * @param <T>  tipo de la entidad
 * @param <ID> tipo del identificador de la entidad
 */
public interface IRepository<T, ID> {

    void guardar(T entidad);

    Optional<T> buscarPorId(ID id);

    List<T> listarTodos();

    boolean existe(ID id);
}
