package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.eai.ljsa.sdk.core.security.LjsaUser;
import it.almaviva.eai.pm.core.grpc.Group;
import it.almaviva.eai.pm.core.grpc.Role;
import it.almaviva.impleme.bolite.core.casefile.ICaseFileService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.core.throwable.UnauthorizedException;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewCaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewSpontaneoDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.SpontaneoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class CaseFileController implements ICaseFileController {

    private final ICaseFileService iCasefileService;

    public CaseFileController(ICaseFileService iCasefileService) {
        this.iCasefileService = iCasefileService;
    }


    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA","AMMINISTRATORE_SERVIZIO","OPERATORE","CITTADINO"})
    public CaseFileDTO getCaseFile(UUID idCaseFile) {
        return iCasefileService.getCaseFile(idCaseFile);
    }

    @Override
    @RolesAllowed({"CITTADINO"})
    public List<CaseFileDTO> getMiePratiche(String codiFisc, Integer stato, String enteId, LocalDateTime start, LocalDateTime end, Integer tipo) {
        LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final String fiscalNumber = (String) ljsaUser.getClaims().get("fiscalNumber");
        if(fiscalNumber == null){
            throw new UnauthorizedException("Utente non autorizzato");
        }

        if(!(fiscalNumber).equalsIgnoreCase("TINIT-"+codiFisc)){
            throw new UnauthorizedException("Utente non autorizzato");
        }
        return iCasefileService.getMiePratiche(codiFisc, stato, enteId, start, end, tipo);
    }

    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA","AMMINISTRATORE_SERVIZIO","OPERATORE"})
    public List<CaseFileDTO> filterPratiche(String codiFisc, Integer stato, Integer tipo, LocalDateTime start, LocalDateTime end) {
    	
        LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
		List<Role> roles = (List<Role>) ljsaUser.getRoles().stream().filter(role -> role instanceof Role)
				.map(Role.class::cast).collect(Collectors.toList());
        
        final Group group = (Group) ljsaUser.getGroups().stream().findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Gruppo"));
        //SIF07
        Group.DomainValue dvC = group.getDomainvaluesList().stream().filter(domainValue -> domainValue.getDomain().getName().equals("COMUNE")).findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Domain Value"));
        
		if (roles.stream().filter(r -> r.getName().equals("SUPER_AMMINISTRATORE")).findFirst().isPresent()) {
			return iCasefileService.filterPratiche(codiFisc, stato, start, end,  dvC.getValue(),null, null, tipo);
		}

        final Optional<Group.DomainValue> tributo = group.getDomainvaluesList().stream().filter(domainValue -> domainValue.getDomain().getName().equals("TRIBUTO")).findFirst();

        final Optional<Group.DomainValue> pratica =  group.getDomainvaluesList().stream().filter(domainValue -> domainValue.getDomain().getName().equals("PRATICA")).findFirst();

        return iCasefileService.filterPratiche(codiFisc, stato, start, end,  dvC.getValue(),tributo.isPresent()? tributo.get().getValue(): null, pratica.isPresent()? pratica.get().getValue(): null, tipo);
    }

    @Override
    public SpontaneoDTO createManualeCaseFile(@Valid  NewCaseFileDTO caseFileDTO) {
        if(!caseFileDTO.getRichiedente().getFlagFruitore() && caseFileDTO.getFruitore() == null){
            throw new OverlapException(
                    "Valorizzare il campo fruitore");
        }

        checkPrivacy(caseFileDTO.getFlagPrivacy1(), caseFileDTO.getFlagPrivacy2(), caseFileDTO.getFlagPrivacy3(), caseFileDTO.getFlagPrivacy4());

        return iCasefileService.createManualeCaseFile(caseFileDTO);
    }

    @Override
    public SpontaneoDTO createSpontaneousCaseFile(NewSpontaneoDTO caseFileDTO) {
       return iCasefileService.createSpontaneousCaseFile(caseFileDTO);
    }


    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SISTEMA","AMMINISTRATORE_SERVIZIO","OPERATORE","CITTADINO"})
    public List<AttachmentDTO> getCaseFileAttachments(UUID idCaseFile) {
        return iCasefileService.getCaseFileAttachments(idCaseFile);
    }

    @Override
    @RolesAllowed({"CITTADINO","OPERATORE"})
    public void loadCaseFileAttachments(UUID idCaseFile, List<NewAttachmentDTO> body)  {
         iCasefileService.loadCaseFileAttachments(idCaseFile, body);
    }

    private void checkPrivacy(Boolean flagPrivacy1, Boolean flagPrivacy2, Boolean flagPrivacy3, Boolean flagPrivacy4) {

        if(flagPrivacy1 == false || flagPrivacy2 == false ||  flagPrivacy3 == false ||  flagPrivacy4 == false ){
            throw new OverlapException(
                    "Accettare i campi privacy");
        }
    }
}
