package it.almaviva.impleme.bolite.core.impl.casefile;

import it.almaviva.impleme.bolite.core.IEmailService;
import it.almaviva.impleme.bolite.core.IJobStarterService;
import it.almaviva.impleme.bolite.core.IOutstandingDebtService;
import it.almaviva.impleme.bolite.core.casefile.ICaseFileService;
import it.almaviva.impleme.bolite.core.mappers.ICaseFileMapper;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.DebitoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.FruitoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.IndirizzoDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewCaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewSpontaneoDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.SpontaneoDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.*;
import it.almaviva.impleme.bolite.integration.entities.room.EnteEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TariffeEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeId;
import it.almaviva.impleme.bolite.integration.repositories.ITributeRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileStateRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileTypeRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IEnteRepository;
import it.almaviva.impleme.bolite.utils.RollBackEvent;
import it.almaviva.impleme.bolite.zeebe.outadapter.MessageClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Transactional
@Service
@Slf4j
public class CaseFileService implements ICaseFileService {

    @Value("${attachment.base}")
    private String homeDir;

    @Value("${attachment.tmp}")
    private String attachmentDir;

    @Value("${tributi.diritti}")
    private String codiceTributoDirittiSegreteria;



    private final ApplicationEventPublisher applicationEventPublisher;


    private final ICaseFileStateRepository iCaseFileStateRepository;
    private final ICaseFileTypeRepository iCaseFileTypeRepository;
    private final ICaseFileRepository iCaseFileRepository;
    private final IEnteRepository iEnteRepository;
    private final ICaseFileMapper iCaseFileMapper;

    private final EntityManager entityManager;
    private final IJobStarterService iJobStarterService;
    private final MessageClient messageClient;
    private final ITributeRepository iTributeRepository;
    private final IOutstandingDebtService iOutstandingDebtService;
    private final IEmailService iEmailService;



    public CaseFileService(ApplicationEventPublisher applicationEventPublisher, ICaseFileStateRepository iCaseFileStateRepository, ICaseFileTypeRepository iCaseFileTypeRepository, ICaseFileRepository iCaseFileRepository, IEnteRepository iEnteRepository, ICaseFileMapper iCaseFileMapper, EntityManager entityManager, IJobStarterService iJobStarterService, MessageClient messageClient, ITributeRepository iTributeRepository, IOutstandingDebtService iOutstandingDebtService, IEmailService iEmailService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.iCaseFileStateRepository = iCaseFileStateRepository;
        this.iCaseFileTypeRepository = iCaseFileTypeRepository;
        this.iCaseFileRepository = iCaseFileRepository;
        this.iEnteRepository = iEnteRepository;
        this.iCaseFileMapper = iCaseFileMapper;
        this.entityManager = entityManager;
        this.iJobStarterService = iJobStarterService;
        this.messageClient = messageClient;
        this.iTributeRepository = iTributeRepository;
        this.iOutstandingDebtService = iOutstandingDebtService;
        this.iEmailService = iEmailService;
    }

    @Override
    public void deleteDirectory(String filePath) {
    	FileSystemUtils.deleteRecursively(new File(filePath));
    }

    @Override
    @SneakyThrows
    public CaseFileDTO getCaseFile(UUID idCaseFile) {
    	final CaseFileEntity entity = iCaseFileRepository.findByCodice(idCaseFile).orElseThrow(()->new NotFoundException(idCaseFile.toString()));
        CaseFileDTO caseDTO = iCaseFileMapper.map(entity);
    	Set<CaseFileUserEntity> users = entity.getUsers();
    	for (CaseFileUserEntity caseFileUserEntity : users) {
			if(caseFileUserEntity.getFlag_richiedente()) {
				RichiedenteDTO richiedente = iCaseFileMapper.mapRichiedente(caseFileUserEntity);
				caseDTO.setRichiedente(richiedente);
			}
		}
        
        return caseDTO;
    }

