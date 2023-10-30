package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.room.RoomCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomCategoryRepository extends JpaRepository<RoomCategoryEntity, Integer> {
}
