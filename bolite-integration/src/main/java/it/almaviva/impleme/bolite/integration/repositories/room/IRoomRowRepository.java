package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.room.RoomRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRowRepository  extends JpaRepository<RoomRowEntity, Integer> {
}
