package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Data
@Entity
@Check(constraints = "unavailability_start_date < unavailability_end_date")
@Table(name = "ROOM_RESERVATION")
public class RoomReservationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name="cause",nullable = false)
	private String motivo;
	
	@Column(name="unavailability_start_date",nullable = false,columnDefinition = "TIMESTAMP")
	private LocalDate giornoDa;
	
	@Column(name="unavailability_end_date",nullable = false,columnDefinition = "TIMESTAMP")
	private LocalDate giornoA;
	
	@Column(name="unavailability_start_hour",nullable = false,columnDefinition = "TIME")
	private LocalTime oraDa;
	
	@Column(name="unavailability_end_hour",nullable = false,columnDefinition = "TIME")
	private LocalTime oraA;
	
	@Column
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoomReservationEntity that = (RoomReservationEntity) o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