    @Override
    @SneakyThrows
    public List<CaseFileDTO> getMiePratiche(String codiFisc, Integer stato, String enteId, LocalDateTime start, LocalDateTime end, Integer tipo) {
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CaseFileEntity> query = builder.createQuery(CaseFileEntity.class);
        Root<CaseFileEntity> root = query.from(CaseFileEntity.class);

        List<Predicate> predicates = new ArrayList<>(0);
        
        final Join<CaseFileEntity, CaseFileUserEntity> users = root.join("users", JoinType.INNER);
        Predicate isCodiFisc = builder.equal(users.get("cf"), codiFisc);
        Predicate isRichiedente = builder.equal(users.get("flag_richiedente"), true);
        predicates.add(isCodiFisc);
        predicates.add(isRichiedente);

        final Join<CaseFileEntity, CaseFileTypeEntity> tipoPratica = root.join("caseFileTypeEntity", JoinType.INNER);
        Predicate isTipoCasefile = builder.notEqual(tipoPratica.get("id"), CaseFileTypeEntity.ROOM_BOOKING);
        predicates.add(isTipoCasefile);

        if(enteId != null){

            final EnteEntity enteEntity = iEnteRepository.findByCodice(enteId).orElseThrow(()->new NotFoundException(enteId));
            final Join<CaseFileEntity, EnteEntity> enteJoin = root.join("ente", JoinType.INNER);
            Predicate isEnte = builder.equal(enteJoin.get("id"),  enteEntity.getId());
            predicates.add(isEnte);
        }


        if(stato != null){
            final Join<CaseFileEntity, CaseFileStateEntity> cfStato = root.join("stato", JoinType.INNER);
            Predicate isStato = builder.equal(cfStato.get("id"), stato);
            predicates.add(isStato);
        }

        if(tipo != null){
            final Join<CaseFileEntity, CaseFileTypeEntity> cfTipo = root.join("caseFileTypeEntity", JoinType.INNER);
            Predicate isTipo = builder.equal(cfTipo.get("id"), tipo);
            predicates.add(isTipo);
        }

        if(start != null && end != null) {
            Predicate isBetween = builder.between(root.get("creationDate"), start, end);
            predicates.add(isBetween);
        }

        query.distinct(true).where(
                builder.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        List<CaseFileDTO> casefilesDTO = new ArrayList<>();
        final List<CaseFileEntity> resultList = entityManager.createQuery(query.select(root)).getResultList();
        for (CaseFileEntity caseFileEntity : resultList) {
        	CaseFileDTO caseDTO = iCaseFileMapper.map(caseFileEntity);
        	Set<CaseFileUserEntity> utenti = caseFileEntity.getUsers();
        	for (CaseFileUserEntity caseFileUserEntity : utenti) {
    			if(caseFileUserEntity.getFlag_richiedente()) {
    				RichiedenteDTO richiedente = iCaseFileMapper.mapRichiedente(caseFileUserEntity);
    				caseDTO.setRichiedente(richiedente);
    			}
    		}
        	casefilesDTO.add(caseDTO);
		}
        return casefilesDTO;
    }

    @Override
    @SneakyThrows
    public List<CaseFileDTO> filterPratiche(String codiFisc, Integer stato, LocalDateTime start, LocalDateTime end, String ente, String tributo, String pratica, Integer tipo) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CaseFileEntity> query = builder.createQuery(CaseFileEntity.class);
        Root<CaseFileEntity> root = query.from(CaseFileEntity.class);

        List<Predicate> predicates = new ArrayList<>(0);


        final EnteEntity enteEntity = iEnteRepository.findByCodice(ente).orElseThrow(()->new NotFoundException(ente));
        final Join<CaseFileEntity, EnteEntity> enteJoin = root.join("ente", JoinType.INNER);
        Predicate isEnte = builder.equal(enteJoin.get("id"),  enteEntity.getId());
        predicates.add(isEnte);

        
        if(tributo != null) {
            Predicate isTributo = builder.equal(root.get("tributo_id"), tributo);
            predicates.add(isTributo);
        }

        if(pratica != null) {
            final Join<CaseFileEntity, CaseFileTypeEntity> cfTipoPratica = root.join("caseFileTypeEntity", JoinType.INNER);
            Predicate isPratica = builder.equal(cfTipoPratica.get("codice"), pratica);
            predicates.add(isPratica);
        }

        if(codiFisc != null){
            final Join<CaseFileEntity, CaseFileUserEntity> users = root.join("users", JoinType.INNER);
            Predicate isCodiFisc = builder.equal(users.get("cf"), codiFisc);
            Predicate isRichiedente = builder.equal(users.get("flag_richiedente"), true);
            predicates.add(isCodiFisc);
            predicates.add(isRichiedente);
        }


        if(stato != null){
            final Join<CaseFileEntity, CaseFileStateEntity> cfStato = root.join("stato", JoinType.INNER);
            Predicate isStato = builder.equal(cfStato.get("id"), stato);
            predicates.add(isStato);
        }

        if(tipo != null){
            final Join<CaseFileEntity, CaseFileTypeEntity> cfTipo = root.join("caseFileTypeEntity", JoinType.INNER);
            Predicate isTipo = builder.equal(cfTipo.get("id"), tipo);
            predicates.add(isTipo);
        }

        if(start != null && end != null) {
            Predicate isBetween = builder.between(root.get("creationDate"), start, end);
            predicates.add(isBetween);
        }

        query.distinct(true).where(
                builder.and(predicates.toArray(new Predicate[predicates.size()]))
        );

        List<CaseFileDTO> casefilesDTO = new ArrayList<>();
        final List<CaseFileEntity> resultList = entityManager.createQuery(query.select(root)).getResultList();
        for (CaseFileEntity caseFileEntity : resultList) {
        	CaseFileDTO caseDTO = iCaseFileMapper.map(caseFileEntity);
        	Set<CaseFileUserEntity> users = caseFileEntity.getUsers();
        	for (CaseFileUserEntity caseFileUserEntity : users) {
    			if(caseFileUserEntity.getFlag_richiedente()) {
    				RichiedenteDTO richiedente = iCaseFileMapper.mapRichiedente(caseFileUserEntity);
    				caseDTO.setRichiedente(richiedente);
    			}
    		}
        	casefilesDTO.add(caseDTO);
		}
        return casefilesDTO;
    }

