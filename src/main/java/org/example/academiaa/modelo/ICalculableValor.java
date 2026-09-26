package org.example.academiaa.modelo;

import java.math.BigDecimal;

/**
 * Interface Segregation Principle (ISP): en lugar de forzar a  Curso
 * a implementar una interfaz "gorda" con operaciones que no todas las clases
 * necesitan, se aísla exclusivamente el contrato de cálculo de valor.
 * Cualquier clase que sepa calcular su propio valor base (no solo Curso)
 * podría implementar esta interfaz sin arrastrar métodos ajenos a su rol.
 */
public interface ICalculableValor {
    BigDecimal calcularValorBase();
}
