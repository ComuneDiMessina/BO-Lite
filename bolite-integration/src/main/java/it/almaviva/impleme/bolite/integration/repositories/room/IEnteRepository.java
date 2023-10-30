package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.room.EnteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEnteRepository extends JpaRepository<EnteEntity, Integer> {

    Optional<EnteEntity> findByCodice(String codice);

}
