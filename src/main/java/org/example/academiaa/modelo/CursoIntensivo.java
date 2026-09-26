package org.example.academiaa.modelo;

import java.math.BigDecimal;

/**
 * Curso intensivo: además del valor por duración, aplica un porcentaje de
 * recargo propio de la modalidad intensiva (mayor carga horaria semanal).
 */
public class CursoIntensivo extends Curso {

    private BigDecimal porcentajeRecargo; // ej: 0.20 = 20%

    public CursoIntensivo(String codigo, String nombre, String idioma, String descripcion,
                          int duracionMeses, BigDecimal valorMensual, EstadoCurso estado,
                          BigDecimal porcentajeRecargo) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, estado);
        this.porcentajeRecargo = porcentajeRecargo == null ? BigDecimal.valueOf(0.20) : porcentajeRecargo;
    }

    public BigDecimal getPorcentajeRecargo() {
        return porcentajeRecargo;
    }

    public void setPorcentajeRecargo(BigDecimal porcentajeRecargo) {
        this.porcentajeRecargo = porcentajeRecargo;
    }

    @Override
    public BigDecimal calcularValorBase() {
        BigDecimal base = getValorMensual().multiply(BigDecimal.valueOf(getDuracionMeses()));
        BigDecimal recargo = base.multiply(porcentajeRecargo);
        return base.add(recargo);
    }

    @Override
    public String getTipoCurso() {
        return "Intensivo";
    }
}

