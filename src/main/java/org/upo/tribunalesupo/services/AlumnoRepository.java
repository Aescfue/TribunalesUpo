package org.upo.tribunalesupo.services;

import org.upo.tribunalesupo.data.Alumno;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    @Query("select a from Alumno a " +
            "where lower(a.persona.nombre) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(a.persona.apellidos) like lower(concat('%', :searchTerm, '%'))")
    List<Alumno> search(@Param("searchTerm") String searchTerm);

    @Query("select a from Alumno a " +
            "where lower(a.dni) like lower(concat('%', :searchTerm, '%')) ")
    List<Alumno> buscarDni(@Param("searchTerm") String searchTerm);

    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO Alumno (dni, fecha_ingreso) VALUES (:dni, :fecha)",
            nativeQuery = true)
    void guardar(@Param("dni") String dni, @Param("fecha")LocalDate fecha );
}
