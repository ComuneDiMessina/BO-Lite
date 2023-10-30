package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ROOM_TARIFF")
@EqualsAndHashCode(exclude = "fasce")
public class RoomTariffEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "full_day_cost")
	private BigDecimal costoInteraGiornata;
	
	@Column(name = "weekly_cost")
	private BigDecimal costoSettimanale;
	
	@Column(name = "hourly_cost")
	private BigDecimal costoOrario;

	@Column(name = "flag_intera_giornata")
	private Boolean flagInteraGiornata;

	@Column(name = "flag_intera_settimana")
	private Boolean flagInteraSettimana;

	@Column
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

	@Column(name = "evento")
    private String eventType;
	
	@OneToMany(mappedBy = "tariff" , orphanRemoval = true, cascade = {  CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomDailyTariffEntity> fasce = new HashSet<>(0);
	
	public void addRange(RoomDailyTariffEntity item) {
		 this.fasce.add(item); 
		 item.setTariff(this);
    }

}
