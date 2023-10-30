package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.impleme.bolite.core.IJobMessageService;
import it.almaviva.impleme.bolite.core.IOutstandingDebtService;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class OutstandingDebtController implements IOutstandingDebtController{
	
	
	private final IJobMessageService jobMessageService;
	private final IOutstandingDebtService outstandingDebtService;

	public OutstandingDebtController(IJobMessageService jobMessageService, IOutstandingDebtService outstandingDebtService) {
		this.jobMessageService = jobMessageService;
		this.outstandingDebtService = outstandingDebtService;
	}

	@Override
	public void paymentRcvByIuv(String iuv, String ente){
		log.info("paymentRcvByIuv");
		CaseFileOutstandingDebtEntity caseFileOutstandingDebtEntity = outstandingDebtService.findByIuv(iuv);
		UUID idCaseFile = caseFileOutstandingDebtEntity.getCaseFileEntity().getCodice();
		jobMessageService.sendPaymentRcv(idCaseFile, caseFileOutstandingDebtEntity.getIdOutstandingDebt());
	}

	@Override
	public void paymentRcv(UUID id){
		jobMessageService.sendPaymentRcv(id, null);
	}

}