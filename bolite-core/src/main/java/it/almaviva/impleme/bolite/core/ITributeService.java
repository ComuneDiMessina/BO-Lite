package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.domain.dto.TributeDTO;

import java.util.List;

public interface ITributeService {
	
	List<TributeDTO> getTributi(String codice);

}
