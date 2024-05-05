package com.example.application.services;

import com.example.application.data.Convocatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Long> {

    @Query("select c from Convocatoria c " +
            "where CONCAT(c.id.curso, '') like lower(concat('%', :value, '%')) ")
    List<Convocatoria> buscar(String value);
}
