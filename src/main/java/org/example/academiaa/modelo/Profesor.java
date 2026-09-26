package org.example.academiaa.modelo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa a un profesor que puede ser asignado a estudiantes
 * matriculados en cursos personalizados.
 */
public class Profesor {

    private final String identificacion;
    private String nombre;
    private String idiomaQueEnsenia;
    private String telefono;
    private BigDecimal tarifaPorSesion;

    public Profesor(String identificacion, String nombre, String idiomaQueEnsenia,
                     String telefono, BigDecimal tarifaPorSesion) {
        this.identificacion = Objects.requireNonNull(identificacion);
        this.nombre = Objects.requireNonNull(nombre);
        this.idiomaQueEnsenia = idiomaQueEnsenia;
        this.telefono = telefono;
        this.tarifaPorSesion = tarifaPorSesion == null ? BigDecimal.ZERO : tarifaPorSesion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdiomaQueEnsenia() {
        return idiomaQueEnsenia;
    }

    public void setIdiomaQueEnsenia(String idiomaQueEnsenia) {
        this.idiomaQueEnsenia = idiomaQueEnsenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getTarifaPorSesion() {
        return tarifaPorSesion;
    }

    public void setTarifaPorSesion(BigDecimal tarifaPorSesion) {
        this.tarifaPorSesion = tarifaPorSesion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profesor)) return false;
        Profesor profesor = (Profesor) o;
        return identificacion.equals(profesor.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion);
    }

    @Override
    public String toString() {
        return nombre + " - " + idiomaQueEnsenia;
    }
}
