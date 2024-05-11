package org.upo.tribunalesupo.data;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "convocatoria")
public class Convocatoria {
    @EmbeddedId
    private ConvocatoriaId id;

    public ConvocatoriaId getId() {
        return id;
    }

    public void setId(ConvocatoriaId id) {
        this.id = id;
    }

    public Convocatoria(){};

    public Convocatoria(ConvocatoriaId id ){
        this.setId(id);
    };

}