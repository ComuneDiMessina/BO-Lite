package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IOutstandingDebtService;
import it.almaviva.impleme.bolite.core.ITransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class TransactionalService implements ITransactionalService {

    private final IOutstandingDebtService iOutstandingDebtService;

    public TransactionalService(IOutstandingDebtService outstandingDebtService) {
        this.iOutstandingDebtService = outstandingDebtService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void caseFileOutstandingDebtUpdateState(String idPosizioneDebitoria, String stateTypeOutstandingDebt) {
        iOutstandingDebtService.updateOutstandingDebtState(idPosizioneDebitoria, stateTypeOutstandingDebt);
    }
}
