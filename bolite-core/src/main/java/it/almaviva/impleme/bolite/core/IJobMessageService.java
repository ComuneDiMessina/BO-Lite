package it.almaviva.impleme.bolite.core;

import java.util.UUID;

public interface IJobMessageService {

	 void sendOrderCancel (UUID idCaseFile);
	 void sendPaymentRcv (UUID idCaseFile, String idDebt);
	 void sendIntegrationRcv (UUID idCaseFile, String path);
}