    @Override
    public SpontaneoDTO createSpontaneousCaseFile(NewSpontaneoDTO body) {

        final Integer quantita = body.getQuantita();
        final Integer tariffa = body.getTariffa();

        TributeId tributeId = new TributeId();
        tributeId.setId(body.getTributo());
        tributeId.setEnte(body.getEnte());

        final TributeEntity tributeEntity = iTributeRepository.findById(tributeId).orElseThrow(()->new NotFoundException(body.getTributo()));
        final Set<TariffeEntity> tariffe = tributeEntity.getTariffe();

        final BigDecimal importo = body.getImporto().stripTrailingZeros();

        if(tariffe != null && !tariffe.isEmpty()) {
            if(tariffa == null){
                throw new OverlapException("Errore: per il servizio selezionato è obbligatorio scegliere una tariffa");
            }

            if(quantita == null){
                throw new OverlapException("Errore: per il servizio selezionato è obbligatorio inserire la quantità");
            }

            final TariffeEntity t = tariffe.stream().filter(tariffeEntity -> tariffeEntity.getId().equals(tariffa)).findFirst().orElseThrow(()-> new OverlapException("La tariffa non è valida per il servizio selezionato"));
            if(!t.getImportoIsEditable()){
                BigDecimal tImporto = t.getImporto();
                final BigDecimal multiply = BigDecimal.valueOf(quantita).multiply(tImporto).stripTrailingZeros();
                if(!multiply.equals(importo)){
                    throw new OverlapException("Errore: l'importo inserito non è valido");

                }
            }
        }

        UUID codicePratica = UUID.randomUUID();

        final CaseFileStateEntity INSERITA = iCaseFileStateRepository.findById(3).orElseThrow(()->new NotFoundException("Stato Inserita not found"));
        final CaseFileTypeEntity AUTO = iCaseFileTypeRepository.findByCodice(CaseFileTypeEntity.GENERIC).orElseThrow(()->new NotFoundException("Tipo pratica AUTO non trovata"));
        final EnteEntity enteEntity = iEnteRepository.findByCodice(body.getEnte()).orElseThrow(()->new NotFoundException(body.getEnte()));

        CaseFileEntity cfe = new CaseFileEntity();
        cfe.setCodice(codicePratica);
        cfe.setImporto(importo);
        cfe.setStato(INSERITA);
        cfe.setCreationDate(LocalDateTime.now());
        cfe.setCaseFileTypeEntity(AUTO);
        cfe.setEnte(enteEntity);
        cfe.setCausale(body.getCausale());
        cfe.setTributo_id(body.getTributo());
        cfe.setRata("00");
        cfe.setDetails(body.getDetails().toString());

        final DebitoreDTO richiedenteDTO = body.getDebitore();
        CaseFileUserEntity richiedenteEntity = new CaseFileUserEntity();
        richiedenteEntity.setNome(richiedenteDTO.getName());
        richiedenteEntity.setSurname(richiedenteDTO.getSurname());
        richiedenteEntity.setTelephoneNumber(richiedenteDTO.getTelephoneNumber());
        richiedenteEntity.setEmail(richiedenteDTO.getEmail());
        richiedenteEntity.setCf(richiedenteDTO.getCodiFisc());
        final IndirizzoDTO indirizzo = richiedenteDTO.getIndirizzo();
        richiedenteEntity.setResidenza_stato(indirizzo.getStato());
        richiedenteEntity.setResidenza_provincia(indirizzo.getProvincia());
        richiedenteEntity.setResidenza_comune(indirizzo.getComune());
        richiedenteEntity.setResidenza_cap(indirizzo.getCap());
        richiedenteEntity.setResidenza_civico(indirizzo.getCivico());
        richiedenteEntity.setResidenza_address(indirizzo.getIndirizzo());
        richiedenteEntity.setFlag_richiedente(true);
        cfe.addUsers(richiedenteEntity);


        iCaseFileRepository.save(cfe);


        final String idDebt = iOutstandingDebtService.createOutstandingDebt(codicePratica, importo, "APERTA", false);
        final String iuv = iOutstandingDebtService.openOutsandingDebt(codicePratica, idDebt);
        final CaseFileOutstandingDebtEntity byIuv = iOutstandingDebtService.findById(idDebt);

        Period period = Period.between(byIuv.getCreationDate().toLocalDate(), byIuv.getDueDate());
        iOutstandingDebtService.updateOutstandingDebtIUV(idDebt, iuv);
        iEmailService.sendUserNotificatore(richiedenteDTO.getCodiFisc(),richiedenteDTO.getEmail());
        iJobStarterService.createPraticaAutomatica(codicePratica, period.toString(), idDebt);

        SpontaneoDTO spontaneoDTO = new SpontaneoDTO();
        spontaneoDTO.setCodiceFiscale(richiedenteDTO.getCodiFisc());
        spontaneoDTO.setImporto(importo);
        spontaneoDTO.setIuv(iuv);
        spontaneoDTO.setTributo(body.getTributo());
        return spontaneoDTO;
    }

