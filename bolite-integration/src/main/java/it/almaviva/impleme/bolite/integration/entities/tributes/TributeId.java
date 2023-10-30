package it.almaviva.impleme.bolite.integration.entities.tributes;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class TributeId implements Serializable {

    @Column(name = "ente", nullable = false)
    private String ente;

    @Column(name = "Id", nullable = false)
    private String id;
}
