package it.almaviva.impleme.bolite.integration.repositories.room;

import it.almaviva.impleme.bolite.integration.entities.templates.EntiTemplatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEnteTemplateRepository extends JpaRepository<EntiTemplatesEntity, Integer> {

    Optional<EntiTemplatesEntity> findByEnte_IdAndNome(Integer id, String nome);

}
