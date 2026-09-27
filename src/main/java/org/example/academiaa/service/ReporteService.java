package org.example.academiaa.service;

import org.example.academiaa.modelo.Matricula;
import org.example.academiaa.repository.IMatriculaRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * SRP: encapsula exclusivamente la lógica de reportes (ingresos por periodo),
 * separada de {@link MatriculaService} para no mezclar el caso de uso de
 * "matricular" con el de "consultar ingresos".
 */
public class ReporteService {

    private final IMatriculaRepository matriculaRepository;

    public ReporteService(IMatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    /**
     * Recorre las matrículas registradas dentro del periodo [fechaInicial, fechaFinal]
     * y acumula el valor total generado, tal como lo exige el enunciado.
     */
    public BigDecimal calcularIngresosPorPeriodo(LocalDate fechaInicial, LocalDate fechaFinal) {
        List<Matricula> matriculasDelPeriodo = matriculaRepository.buscarPorPeriodo(fechaInicial, fechaFinal);

        BigDecimal totalIngresos = BigDecimal.ZERO;
        for (Matricula matricula : matriculasDelPeriodo) {
            totalIngresos = totalIngresos.add(matricula.calcularValorFinal());
        }
        return totalIngresos.setScale(2, RoundingMode.HALF_UP);
    }
}
