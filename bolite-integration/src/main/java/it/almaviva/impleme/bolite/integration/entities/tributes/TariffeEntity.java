package it.almaviva.impleme.bolite.integration.entities.tributes;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tariffe", schema = "pagopa")
public class TariffeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column
    private String peg;


    @Column(name = "importo_unitario")
    private BigDecimal importo;

    @Column(name = "quantita")
    private Integer quantita;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "importo_is_editable")
    private Boolean importoIsEditable;

    @Column(name = "quantita_is_editable")
    private Boolean quantitaIsEditable;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "ente", referencedColumnName = "ente"), @JoinColumn(name = "id_tributo", referencedColumnName = "id")})
    private TributeEntity tributo;



}
