package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.casefile.ICaseFileService;
import it.almaviva.impleme.bolite.utils.RollBackEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class TransactionalCaseFileListener {
	

	@Autowired 
	private ICaseFileService casefileService;
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void rollBackCaseFile(RollBackEvent events) {
		casefileService.deleteDirectory(events.getData());
	    log.info("Rollback: "+events);
	}
	

	
}