    @Override
    @SneakyThrows
    public SpontaneoDTO createManualeCaseFile(NewCaseFileDTO body) {
        UUID codicePratica = UUID.randomUUID();

        final CaseFileStateEntity INSERITA = iCaseFileStateRepository.findById(1).orElseThrow(()->new NotFoundException("Stato inserita non trovato"));
        final CaseFileTypeEntity tipologiaPratica = iCaseFileTypeRepository.findById(body.getTipologia()).orElseThrow(()->new NotFoundException("Tipologia di pratica non trovata"));
        final EnteEntity enteEntity = iEnteRepository.findByCodice(body.getEnte()).orElseThrow(()->new NotFoundException("Codice ente errato: "+body.getEnte()));


        BigDecimal importoDiritti = tipologiaPratica.getImporto();

        CaseFileEntity cfe = new CaseFileEntity();
        cfe.setCodice(codicePratica);
        cfe.setImporto(importoDiritti);
        cfe.setStato(INSERITA);
        cfe.setCreationDate(LocalDateTime.now());
        cfe.setCaseFileTypeEntity(tipologiaPratica);
        cfe.setEnte(enteEntity);
        cfe.setRata("00");
        cfe.setDetails("{}");
        cfe.setCausale(body.getCausale());
        cfe.setTributo_id(codiceTributoDirittiSegreteria);

        final String path = saveAttachments(codicePratica, body.getAttachments());
        final FruitoreDTO richiedente = body.getRichiedente();

        CaseFileUserEntity richiedenteEntity = new CaseFileUserEntity();
        richiedenteEntity.setNome(richiedente.getName());
        richiedenteEntity.setSurname(richiedente.getSurname());
        richiedenteEntity.setTelephoneNumber(richiedente.getTelephoneNumber());
        richiedenteEntity.setEmail(richiedente.getEmail());
        richiedenteEntity.setCf(richiedente.getCodiFisc());
        final IndirizzoDTO indirizzo = richiedente.getIndirizzo();
        richiedenteEntity.setResidenza_stato(indirizzo.getStato());
        richiedenteEntity.setResidenza_provincia(indirizzo.getProvincia());
        richiedenteEntity.setResidenza_comune(indirizzo.getComune());
        richiedenteEntity.setResidenza_cap(indirizzo.getCap());
        richiedenteEntity.setResidenza_civico(indirizzo.getCivico());
        richiedenteEntity.setResidenza_address(indirizzo.getIndirizzo());
        richiedenteEntity.setFlag_richiedente(true);
        cfe.addUsers(richiedenteEntity);


        final DebitoreDTO fruitore = body.getFruitore();
        CaseFileUserEntity fruitoreEntity = richiedenteEntity;
        if(!richiedente.getFlagFruitore()) {
            fruitoreEntity = new CaseFileUserEntity();
            fruitoreEntity.setNome(fruitore.getName());
            fruitoreEntity.setSurname(fruitore.getSurname());
            fruitoreEntity.setTelephoneNumber(fruitore.getTelephoneNumber());
            fruitoreEntity.setEmail(fruitore.getEmail());
            fruitoreEntity.setCf(fruitore.getCodiFisc());
            final IndirizzoDTO indirizzoDelegato = fruitore.getIndirizzo();
            fruitoreEntity.setResidenza_stato(indirizzoDelegato.getStato());
            fruitoreEntity.setResidenza_provincia(indirizzoDelegato.getProvincia());
            fruitoreEntity.setResidenza_comune(indirizzoDelegato.getComune());
            fruitoreEntity.setResidenza_cap(indirizzoDelegato.getCap());
            fruitoreEntity.setResidenza_civico(indirizzoDelegato.getCivico());
            fruitoreEntity.setResidenza_address(indirizzoDelegato.getIndirizzo());
            fruitoreEntity.setFlag_richiedente(false);
        }

        cfe.addUsers(fruitoreEntity);

        iCaseFileRepository.save(cfe);

        final String idDebt = iOutstandingDebtService.createOutstandingDebt(codicePratica, importoDiritti, "APERTA", true);
        final String iuv = iOutstandingDebtService.openOutsandingDebt(codicePratica, idDebt);
        final CaseFileOutstandingDebtEntity byIuv = iOutstandingDebtService.findById(idDebt);

        Period period = Period.between(byIuv.getCreationDate().toLocalDate(), byIuv.getDueDate());
        iOutstandingDebtService.updateOutstandingDebtIUV(idDebt, iuv);
        iEmailService.sendUserNotificatore(richiedenteEntity.getCf(),richiedenteEntity.getEmail());


        iJobStarterService.createPraticaGenerica(codicePratica, path,period.toString(), enteEntity.getCodice(), idDebt,  tipologiaPratica.getCodice());

        SpontaneoDTO spontaneoDTO = new SpontaneoDTO();
        spontaneoDTO.setCodiceFiscale(richiedenteEntity.getCf());
        spontaneoDTO.setImporto(importoDiritti);
        spontaneoDTO.setIuv(iuv);
        spontaneoDTO.setTributo(codiceTributoDirittiSegreteria);
        return spontaneoDTO;
    }

