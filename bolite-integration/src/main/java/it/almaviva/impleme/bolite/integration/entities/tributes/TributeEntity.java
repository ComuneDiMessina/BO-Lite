package it.almaviva.impleme.bolite.integration.entities.tributes;

import it.almaviva.impleme.bolite.integration.entities.room.EnteEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "Tributi", schema = "pagopa")
public class TributeEntity {

	@EmbeddedId
	private TributeId tributeId;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ente",referencedColumnName="codice", nullable = false, insertable = false, updatable = false)
    private EnteEntity ente;
	
	@Column(name = "descrizione_tributo")
	private String descrizioneTributo;
	
	@Column
	private String tipo;

	@Column
	private String anno;

	@Column
	private Integer giorni_scadenza;

	@Column
	private String sottotipo;

	@Column
	private Boolean validazione;
	
	@Column(name = "descrizione_rt")
	private String descrizioneRT;
	
	@Column(name = "descrizione_tributo_estesa")
	private String descrizioneTributoEstesa;
	
	@Column
	private String spontaneo;
	
	@Column(name = "configparam")
	private String configParam;

	private Boolean diritti_segreteria;

	private Boolean paga_diritti_segreteria;

	private Boolean prenota_spazio;
	
	@Column(name="data_creazione",nullable = false,columnDefinition = "TIMESTAMP")
	private LocalDateTime dataCreazione;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tributo")
    private Set<TariffeEntity> tariffe;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TributeEntity that = (TributeEntity) o;
		return getTributeId().equals(that.getTributeId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTributeId());
	}
}
