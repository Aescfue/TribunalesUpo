package com.example.application.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "docente")
public class Docente implements Comparable<Docente> {
    @Id
    @Size(max = 15)
    @Column(name = "dni", nullable = false, length = 15)
    private String dni;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dni", nullable = false)
    private Persona persona;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Size(max = 100)
    @NotNull
    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docente docente = (Docente) o;
        return Objects.equals(dni, docente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public int compareTo(Docente d2) {
        int salida =0;
        HashMap<String, Integer> categorias  = (HashMap<String, Integer>) Map.of(
                "PROFESOR/A VISITANTE LOU",10,
                "PROFESOR/A ASOCIADO LOU", 20,
                "PROFESOR/A SUSTITUTO INTERINO", 30,
                "PROFESOR/A COLABORADOR", 40,
                "PROFESOR/A TITULAR ESCUELA UNIVERSITARIA", 50,
                "PROFESOR/A AYUDANTE DOCTOR", 60,
                "PROFESOR/A TITULAR DE UNIVERSIDAD",70,
                "PROFESOR/A CONTRATADO DOCTOR TEMPORAL",80,
                "PROFESOR/A CONTRATADO DOCTOR", 90,
                "CATEDRATICO/A DE UNIVERSIDAD",100);

        if (categorias.get(this.categoria) > categorias.get(d2.categoria)) {
            salida= 1;
        }
        if(categorias.get(this.categoria).intValue() == categorias.get(d2.categoria).intValue()){
            salida = this.fechaIngreso.compareTo(d2.fechaIngreso);
        }
        if(categorias.get(this.categoria) < categorias.get(d2.categoria)){
            salida = -1;
        }
        return salida;
    }
}