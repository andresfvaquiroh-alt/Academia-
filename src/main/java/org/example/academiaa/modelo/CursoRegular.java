package org.example.academiaa.modelo;

import java.math.BigDecimal;

/**
 * Curso regular: su valor base es simplemente la duración contratada
 * multiplicada por el valor mensual, sin recargos adicionales.
 */
public class CursoRegular extends Curso {

    public CursoRegular(String codigo, String nombre, String idioma, String descripcion,
                        int duracionMeses, BigDecimal valorMensual, EstadoCurso estado) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado);
    }

    @Override
    public BigDecimal calcularValorBase() {
        return getValorMensual().multiply(BigDecimal.valueOf(getDuracionMeses()));
    }

    @Override
    public String getTipoCurso() {
        return "Regular";
    }
}
