package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.ITributeService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.domain.dto.TributeDTO;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.repositories.ITributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
public class TributeService implements ITributeService {
	
	private final ITributeRepository iTributeRepository;
	
	public TributeService(ITributeRepository iTributeRepository) {
		this.iTributeRepository = iTributeRepository; 
	}
	
	@Override
	public List<TributeDTO> getTributi(String codice) {
		
		List<TributeDTO> result = new ArrayList<>();
		final List<TributeEntity> tributi = iTributeRepository.findByEnte(codice).orElseThrow(()->new NotFoundException(codice));
		
		for (TributeEntity tributeEntity : tributi) {
			TributeDTO t = new TributeDTO();
			t.setId(tributeEntity.getTributeId().getId());
			t.setDescrizioneTributo(tributeEntity.getDescrizioneTributo());
			t.setDescrizioneTributoEstesa(tributeEntity.getDescrizioneTributoEstesa());
			t.setTipo(tributeEntity.getTipo());
			t.setDataCreazione(LocalDateTime.now());
			result.add(t);
		}
		
		return result;
	}
	
	

}
