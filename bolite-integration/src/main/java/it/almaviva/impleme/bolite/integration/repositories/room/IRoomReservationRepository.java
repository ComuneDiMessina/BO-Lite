package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.room.RoomReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomReservationRepository extends JpaRepository<RoomReservationEntity, Integer>{

}
