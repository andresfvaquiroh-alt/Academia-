package org.example.academiaa.modelo;

import org.example.academiaa.repository.*;
import org.example.academiaa.repository.impl.*;
/**
 * Representa a la academia de idiomas "LenguajeCafetero" y sus datos básicos.
 * <p>
 * Patrón creacional <b>Singleton</b>: existe una única instancia de la academia
 * en toda la aplicación (no tendría sentido administrar dos academias distintas
 * dentro del mismo sistema), y esa instancia es además el punto único de acceso
 * a los repositorios que contienen los estudiantes, cursos, profesores,
 * servicios y matrículas registrados.
 * <p>
 * Dependency Inversion Principle (DIP): Academia expone los repositorios a
 * través de sus <i>interfaces</i> ({@link IEstudianteRepository}, etc.) y no
 * de sus implementaciones concretas; la capa de servicios y controladores
 * depende de esas abstracciones, nunca de {@code EstudianteRepositoryMemoria}
 * directamente.
 */
public final class Academia {

    private static Academia instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    private final IEstudianteRepository estudianteRepository;
    private final ICursoRepository cursoRepository;
    private final IProfesorRepository profesorRepository;
    private final IServicioAdicionalRepository servicioRepository;
    private final IMatriculaRepository matriculaRepository;

    private Academia() {
        this.nombreComercial = "LenguajeCafetero";
        this.nit = "900.000.000-1";
        this.direccion = "Armenia, Quindío";
        this.telefono = "";
        this.correoElectronico = "";
        this.paginaWeb = "";

        // Implementaciones por defecto (en memoria). Al depender de las
        // interfaces, estas podrían reemplazarse por una implementación
        // con base de datos sin tocar el resto de la aplicación (OCP + DIP).
        this.estudianteRepository = new EstudianteRepositoryMemoria();
        this.cursoRepository = new CursoRepositoryMemoria();
        this.profesorRepository = new ProfesorRepositoryMemoria();
        this.servicioRepository = new ServicioAdicionalRepositoryMemoria();
        this.matriculaRepository = new MatriculaRepositoryMemoria();
    }

    /** Único punto de acceso a la instancia (patrón Singleton). */
    public static synchronized Academia obtenerInstancia() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    public void configurarDatosBasicos(String nombreComercial, String nit, String direccion,
                                        String telefono, String correoElectronico, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.paginaWeb = paginaWeb;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public IEstudianteRepository getEstudianteRepository() {
        return estudianteRepository;
    }

    public ICursoRepository getCursoRepository() {
        return cursoRepository;
    }

    public IProfesorRepository getProfesorRepository() {
        return profesorRepository;
    }

    public IServicioAdicionalRepository getServicioRepository() {
        return servicioRepository;
    }

    public IMatriculaRepository getMatriculaRepository() {
        return matriculaRepository;
    }
}
