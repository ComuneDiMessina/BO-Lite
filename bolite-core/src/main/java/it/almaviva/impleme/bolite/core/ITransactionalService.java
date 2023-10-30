package it.almaviva.impleme.bolite.core;

public interface ITransactionalService {
	void caseFileOutstandingDebtUpdateState(String idPosizioneDebitoria, String stateTypeOutstandingDebt);
}
