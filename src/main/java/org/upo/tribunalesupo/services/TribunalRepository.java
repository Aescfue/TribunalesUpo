package org.upo.tribunalesupo.services;

import org.upo.tribunalesupo.data.Docente;
import org.upo.tribunalesupo.data.Tfg;
import org.upo.tribunalesupo.data.Tribunal;
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

    @Query(value = "SELECT CONCAT(persona.nombre, ' ', persona.apellidos) AS nombre, COUNT(*) AS apariciones " +
            "FROM Tribunal " +
            "JOIN persona ON (Tribunal.dni_1 = persona.dni OR Tribunal.dni_2 = persona.dni OR Tribunal.dni_3 = persona.dni) " +
            "GROUP BY nombre " +
            "ORDER by apariciones ASC;",nativeQuery = true
    )
    List<Object[]> estadisticaDocentesTribunal();

    @Query(
            value = "SELECT d.dni, COALESCE(t.veces_aparece, 0) AS veces_aparece " +
                    "FROM Docente d " +
                    "LEFT JOIN ( " +
                    "    SELECT dni, COUNT(*) AS veces_aparece " +
                    "    FROM ( " +
                    "        SELECT dni_1 AS dni FROM Tribunal " +
                    "        UNION ALL " +
                    "        SELECT dni_2 AS dni FROM Tribunal " +
                    "        UNION ALL " +
                    "        SELECT dni_3 AS dni FROM Tribunal " +
                    "    ) AS docentes_en_tribunal " +
                    "    GROUP BY dni " +
                    ") AS t ON d.dni = t.dni " +
                    "ORDER BY veces_aparece ASC;",
            nativeQuery = true
    )
    List<Object[]> docentesMenosParticipaciones();

    List<Tribunal> findByCodigoTFG(Tfg tfg);
    List<Tribunal> findByDocente1(Docente d);
    List<Tribunal> findByDocente2(Docente d);
    List<Tribunal> findByDocente3(Docente d);
}
