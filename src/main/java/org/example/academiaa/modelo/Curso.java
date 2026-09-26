package org.example.academiaa.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase base abstracta para los distintos tipos de curso que ofrece la academia.
 * <p>
 * Open/Closed Principle (OCP): nuevos tipos de curso se agregan creando una
 * nueva subclase (y su respectiva {@code CursoFactory}), sin necesidad de
 * modificar esta clase ni el código que ya trabaja contra ella.
 * <p>
 * Liskov Substitution Principle (LSP): cualquier subclase de Curso puede
 * usarse en cualquier lugar donde se espere un Curso (repositorios, tablas
 * de la interfaz, cálculo de matrícula) sin alterar el comportamiento
 * esperado por quien la consume.
 */
public abstract class Curso implements ICalculableValor {

    private final String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private BigDecimal valorMensual;
    private EstadoCurso estado;
    private final List<Beneficio> beneficios = new ArrayList<>();

    protected Curso(String codigo, String nombre, String idioma, String descripcion,
                    int duracionMeses, BigDecimal valorMensual, EstadoCurso estado) {
        this.codigo = Objects.requireNonNull(codigo);
        this.nombre = Objects.requireNonNull(nombre);
        this.idioma = idioma;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual == null ? BigDecimal.ZERO : valorMensual;
        this.estado = estado == null ? EstadoCurso.ACTIVO : estado;
    }

    /** Cada subtipo de curso define su propia regla de negocio para el valor base. */
    @Override
    public abstract BigDecimal calcularValorBase();

    /** Nombre legible del tipo de curso, útil para reportes y la interfaz gráfica. */
    public abstract String getTipoCurso();

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public BigDecimal getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(BigDecimal valorMensual) {
        this.valorMensual = valorMensual;
    }

    public EstadoCurso getEstado() {
        return estado;
    }

    public void setEstado(EstadoCurso estado) {
        this.estado = estado;
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public void agregarBeneficio(Beneficio beneficio) {
        if (beneficio != null && !beneficios.contains(beneficio)) {
            beneficios.add(beneficio);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Curso)) return false;
        Curso curso = (Curso) o;
        return codigo.equals(curso.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + getTipoCurso() + ")";
    }
}
