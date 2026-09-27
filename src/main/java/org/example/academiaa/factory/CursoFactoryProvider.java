package org.example.academiaa.factory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Punto único donde se obtiene la {@link CursoFactory} adecuada según el
 * {@link TipoCurso} solicitado. Se implementa como un registro (Map) en
 * lugar de un {@code switch}, de modo que agregar un nuevo tipo de curso
 * solo requiere una línea nueva en el registro (o incluso podría cargarse
 * dinámicamente), sin tocar el código que ya consume las fábricas (OCP).
 */
public final class CursoFactoryProvider {

    private static final Map<TipoCurso, CursoFactory> FABRICAS = new EnumMap<>(TipoCurso.class);

    static {
        FABRICAS.put(TipoCurso.REGULAR, new CursoRegularFactory());
        FABRICAS.put(TipoCurso.INTENSIVO, new CursoIntensivoFactory());
        FABRICAS.put(TipoCurso.PERSONALIZADO, new CursoPersonalizadoFactory());
    }

    private CursoFactoryProvider() {
    }

    public static CursoFactory obtenerFactory(TipoCurso tipo) {
        CursoFactory factory = FABRICAS.get(tipo);
        if (factory == null) {
            throw new IllegalArgumentException("No existe fábrica registrada para el tipo: " + tipo);
        }
        return factory;
    }
}
