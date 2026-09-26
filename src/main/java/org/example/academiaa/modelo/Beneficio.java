package org.example.academiaa.modelo;

/**
 * Beneficios que puede incluir un  Curso
 */
public enum Beneficio {
    PLATAFORMA_VIRTUAL("Acceso a la plataforma virtual"),
    MATERIAL_DIDACTICO("Material didáctico"),
    CLUB_CONVERSACION("Club de conversación");

    private final String descripcion;

    Beneficio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
