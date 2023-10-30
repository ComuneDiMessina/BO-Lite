package it.almaviva.impleme.bolite.integration.repositories.room;


import it.almaviva.impleme.bolite.integration.entities.room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoomRepository extends JpaRepository<RoomEntity, Integer> {

    void deleteByCodice(UUID codice);

    Optional<RoomEntity> findByCodice(UUID codice);
    
    @Query("SELECT r FROM RoomEntity r WHERE (r.ente.codice = :comune) and (:tipologia is null"
    		  + " or r.structure.id = :tipologia) and (:categoria is null or r.category.id = :categoria)")
    List<RoomEntity> getRooms(@Param("comune")String comune,@Param("tipologia") Integer tipologia,@Param("categoria") Integer categoria);
}
