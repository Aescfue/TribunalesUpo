package com.example.application.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class ConvocatoriaId implements java.io.Serializable {
    private static final long serialVersionUID = -7492708686326044145L;
    @NotNull
    @Column(name = "curso", nullable = false)
    private Integer curso;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConvocatoriaId entity = (ConvocatoriaId) o;
        return Objects.equals(this.numero, entity.numero) &&
                Objects.equals(this.curso, entity.curso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, curso);
    }

}