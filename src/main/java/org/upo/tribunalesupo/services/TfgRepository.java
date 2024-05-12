package org.upo.tribunalesupo.services;

import org.upo.tribunalesupo.data.Alumno;
import org.upo.tribunalesupo.data.Docente;
import org.upo.tribunalesupo.data.Tfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TfgRepository extends JpaRepository<Tfg, Long> {

    List<Tfg> findByAlumno(Alumno a);

    @Query("select t from Tfg t " +
            "where lower(t.codigo) like lower(concat('%', :searchTerm, '%')) ")
    List<Tfg> search(@Param("searchTerm") String searchTerm);

    @Query("SELECT t FROM Tfg t WHERE t NOT IN " +
            "(SELECT tr.codigoTFG FROM Tribunal tr WHERE tr.convocatoria.id.curso = :curso AND tr.convocatoria.id.numero = :numero)")
    List<Tfg> buscarTfgsSinTribunal(@Param("curso") String curso, @Param("numero") String numero);

    @Query("SELECT t FROM Tfg t WHERE t IN " +
            "(SELECT tr.codigoTFG FROM Tribunal tr WHERE tr.docente1 = :d OR tr.docente2 = :d OR tr.docente3 = :d)")
    List<Tfg> buscarTfgsDocente(Docente d);
}