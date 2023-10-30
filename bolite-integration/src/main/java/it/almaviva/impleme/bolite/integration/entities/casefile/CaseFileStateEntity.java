package it.almaviva.impleme.bolite.integration.entities.casefile;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "CASE_FILE_STATE")
public class CaseFileStateEntity {

	public static final Integer RICHIESTA_PAGAMENTO = 1;
	public static final Integer RICHIESTA_DOCUMENTAZIONE = 2;
	public static final Integer INSERITA = 3;
	public static final Integer IN_LAVORAZIONE = 4;
	public static final Integer VALIDATA = 5;
	public static final Integer RIFIUTATA = 6;
	public static final Integer ANNULLATA = 7;
	public static final Integer REVOCATA = 8;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String label;
	
	@Column
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CaseFileStateEntity that = (CaseFileStateEntity) o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
