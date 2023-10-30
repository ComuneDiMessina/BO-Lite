package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "ROOM_STRUCTURE_TYPE")
public class RoomStructureTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String struttura;
	
	@Column(name="description",nullable = false)
	private String descrizione;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoomStructureTypeEntity that = (RoomStructureTypeEntity) o;
		return getId().equals(that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}
