package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IJobStarterService;
import it.almaviva.impleme.bolite.zeebe.outadapter.StarterClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
public class JobStarterService implements IJobStarterService {

    @Autowired
    private StarterClient starterClient;


	@Override
	public void createBookingCaseFile(UUID idCaseFile, String dirPath, BigDecimal amount, String suffix) {
		starterClient.startBookingWf(idCaseFile, dirPath, amount, "OPERATORE_"+suffix);
	}

	@Override
	public void createPraticaGenerica(UUID idCaseFile, String dirPath, String period, String ente, String idDebt, String codice) {
		starterClient.startPraticaGenerica(idCaseFile, dirPath, period, "OPERATORE_"+ente+"_"+codice, idDebt);
	}

	@Override
	public void createPraticaAutomatica(UUID idCaseFile, String period, String idDebt) {
		starterClient.startPraticaAutomatica(idCaseFile, period, idDebt);
	}
}
