package it.almaviva.impleme.bolite.integration.repositories.user;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<CaseFileUserEntity, Integer> {

    Optional<CaseFileUserEntity> findByCf(String codiceFiscale);

    List<CaseFileUserEntity> findAllByCaseFile_Codice(UUID codicePratica);
}


