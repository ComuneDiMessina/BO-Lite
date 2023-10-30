package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "Enti", schema = "pagopa")
public class EnteEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String codice;

	@Column(nullable = false)
	private String descrizione;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EnteEntity that = (EnteEntity) o;
		return getCodice().equals(that.getCodice());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodice());
	}
}
