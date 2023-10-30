package it.almaviva.impleme.bolite.integration.entities.casefile;

import it.almaviva.impleme.bolite.integration.entities.LocalDateConverter;
import it.almaviva.impleme.bolite.integration.entities.LocalDateTimeConverter;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.enums.EOutstandingDebtStates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "case_file_outstanding_debt")
public class CaseFileOutstandingDebtEntity {

	@Id
	@GeneratedValue(generator = "outstandingDebtGenerator")
    @GenericGenerator(name = "outstandingDebtGenerator", strategy = "it.almaviva.impleme.bolite.integration.utils.OutstandingDebtIDGenerator")
	@Column(name = "ID_RICHIESTA", unique = true, nullable = false)
	private String idOutstandingDebt;

	@Column(name = "IUV")
	private String iuv;

	@Column(name = "causale", columnDefinition = "TEXT")
	private String causale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRATICA")
	private CaseFileEntity caseFileEntity;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns({@JoinColumn(name = "ente_id", referencedColumnName = "ente"), @JoinColumn(name = "tributo_id", referencedColumnName = "id")})
	private TributeEntity tributeEntity;

	@Column(name="IMPORTO")
	private BigDecimal amount;
	
	@Column(name="STATO")
	@Enumerated(EnumType.STRING)
	private EOutstandingDebtStates state;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "DATA_CREAZIONE")
	private LocalDateTime creationDate;

	@Convert(converter = LocalDateConverter.class)
	@Column(name = "DATA_SCADENZA")
	private LocalDate dueDate;
}
