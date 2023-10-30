package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.room.RoomAperturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomOpeningRepository extends JpaRepository<RoomAperturaEntity, Integer> {
}
