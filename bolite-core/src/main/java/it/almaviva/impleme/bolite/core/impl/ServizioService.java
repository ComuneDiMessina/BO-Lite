package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IServizioService;
import it.almaviva.impleme.bolite.core.mappers.ICaseFileTypeMapper;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.domain.dto.NewServizioDTO;
import it.almaviva.impleme.bolite.domain.dto.ServizioDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileTypeEntity;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServizioService implements IServizioService {

    private final ICaseFileTypeRepository iCaseFileTypeRepository;
    private final ICaseFileTypeMapper iCaseFileTypeMapper;

    public ServizioService(ICaseFileTypeRepository iCaseFileTypeRepository, ICaseFileTypeMapper iCaseFileTypeMapper) {
        this.iCaseFileTypeRepository = iCaseFileTypeRepository;
        this.iCaseFileTypeMapper = iCaseFileTypeMapper;
    }


    @Override
    public List<ServizioDTO> getServizi() {
        final List<CaseFileTypeEntity> all = iCaseFileTypeRepository.findAll();
        all.removeIf(e -> e.getHidden());
        return iCaseFileTypeMapper.map(all);
    }

    @Override
    public ServizioDTO createServizio(NewServizioDTO body) {

        CaseFileTypeEntity entity = new CaseFileTypeEntity();
        entity.setName(body.getNome());
        entity.setDescription(body.getDescrizione());
        entity.setFreeText(body.getLibero());
        entity.setNotes(body.getNote());
        entity.setCodice(body.getCodice());
        entity.setImg(body.getImg());
        entity.setHidden(false);
        entity.setImporto(body.getImporto());

        return iCaseFileTypeMapper.map(iCaseFileTypeRepository.save(entity));
    }

    @Override
    public ServizioDTO updateServizio(Integer id, ServizioDTO body) {
        CaseFileTypeEntity entity = iCaseFileTypeRepository.findById(id).orElseThrow(()->new NotFoundException(id));
        entity.setName(body.getNome());
        entity.setDescription(body.getDescrizione());
        entity.setFreeText(body.getLibero());
        entity.setNotes(body.getNote());
        entity.setCodice(body.getCodice());
        entity.setImg(body.getImg());
        entity.setHidden(false);
        entity.setImporto(body.getImporto());
        return iCaseFileTypeMapper.map(iCaseFileTypeRepository.save(entity));
    }

    @Override
    public void deleteServizio(Integer id) {
        iCaseFileTypeRepository.findById(id).orElseThrow(()->new NotFoundException(id));
        iCaseFileTypeRepository.deleteById(id);
    }
}
