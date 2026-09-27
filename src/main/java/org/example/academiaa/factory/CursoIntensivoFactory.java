package org.example.academiaa.factory;

import  org.example.academiaa.modelo.Curso;
import  org.example.academiaa.modelo.CursoIntensivo;

import java.math.BigDecimal;

public class CursoIntensivoFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso d) {
        BigDecimal recargo = d.porcentajeRecargo == null ? BigDecimal.valueOf(0.20) : d.porcentajeRecargo;
        return new CursoIntensivo(d.codigo, d.nombre, d.idioma, d.descripcion,
                d.duracionMeses, d.valorMensual, d.estado, recargo);
    }
}
