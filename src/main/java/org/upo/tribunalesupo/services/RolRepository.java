package org.upo.tribunalesupo.services;

import org.upo.tribunalesupo.data.Rol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("select r from Rol r " +
            "where lower(r.usuario) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.codigo) like lower(concat('%', :searchTerm, '%'))")
    List<Rol> search(@Param("searchTerm") String searchTerm);

    @Transactional
    @Modifying
    @Query(
    value = "INSERT INTO rol (codigo, usuario) VALUES (:codigo, :usuario)",
            nativeQuery = true)
    void guardar(@Param("usuario") String usuario, @Param("codigo") String codigo );
}