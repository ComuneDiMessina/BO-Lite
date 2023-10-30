package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IDocumentService;
import it.almaviva.impleme.bolite.core.IDocumentaleService;
import it.almaviva.impleme.bolite.core.mappers.IDocumentMapper;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileDocumentEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.IDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional

public class DocumentService implements IDocumentService {

	@Autowired
	IDocumentRepository documentRepository;
	
	@Autowired
	IDocumentMapper documentMapper;
	
	@Autowired
	IDocumentaleService documentaleService;
	
	@Autowired
	ICaseFileRepository iCaseFileRepository;
	
	@Override
	public CaseFileDocumentEntity createDocument(AttachmentDTO documentDTO, UUID idCasefile) {

		CaseFileDocumentEntity doc = new CaseFileDocumentEntity();
		doc.setName(documentDTO.getFileName());
		doc.setIdDocument(documentDTO.getIdDocument());
		doc.setDescrizione(documentDTO.getDescription());

		CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(idCasefile).orElseThrow(()->new NotFoundException(idCasefile.toString()));
		caseFileEntity.addDocumento(doc);

		iCaseFileRepository.save(caseFileEntity);
		return doc;
	}

	@Override
	public List<CaseFileDocumentEntity> createDocumentList(List<AttachmentDTO> attachmentDTOs, UUID idCasefile) {

		List<CaseFileDocumentEntity> caseFileDocumentEntities = new ArrayList<>();

		for (AttachmentDTO documentDTO : attachmentDTOs) {
			CaseFileDocumentEntity caseFileDocumentEntity = createDocument(documentDTO, idCasefile);
			caseFileDocumentEntities.add(caseFileDocumentEntity);
		}

		return caseFileDocumentEntities;
	}

	@Override
	public AttachmentDTO findByDto(String id) {
		// TODO Auto-generated method stub
		return documentMapper.map(findBy(id));
	}

	@Override
	public CaseFileDocumentEntity findBy(String id) {
		return documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Error 404: document not found"));
	}

	

}
