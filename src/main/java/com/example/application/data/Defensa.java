package com.example.application.data;

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
    @Column(name = "rubrica", nullable = false)
    private Double rubrica;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tribunal", nullable = false)
    private Tribunal tribunal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRubrica() {
        return rubrica;
    }

    public void setRubrica(Double rubrica) {
        this.rubrica = rubrica;
    }

    public Tribunal getTribunal() {
        return tribunal;
    }

    public void setTribunal(Tribunal tribunal) {
        this.tribunal = tribunal;
    }
}