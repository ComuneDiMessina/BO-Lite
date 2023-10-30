package it.almaviva.impleme.bolite.integration.entities.templates;

import it.almaviva.impleme.bolite.integration.entities.room.EnteEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "enti_templates", schema = "pagopa")
public class EntiTemplatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String template;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ente_id", nullable = false)
    private EnteEntity ente;
}
