package it.almaviva.impleme.bolite.integration.repositories.casefile;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaseFileStateRepository extends JpaRepository<CaseFileStateEntity, Integer> {
}
