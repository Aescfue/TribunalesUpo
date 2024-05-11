package org.upo.tribunalesupo.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tribunal")
public class Tribunal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tribunal", nullable = false)
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigoTFG", nullable = false)
    private Tfg codigoTFG;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dni_1", nullable = false)
    private Docente docente1;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dni_2", nullable = false)
    private Docente docente2;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dni_3", nullable = false)
    private Docente docente3;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "curso_conv", referencedColumnName = "curso", nullable = false),
            @JoinColumn(name = "numero_conv", referencedColumnName = "numero", nullable = false)
    })
    private Convocatoria convocatoria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tfg getCodigoTFG() {
        return codigoTFG;
    }

    public void setCodigoTFG(Tfg codigoTFG) {
        this.codigoTFG = codigoTFG;
    }

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

    public Docente getDocente3() {
        return docente3;
    }

    public void setDocente3(Docente docente3) {
        this.docente3 = docente3;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void ordenarDocentes(){
        List<Docente> docentes = new ArrayList<Docente>();
        Docente d1 = this.getDocente1();
        docentes.add(docente1);
        docentes.add(docente2);
        docentes.add(docente3);
        Collections.sort(docentes);

        this.docente1 = docentes.get(2);
        this.docente2 = docentes.get(1);
        this.docente3 = docentes.get(0);
    }
}