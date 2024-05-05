package com.example.application.services;

import com.example.application.data.Docente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
    @Query("select d from Docente d " +
            "where lower(d.persona.nombre) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(d.persona.apellidos) like lower(concat('%', :searchTerm, '%'))")
    List<Docente> search(@Param("searchTerm") String searchTerm);

    @Query("select d from Docente d " +
            "where lower(d.dni) like lower(concat('%', :searchTerm, '%')) ")
    List<Docente> buscarDni(@Param("searchTerm") String searchTerm);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Docente (dni, fecha_ingreso, categoria) VALUES (:dni, :fecha, :categoria)",
            nativeQuery = true)
    void guardar(@Param("dni") String dni, @Param("fecha")LocalDate fecha, @Param("categoria") String categoria );
}
