package it.almaviva.impleme.bolite.integration.entities.casefile;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "CASE_FILE_TYPE")
@Data
public class CaseFileTypeEntity {

    public static final String ROOM_BOOKING = "01";
    public static final String GENERIC = "02";
    public static final String PASSI_CARRABILI = "03";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
    @Column(name = "CODICE", unique = true)
    private String codice;
    
    @Column(name = "NOME")
    private String name;
    
    @Column(name = "DESCRIZIONE")
    private String description;

    @Column(name = "NOTE")
    private String notes;

    @Column(columnDefinition = "TEXT", name = "TESTO_LIBERO")
    private String freeText;

    @Column(columnDefinition = "TEXT", name = "img")
    private String img;

    @Column(name = "HIDDEN")
    private Boolean hidden;

    @Column(name = "IMPORTO")
    private BigDecimal importo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseFileTypeEntity that = (CaseFileTypeEntity) o;
        return getCodice().equals(that.getCodice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodice());
    }
}