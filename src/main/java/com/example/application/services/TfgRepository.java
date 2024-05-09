package com.example.application.services;

import com.example.application.data.Tfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TfgRepository extends JpaRepository<Tfg, Long> {

    @Query("select t from Tfg t join fetch t.codigo cod " +
            "where lower(t.codigo) like lower(concat('%', :searchTerm, '%')) ")
    List<Tfg> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT t FROM Tfg t WHERE t NOT IN " +
            "(SELECT tr.codigoTFG FROM Tribunal tr WHERE tr.convocatoria.id.curso = :curso AND tr.convocatoria.id.numero = :numero)")
    List<Tfg> buscarTfgsSinTribunal(@Param("curso") String curso, @Param("numero") String numero);
}