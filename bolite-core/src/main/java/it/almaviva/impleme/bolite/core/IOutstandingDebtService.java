package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;

import java.math.BigDecimal;
import java.util.UUID;

public interface IOutstandingDebtService {
	String createOutstandingDebt(UUID codice, BigDecimal amount, String stato, Boolean dirittiSegreteria);
	void updateOutstandingDebtIUV(String idDebt, String iuv);

	void updateOutstandingDebtState(String idDebt, String stato);
	CaseFileOutstandingDebtEntity findById (String idRichiesta);
    CaseFileOutstandingDebtEntity findByIuv (String iuv);
	String openOutsandingDebt(UUID codice, String idDeb);

	void cancelOutsandingDebt(UUID fromString, String idDebt);
}
