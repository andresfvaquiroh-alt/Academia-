package org.example.academiaa.factory;

import  org.example.academiaa.modelo.Curso;
import  org.example.academiaa.modelo.CursoPersonalizado;

public class CursoPersonalizadoFactory implements CursoFactory {

    @Override
    public Curso crearCurso(DatosCurso d) {
        return new CursoPersonalizado(d.codigo, d.nombre, d.idioma, d.descripcion,
                d.duracionMeses, d.valorMensual, d.estado,
                d.cantidadSesiones, d.nivelReferencia, d.objetivosEstudiante);
    }
}
