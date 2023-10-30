package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.domain.dto.RichiestaPagamentoDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;

public interface IRichiestaPagamentoService {
    CaseFileOutstandingDebtEntity createRichiestaPagamento(RichiestaPagamentoDTO richiestaPagamentoDTO);
}