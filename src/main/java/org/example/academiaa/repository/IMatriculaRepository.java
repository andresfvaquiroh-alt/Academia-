package org.example.academiaa.repository;

import org.example.academiaa.modelo.Matricula;
import java.time.LocalDate;
import java.util.List;

public interface IMatriculaRepository extends IRepository<Matricula, String> {

    /** Matrículas cuya fecha está dentro del rango [desde, hasta], ambos inclusive. */
    List<Matricula> buscarPorPeriodo(LocalDate desde, LocalDate hasta);
}
