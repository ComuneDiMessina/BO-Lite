package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Entity
@Check(constraints = "start_hour < end_hour")
@Table(name = "room_daily_timelost")
@EqualsAndHashCode(exclude = {"tariff"})
public class RoomRowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "start_hour", columnDefinition = "TIME")
    private LocalTime oraDa;

    @Column(name = "end_hour", columnDefinition = "TIME")
    private LocalTime oraA;
    
    @Column(name = "timeslot_cost")
	private BigDecimal costoFascia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_daily_tariff_id", nullable = false)
    private RoomDailyTariffEntity tariff;

}
