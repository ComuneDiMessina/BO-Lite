package it.almaviva.impleme.bolite.core;

import java.math.BigDecimal;
import java.util.UUID;

public interface IJobStarterService{

    
    void createBookingCaseFile(UUID idCaseFile, String dirPath, BigDecimal amount, String enteTributo02);

    void createPraticaGenerica(UUID idCaseFile, String dirPath, String period, String ente, String idDebt, String codice);

    void createPraticaAutomatica(UUID idCaseFile, String dirPath, String idDebt);

}
