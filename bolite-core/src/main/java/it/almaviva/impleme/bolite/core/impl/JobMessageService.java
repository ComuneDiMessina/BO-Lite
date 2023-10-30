package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IJobMessageService;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.zeebe.outadapter.MessageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Transactional
@Service
public class JobMessageService implements IJobMessageService {

	private final MessageClient messageClient;
	private final ICaseFileRepository iCaseFileRepository;

	public JobMessageService(MessageClient messageClient, ICaseFileRepository iCaseFileRepository) {
		this.messageClient = messageClient;
		this.iCaseFileRepository = iCaseFileRepository;
	}

	@Override
	public void sendOrderCancel(UUID idCaseFile) {
		messageClient.sendOrderCancel(idCaseFile);
	}

	@Override
	public void sendPaymentRcv(UUID idCaseFile, String idDebt) {
		messageClient.sendPaymentRcv(idCaseFile, idDebt);
	}

	@Override
	public void sendIntegrationRcv(UUID idCaseFile, String path) {
		messageClient.sendIntegrationRcv(idCaseFile, path);
	}

}
