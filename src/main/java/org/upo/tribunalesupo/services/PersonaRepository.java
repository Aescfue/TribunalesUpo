package org.upo.tribunalesupo.services;

import org.upo.tribunalesupo.data.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query("select p from Persona p " +
            "where lower(p.usuario) like lower(concat('%', :searchTerm, '%')) ")
    List<Persona> search(@Param("searchTerm") String searchTerm);

    @Query("select p from Persona p " +
            "where lower(p.nombre) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.apellidos) like lower(concat('%', :searchTerm, '%'))")
    List<Persona> buscarPorNombre(@Param("searchTerm") String searchTerm);
}
