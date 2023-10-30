package it.almaviva.impleme.bolite.integration.repositories.casefile;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICaseFileTypeRepository extends JpaRepository<CaseFileTypeEntity, Integer> {

  Optional<CaseFileTypeEntity> findByCodice(String codice);

}
