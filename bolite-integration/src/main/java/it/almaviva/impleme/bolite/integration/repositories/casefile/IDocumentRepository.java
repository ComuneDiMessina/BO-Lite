package it.almaviva.impleme.bolite.integration.repositories.casefile;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileDocumentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDocumentRepository extends CrudRepository<CaseFileDocumentEntity, String>{

}