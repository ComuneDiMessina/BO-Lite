package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IDocumentaleService;
import it.almaviva.impleme.bolite.integration.client.rest.documentale.DocumentsApiClient;
import it.almaviva.impleme.bolite.integration.model.documentale.Document;
import it.almaviva.impleme.bolite.integration.model.documentale.ResultDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

@Slf4j
public class DocumentaleService implements IDocumentaleService {


	
	@Autowired
	private DocumentsApiClient documentsApi;

	@Override
	public String uploadDocument(String base64, String filename) {
		Document document = new Document();
		document.setFilename(filename);
		document.setContent(base64);
		document.setUser("admin");
		List<String> group = new ArrayList<>();
		group.add("admin");
		document.setReadgroups(group);
		document.setWritegroups(group);
		ResultDocument res = documentsApi.documentsPost(document).getBody();
		return res.getIdFile();
	}

	@Override
	public byte[] getContent(String id) {
		
		return documentsApi.documentsIdFileContentGet(id, null).getBody();
	}


    
   
 
    
}