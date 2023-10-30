package it.almaviva.impleme.bolite.integration.entities.casefile;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@Table(name = "case_file_document")
public class CaseFileDocumentEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pratica_seq")
    //@SequenceGenerator(sequenceName = "documento_seq", allocationSize = 1, name = "documento_seq")
	@Column(name = "ID_DOCUMENTO", unique = true, nullable = false)
    private String idDocument;

    @Column(name = "NOME", unique = false, nullable = false)
    private String name;
    
    @Column(name = "DESCRIZIONE", unique = false)
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRATICA", nullable = false)
	private CaseFileEntity praticaEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseFileDocumentEntity that = (CaseFileDocumentEntity) o;
        return getIdDocument().equals(that.getIdDocument());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDocument());
    }
}