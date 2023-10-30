package it.almaviva.impleme.bolite.integration.entities.casefile;

import it.almaviva.impleme.bolite.integration.entities.LocalDateConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "CASE_FILE_USER")
@Data
public class CaseFileUserEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column
	private String nome;
	
	@Column(name = "cognome")
	private String surname;
	
	@Column(name="cf")
	private String cf;

	@Column(name="piva")
	private String piva;

	@Convert(converter = LocalDateConverter.class)
	@Column(name="birth_date")
	private LocalDate birthDate;

	@Column(name="birth_place")
	private String birthPlace;
	
	@Column(nullable = false)
	private String email;

	@Column(name="telephone_number",nullable = false)
	private String telephoneNumber;

	@Column(name="residenza_stato")
	private String residenza_stato;

	@Column(name="residenza_provincia")
	private String residenza_provincia;

	@Column(name="residenza_comune")
	private String residenza_comune;

	@Column(name="residenza_address")
	private String residenza_address;

	@Column(name="residenza_civico")
	private String residenza_civico;

	@Column(name="residenza_cap")
	private String residenza_cap;

	@Column(name="ente_ragione_sociale")
	private String ente_ragione_sociale;


	private Boolean flag_presidente = false;

	private Boolean flag_legale= false;

	private Boolean flag_richiedente= false;

	private Boolean flag_organizzatore= false;

	private Boolean flag_ente = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "casefile_id", nullable = false)
    private CaseFileEntity caseFile;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CaseFileUserEntity that = (CaseFileUserEntity) o;
		return Objects.equals(getCf(), that.getCf()) &&
				Objects.equals(getPiva(), that.getPiva());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCf(), getPiva());
	}
}
