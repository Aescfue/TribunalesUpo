package org.upo.tribunalesupo.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "defensa")
public class Defensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_defensa", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tribunal", nullable = false)
    private Tribunal tribunal;

    @NotNull
    @Column(name = "calidad", nullable = false)
    private Double calidad;

    @NotNull
    @Column(name = "adquisicion", nullable = false)
    private Double adquisicion;

    @NotNull
    @Column(name = "presentacion", nullable = false)
    private Double presentacion;

    @NotNull
    @Column(name = "defensa", nullable = false)
    private Double defensa;

    public Double getDefensa() {
        return defensa;
    }

    public void setDefensa(Double defensa) {
        this.defensa = defensa;
    }

    public Double getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Double presentacion) {
        this.presentacion = presentacion;
    }

    public Double getAdquisicion() {
        return adquisicion;
    }

    public void setAdquisicion(Double adquisicion) {
        this.adquisicion = adquisicion;
    }

    public Double getCalidad() {
        return calidad;
    }

    public void setCalidad(Double calidad) {
        this.calidad = calidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }
}