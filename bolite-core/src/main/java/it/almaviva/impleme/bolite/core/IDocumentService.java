package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileDocumentEntity;

import java.util.List;
import java.util.UUID;

public interface IDocumentService {
	CaseFileDocumentEntity createDocument (AttachmentDTO documentEntity, UUID idCasefile);
	List<CaseFileDocumentEntity> createDocumentList (List<AttachmentDTO> AttachmentDTOs, UUID idCasefile);
	AttachmentDTO findByDto(String id);
	CaseFileDocumentEntity findBy(String id);
//	public AttachmentDTO getContent(String id);
}
