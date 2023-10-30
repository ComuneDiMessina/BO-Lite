package it.almaviva.impleme.bolite.core.casefile;

import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewCaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewSpontaneoDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.SpontaneoDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ICaseFileService {

    void deleteDirectory(String filePath);

    CaseFileDTO getCaseFile(UUID idCaseFile);

    List<CaseFileDTO> getMiePratiche(String codiFisc, Integer stato, String enteId, LocalDateTime start, LocalDateTime end, Integer tipo);

    List<CaseFileDTO> filterPratiche(String codiFisc, Integer stato, LocalDateTime start, LocalDateTime end, String ente, String tributo, String pratica, Integer tipo);

    SpontaneoDTO createSpontaneousCaseFile(NewSpontaneoDTO caseFileDTO);

    SpontaneoDTO createManualeCaseFile(NewCaseFileDTO caseFileDTO);

    String saveAttachments(UUID codice, List<NewAttachmentDTO> attachments) throws IOException;

    List<AttachmentDTO> getCaseFileAttachments(UUID idCaseFile);

    void loadCaseFileAttachments(UUID idCaseFile, List<NewAttachmentDTO> body);

    void updateCaseFile(UUID codice, Integer statoPratica, String message);

    void updateRegistry(UUID fromString, String numero, LocalDateTime dateTime);
}
