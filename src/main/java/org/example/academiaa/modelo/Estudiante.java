package org.example.academiaa.modelo;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa a un estudiante registrado en la academia.
 * SRP: esta clase solo conoce y gestiona los datos propios del estudiante;
 * no calcula matrículas ni conoce servicios ni cursos.
 */
public class Estudiante {

    private final String documentoIdentidad; // identificador natural, único
    private String nombreCompleto;
    private String telefono;
    private String correoElectronico;
    private int edad;
    private final LocalDate fechaRegistro;

    public Estudiante(String documentoIdentidad, String nombreCompleto, String telefono,
                       String correoElectronico, int edad, LocalDate fechaRegistro) {
        this.documentoIdentidad = Objects.requireNonNull(documentoIdentidad, "El documento es obligatorio");
        this.nombreCompleto = Objects.requireNonNull(nombreCompleto, "El nombre es obligatorio");
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro == null ? LocalDate.now() : fechaRegistro;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudiante)) return false;
        Estudiante that = (Estudiante) o;
        return documentoIdentidad.equals(that.documentoIdentidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentoIdentidad);
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + documentoIdentidad + ")";
    }
}
