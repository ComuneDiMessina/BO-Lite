package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "ROOM_SERVICE")
@EqualsAndHashCode(exclude = "room")
public class RoomServiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name="code", nullable = false)
	private String codice;
	
	@Column(name="description", nullable = false)
	private String descrizione;
	
	@Column
	private String note;
	
	@Column(name="amount")
	private BigDecimal importo;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
}

