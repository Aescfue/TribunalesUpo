package com.example.application.services;

import com.example.application.data.Tribunal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TribunalRepository extends JpaRepository<Tribunal, Long> {

    @Query("select t from Tribunal t " +
            "where lower(t.codigoTFG.codigo) like lower(concat('%', :codigo, '%')) " +
            "AND concat('',t.convocatoria.id.curso) like concat('', :curso ) " +
            "AND concat('',t.convocatoria.id.numero) like concat('', :numero ) ")
    List<Tribunal> buscarCodigo(String codigo, Integer curso, Integer numero);

    @Query("select t from Tribunal t " +
            "where concat('',t.convocatoria.id.curso) like concat('%', :anno, '%') ")
    List<Tribunal> buscarAnno(String anno);
}
