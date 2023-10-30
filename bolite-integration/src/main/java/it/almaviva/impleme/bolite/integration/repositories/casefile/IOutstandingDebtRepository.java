package it.almaviva.impleme.bolite.integration.repositories.casefile;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOutstandingDebtRepository extends CrudRepository<CaseFileOutstandingDebtEntity, String>{
    CaseFileOutstandingDebtEntity findByCaseFileEntity(UUID codice);
    Optional<CaseFileOutstandingDebtEntity> findByIuv(String iuv);
}