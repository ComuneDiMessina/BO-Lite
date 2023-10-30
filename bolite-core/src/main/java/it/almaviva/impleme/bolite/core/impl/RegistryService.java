package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IDocumentaleService;
import it.almaviva.impleme.bolite.core.IRegistryService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.RegistryDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.integration.client.rest.IProtocolazioneClient;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.protocollazione.model.*;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.utils.ProtocollazioneProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Transactional
@Service
public class RegistryService implements IRegistryService {


    private final IProtocolazioneClient iProtocolazioneClient;
    private final ICaseFileRepository iCaseFileRepository;
    private final IDocumentaleService documentaleService;
    private final ProtocollazioneProperties protocollazioneProperties;


    public RegistryService(IProtocolazioneClient iProtocolazioneClient, ICaseFileRepository iCaseFileRepository, IDocumentaleService documentaleService, ProtocollazioneProperties protocollazioneProperties) {
        this.iProtocolazioneClient = iProtocolazioneClient;
        this.iCaseFileRepository = iCaseFileRepository;
        this.documentaleService = documentaleService;
        this.protocollazioneProperties = protocollazioneProperties;
    }


    @Override
    public RegistryDTO registryMessage(CaseFileDTO caseFileDTO) {
        return null;
    }

    @Override
    public RegistryDTO registryAttachment(CaseFileDTO caseFileDTO) {
        return null;
    }


    @Override
    public RegistryDTO protocollaPratica(UUID caseFileId, ArrayList<AttachmentDTO> pdfPratica) {


        RegistryDTO registryDTO = new RegistryDTO();
            registryDTO.setEsito("TEST");
            registryDTO.setMessaggio("TEST");
            registryDTO.setNumero("TEST");
            registryDTO.setAnno("1900");
            registryDTO.setData("01/01/1900");
            registryDTO.setOra("00:00");

        return registryDTO;
    }

    @Override
    public void protocollaAllegati(String anno, String numero, ArrayList<AttachmentDTO> allegati) {

    }

    @Override
    public RegistryDTO registryCaseFile(UUID codice, String dirPath) {
        CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).orElseThrow(()->new NotFoundException(codice.toString()));
        ArrayList<AttachmentDTO> allegati = new ArrayList<>();
        ArrayList<AttachmentDTO> pdfPratica = new ArrayList<>();
        manageAttachmentFiles(pdfPratica, allegati, caseFileEntity);
        RegistryDTO registryDTO = protocollaPratica(codice, pdfPratica);
        protocollaAllegati(registryDTO.getAnno(), registryDTO.getNumero(), allegati);
        return registryDTO;
    }

    private void manageAttachmentFiles(ArrayList<AttachmentDTO> pdfPratica,
                                       ArrayList<AttachmentDTO> allegati, CaseFileEntity caseFileEntity) {

        caseFileEntity.getDocumentEntities().forEach(document -> {

            byte[] fileContent = documentaleService.getContent(document.getIdDocument());
            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setFileName(document.getName());
            attachmentDTO.setBase64Content(Base64.getEncoder().encodeToString(fileContent));
            attachmentDTO.setContent(fileContent);
            attachmentDTO.setDescription(document.getDescrizione());
            if (document.getName().startsWith("CaseFile_" + caseFileEntity.getCodice())) {
                pdfPratica.add(attachmentDTO);
            } else {
                allegati.add(attachmentDTO);
            }
        });

    }

}
