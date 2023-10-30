package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.impleme.bolite.core.IDocumentService;
import it.almaviva.impleme.bolite.core.IDocumentaleService;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class DocumentController implements IDocumentController{

	private final IDocumentService documentService;

	private final IDocumentaleService documentaleService;

	public DocumentController(IDocumentService documentService, IDocumentaleService documentaleService) {
		this.documentService = documentService;
		this.documentaleService = documentaleService;
	}


	@Override
	@PermitAll
	public AttachmentDTO getDocument(String idDocument) {
		return documentService.findByDto(idDocument);
	}

	@Override
	@PermitAll
	public ResponseEntity<Object> getDocumentContent(String idDocument) {

		AttachmentDTO attachmentDTO;
		byte[] content;
		try {
			attachmentDTO = documentService.findByDto(idDocument);
			content = documentaleService.getContent(idDocument);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
		}

		ByteArrayResource resource = new ByteArrayResource(content);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition","attachment; filename="+attachmentDTO.getFileName());

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(content.length)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}
}
