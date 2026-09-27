package org.example.academiaa.factory;

import  org.example.academiaa.modelo.EstadoCurso;
import  org.example.academiaa.modelo.NivelReferencia;

import java.math.BigDecimal;

/**
 * Objeto de transferencia con todos los datos que alguna de las fábricas
 * concretas de  Curso podría necesitar. No todos los campos aplican
 * a todos los tipos de curso; cada fábrica concreta solo lee los que le
 * corresponden.
 */
public class DatosCurso {
    public String codigo;
    public String nombre;
    public String idioma;
    public String descripcion;
    public int duracionMeses;
    public BigDecimal valorMensual;
    public EstadoCurso estado = EstadoCurso.ACTIVO;

    // Específicos de CursoIntensivo
    public BigDecimal porcentajeRecargo;

    // Específicos de CursoPersonalizado
    public int cantidadSesiones;
    public NivelReferencia nivelReferencia;
    public String objetivosEstudiante;
}
