package com.example.application.services;

import com.example.application.data.Defensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DefensaRepository extends JpaRepository<Defensa, Long> {

    @Query("select d from Defensa d " +
            "where lower(concat(d.tribunal.codigoTFG,'')) like lower(concat('%', :searchTerm, '%')) ")
    List<Defensa> buscar(@Param("searchTerm") String searchTerm);

    @Query("select d from Defensa d " +
            "where lower(d.tribunal.codigoTFG.codigo) like lower(concat('%', :codigo, '%')) " +
            "AND concat('',d.tribunal.convocatoria.id.curso) like concat('', :curso ) " +
            "AND concat('',d.tribunal.convocatoria.id.numero) like concat('', :numero ) ")
    List<Defensa> buscarDefensa(String codigo, Integer curso, Integer numero);
}