    @Override
    public String saveAttachments(UUID codice, List<NewAttachmentDTO> attachments) throws IOException {
        String path = createFolder(codice.toString());
        applicationEventPublisher.publishEvent(new RollBackEvent(path));
        if(attachments != null)
            for (NewAttachmentDTO att : attachments) {
                saveAttachment(att, path);
            }

        return path;
    }

    private void saveAttachment(NewAttachmentDTO attachmentDTO, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(
                path + File.separator + attachmentDTO.getFileName() + "d=" + attachmentDTO.getDescription())) {
            fos.write(Base64.getDecoder().decode(attachmentDTO.getBase64Content()));
        }

    }

    private String createFolder(String idCaseFile) throws IOException {
        String folderName = "CaseFile_" + idCaseFile;

        File file = new File(
                homeDir + File.separator + attachmentDir + File.separator + folderName + File.separator);
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("Directory creata con successo: " + file.getAbsolutePath());
            } else {
                throw new IOException(
                        "Errore nella creazione della directory nel File System! (" + file.getAbsolutePath() + ")");
            }
        }

        return file.getPath();
    }


    @Override
    public List<AttachmentDTO> getCaseFileAttachments(UUID idCaseFile) {
    	final CaseFileEntity entity = iCaseFileRepository.findByCodice(idCaseFile).orElseThrow(()->new NotFoundException(idCaseFile.toString()));
    	List<AttachmentDTO> allegati = iCaseFileMapper.mapAllegati(entity.getDocumentEntities());
        return allegati;
    }

    @SneakyThrows
    @Override
    public void loadCaseFileAttachments(UUID idCaseFile, List<NewAttachmentDTO> body) {
        final String path = saveAttachments(idCaseFile, body);
        messageClient.sendIntegrationRcv(idCaseFile, path);
    }

    @Override
    public void updateCaseFile(UUID codice, Integer statoPratica, String message) {
        CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).orElseThrow(()->new NotFoundException(codice.toString()));
        final CaseFileStateEntity caseFileStateEntity = iCaseFileStateRepository.findById(statoPratica).orElseThrow(()->new NotFoundException(statoPratica.toString()));
        caseFileEntity.setStato(caseFileStateEntity);
        if(message != null && !message.isEmpty()) {
            caseFileEntity.setNote(message);
        }

        iCaseFileRepository.save(caseFileEntity);
    }

    @Override
    public void updateRegistry(UUID codice, String numero, LocalDateTime dateTime) {
        CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).orElseThrow(()->new NotFoundException(codice.toString()));
        pratica.setDataProtocollo(dateTime);
        pratica.setNumeroProtocollo(numero);
        iCaseFileRepository.save(pratica);
    }
}
