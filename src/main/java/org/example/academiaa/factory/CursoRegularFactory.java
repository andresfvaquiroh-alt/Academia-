package org.example.academiaa.factory;

import  org.example.academiaa.modelo.Curso;
import  org.example.academiaa.modelo.CursoRegular;

public class CursoRegularFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso d) {
        return new CursoRegular(d.codigo, d.nombre, d.idioma, d.descripcion,
                d.duracionMeses, d.valorMensual, d.estado);
    }
}
