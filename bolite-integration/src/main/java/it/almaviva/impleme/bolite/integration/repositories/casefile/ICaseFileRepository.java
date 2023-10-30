package it.almaviva.impleme.bolite.integration.repositories.casefile;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICaseFileRepository extends JpaRepository<CaseFileEntity, Integer>{

    Optional<CaseFileEntity> findByCodice(UUID codice);

    Optional<CaseFileEntity> findByCodiceAndCaseFileTypeEntity_Codice(UUID codice, String id);
    
    CaseFileEntity findByBookingId(Integer id);

}