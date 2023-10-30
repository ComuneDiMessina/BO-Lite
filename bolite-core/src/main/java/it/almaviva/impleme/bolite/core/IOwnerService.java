package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;


public interface IOwnerService {
    CaseFileUserEntity findById (String codiceFiscale);
    CaseFileUserEntity createOwner(RichiedenteDTO ownerDTO);

}
