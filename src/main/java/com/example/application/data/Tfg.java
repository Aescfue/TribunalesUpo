package com.example.application.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tfg")
public class Tfg {
    @Id
    @Size(max = 50)
    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Size(max = 4000)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 4000)
    private String nombre;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dni", nullable = false)
    private Alumno alumno;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "curso_conv", referencedColumnName = "curso", nullable = false),
            @JoinColumn(name = "numero_conv", referencedColumnName = "numero", nullable = false)
    })
    private Convocatoria convocatoria;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dni_docente1", nullable = false)
    private Docente docente1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_docente2")
    private Docente docente2;

    public Docente getDocente1() {
        return docente1;
    }

    public void setDocente1(Docente docente1) {
        this.docente1 = docente1;
    }

    public Docente getDocente2() {
        return docente2;
    }

    public void setDocente2(Docente docente2) {
        this.docente2 = docente2;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

}