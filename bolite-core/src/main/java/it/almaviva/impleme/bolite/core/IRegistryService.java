package it.almaviva.impleme.bolite.core;

import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.RegistryDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;

import java.util.ArrayList;
import java.util.UUID;

public interface IRegistryService {

	RegistryDTO registryMessage(CaseFileDTO caseFileDTO);
	
	RegistryDTO registryAttachment(CaseFileDTO caseFileDTO);


	RegistryDTO protocollaPratica(UUID caseFileId, ArrayList<AttachmentDTO> pdfPratica);

	void protocollaAllegati(String anno, String numero, ArrayList<AttachmentDTO> attachments);

	RegistryDTO registryCaseFile(UUID fromString, String dirPath);
}
