package it.almaviva.impleme.bolite.integration.entities.casefile;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import it.almaviva.impleme.bolite.integration.entities.LocalDateTimeConverter;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.room.EnteEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor

@Setter
@Getter
@Entity
@Table(name = "CASE_FILE")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

@Inheritance(strategy = InheritanceType.JOINED)
public class CaseFileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ente_id", nullable = false)
	private EnteEntity ente;

	@Column(unique = true)
	private UUID codice;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "DATA_CREAZIONE", nullable = false, updatable = false)
	private LocalDateTime creationDate;
	
	@Column(name = "NOTE", columnDefinition = "TEXT")
	private String note;

	@Column(name = "tributo_id")
	private String tributo_id;

	//ultimo iuv generato
	private String iuv;

	@Column(name = "rata", length = 2)
	private String rata;

	@Column(name = "CAUSALE", columnDefinition = "TEXT")
	private String causale;
	
	@Column(name = "NUMERO_PROTOCOLLO")
	private String numeroProtocollo;

	@Column(name = "DATA_PROTOCOLLO")
	private LocalDateTime dataProtocollo;

	@Column(name = "IMPORTO")
	private BigDecimal importo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stato_id", nullable = false)
	private CaseFileStateEntity stato;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "BOOKING_ID")
    private BookingEntity booking;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TIPO_PRATICA")
	private CaseFileTypeEntity caseFileTypeEntity;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "tributo")
	private String details;

	@Column(columnDefinition = "TEXT", name = "qrcode")
	private String qrcode;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseFileEntity")
	private Set<CaseFileOutstandingDebtEntity> outstandingDebtEntities  = new HashSet<>(0);

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "praticaEntity")
	private Set<CaseFileDocumentEntity> documentEntities = new HashSet<>(0);

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "caseFile")
	private Set<CaseFileUserEntity> users = new HashSet<>(0);

	public void addDebt(CaseFileOutstandingDebtEntity item) {
		this.outstandingDebtEntities.add(item);
		item.setCaseFileEntity(this);
	}

	public void addDocumento(CaseFileDocumentEntity item) {
		this.documentEntities.add(item);
		item.setPraticaEntity(this);
	}


	public void addUsers(CaseFileUserEntity item) {
		this.users.add(item);
		item.setCaseFile(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CaseFileEntity that = (CaseFileEntity) o;
		return getCodice().equals(that.getCodice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodice());
	}
}
