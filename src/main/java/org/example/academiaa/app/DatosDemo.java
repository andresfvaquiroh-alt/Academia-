package org.example.academiaa.app;

import org.example.academiaa.factory.DatosCurso;
import org.example.academiaa.factory.TipoCurso;
import org.example.academiaa.modelo.*;
import org.example.academiaa.service.CursoService;
import org.example.academiaa.service.EstudianteService;
import org.example.academiaa.service.ProfesorService;
import org.example.academiaa.service.ServicioAdicionalService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Carga un pequeño conjunto de datos de ejemplo, únicamente si los
 * repositorios están vacíos, para poder demostrar el funcionamiento de la
 * aplicación (sustentación) sin tener que digitar todo manualmente.
 */
public final class DatosDemo {

    private DatosDemo() {
    }

    public static void cargarSiVacio(Academia academia) {
        if (!academia.getEstudianteRepository().listarTodos().isEmpty()) {
            return;
        }

        EstudianteService estudianteService = new EstudianteService(academia.getEstudianteRepository());
        CursoService cursoService = new CursoService(academia.getCursoRepository());
        ProfesorService profesorService = new ProfesorService(academia.getProfesorRepository());
        ServicioAdicionalService servicioService = new ServicioAdicionalService(academia.getServicioRepository());

        estudianteService.registrar("1002345678", "Mariana Gómez López", "3104567890",
                "mariana.gomez@correo.com", 22, LocalDate.of(2026, 2, 10));
        estudianteService.registrar("1098765432", "Juan Esteban Ríos", "3159876543",
                "juan.rios@correo.com", 27, LocalDate.of(2026, 3, 5));

        DatosCurso regular = new DatosCurso();
        regular.codigo = "ING-REG-01";
        regular.nombre = "Inglés Regular Básico";
        regular.idioma = "Inglés";
        regular.descripcion = "Curso regular de inglés, 4 horas semanales";
        regular.duracionMeses = 6;
        regular.valorMensual = new BigDecimal("180000");
        Curso cursoRegular = cursoService.registrar(TipoCurso.REGULAR, regular);
        cursoRegular.agregarBeneficio(Beneficio.PLATAFORMA_VIRTUAL);
        cursoRegular.agregarBeneficio(Beneficio.MATERIAL_DIDACTICO);

        DatosCurso intensivo = new DatosCurso();
        intensivo.codigo = "FRA-INT-01";
        intensivo.nombre = "Francés Intensivo A2";
        intensivo.idioma = "Francés";
        intensivo.descripcion = "Curso intensivo, 8 horas semanales";
        intensivo.duracionMeses = 3;
        intensivo.valorMensual = new BigDecimal("250000");
        intensivo.porcentajeRecargo = new BigDecimal("0.20");
        Curso cursoIntensivo = cursoService.registrar(TipoCurso.INTENSIVO, intensivo);
        cursoIntensivo.agregarBeneficio(Beneficio.CLUB_CONVERSACION);

        DatosCurso personalizado = new DatosCurso();
        personalizado.codigo = "POR-PER-01";
        personalizado.nombre = "Portugués Personalizado B1";
        personalizado.idioma = "Portugués";
        personalizado.descripcion = "Curso personalizado con sesiones individuales";
        personalizado.duracionMeses = 2;
        personalizado.valorMensual = new BigDecimal("150000");
        personalizado.cantidadSesiones = 10;
        personalizado.nivelReferencia = NivelReferencia.B1;
        personalizado.objetivosEstudiante = "Preparación para entrevista laboral";
        cursoService.registrar(TipoCurso.PERSONALIZADO, personalizado);

        profesorService.registrar("PRF-01", "Laura Fernanda Torres", "Portugués",
                "3201234567", new BigDecimal("35000"));
        profesorService.registrar("PRF-02", "Carlos Andrés Muñoz", "Francés",
                "3112223344", new BigDecimal("30000"));

        servicioService.registrar("SRV-01", "Simulacro de examen de certificación",
                "Examen simulado con retroalimentación", new BigDecimal("40000"), true);
        servicioService.registrar("SRV-02", "Tutoría de refuerzo",
                "Sesión individual de refuerzo", new BigDecimal("25000"), true);
        servicioService.registrar("SRV-03", "Material impreso",
                "Cuadernillo de ejercicios impreso", new BigDecimal("15000"), true);
        servicioService.registrar("SRV-04", "Taller de conversación",
                "Taller grupal de práctica oral", new BigDecimal("20000"), true);
    }
}
