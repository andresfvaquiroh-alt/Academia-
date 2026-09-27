package org.example.academiaa.factory;

import  org.example.academiaa.modelo.Curso;

/**
 * Patrón creacional <b>Factory Method</b>: cada tipo de curso tiene su propia
 * fábrica encargada de construirlo correctamente a partir de los datos
 * capturados en la interfaz, encapsulando las reglas de creación
 * (por ejemplo, valores por defecto del recargo intensivo).
 * <p>
 * Open/Closed Principle: para soportar un nuevo tipo de curso basta con crear
 * una nueva clase que implemente esta interfaz y registrarla en
 * {@link CursoFactoryProvider}; ninguna clase existente se modifica.
 */
public interface CursoFactory {

    Curso crearCurso(DatosCurso datos);
}
