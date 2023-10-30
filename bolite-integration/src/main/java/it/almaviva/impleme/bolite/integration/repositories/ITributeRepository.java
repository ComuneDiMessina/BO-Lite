package it.almaviva.impleme.bolite.integration.repositories;

import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITributeRepository extends JpaRepository<TributeEntity, TributeId> {
	
	@Query("SELECT t FROM TributeEntity t WHERE (t.ente.codice = :codice)")
	Optional<List<TributeEntity>> findByEnte(@Param("codice")String codice);

	@Query("SELECT t FROM TributeEntity t WHERE (t.ente.codice = :codice) and t.diritti_segreteria = true")
	Optional<TributeEntity> findDirittiDiSegreteria(@Param("codice")String codice);

	@Query("SELECT t FROM TributeEntity t WHERE (t.ente.codice = :codice) and t.prenota_spazio = true")
	Optional<TributeEntity> findPrenotazioneSpazio(@Param("codice")String codice);
}
