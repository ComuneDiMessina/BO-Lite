package it.almaviva.impleme.bolite.zeebe.inadapter;

import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.annotation.ZeebeWorker;
import it.almaviva.impleme.bolite.core.*;
import it.almaviva.impleme.bolite.core.casefile.ICaseFileService;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.RegistryDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;
import it.almaviva.impleme.bolite.integration.repositories.ITributeRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.utils.BoliteUtils;
import it.almaviva.impleme.bolite.utils.ThrowingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@EnableZeebeClient
@Component
@Transactional
@Slf4j
public class CaseFileWorkerClient extends AbstractInadapterClient {


	private final ICaseFileService caseFileService;
	private final IDocumentaleService documentaleService;
	private final IDocumentService documentService;
	private final IEmailService emailService;
	private final IOutstandingDebtService outstandingDebtService;
	private final ICaseFileRepository iCaseFileRepository;
	private final IReportService iReportService;
	private final IRegistryService iRegistryService;
	private final ITransactionalService iTransactionalService;
	private final ITributeRepository iTributeRepository;
	private final IQrcodeService iQrcodeService;

	public CaseFileWorkerClient(ICaseFileService caseFileService, IDocumentaleService documentaleService, IDocumentService documentService, IEmailService emailService, IOutstandingDebtService outstandingDebtService, ICaseFileRepository iCaseFileRepository, IReportService iReportService, IRegistryService iRegistryService, ITransactionalService iTransactionalService, ITributeRepository iTributeRepository,IQrcodeService iQrcodeService) {
		this.caseFileService = caseFileService;
		this.documentaleService = documentaleService;
		this.documentService = documentService;
		this.emailService = emailService;
		this.outstandingDebtService = outstandingDebtService;
		this.iCaseFileRepository = iCaseFileRepository;
		this.iReportService = iReportService;
		this.iRegistryService = iRegistryService;
		this.iTransactionalService = iTransactionalService;
		this.iTributeRepository = iTributeRepository;
		this.iQrcodeService = iQrcodeService;
	}

	/**
	 * Genera il PDF della pratica
	 * @param jobClient
	 * @param activatedJob
	 */
	@ZeebeWorker(type = "casefile-persist")
	public void handleCasefilePersist(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		String caseFileId = inputVariablesMap.get("idCaseFile").toString();
		String dirPath = inputVariablesMap.get("dirPath").toString();
		String pdf_template = headersMap.get("pdf_template");
		iReportService.generateReport(UUID.fromString(caseFileId), pdf_template, dirPath);
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}
	
	
	@ZeebeWorker(type = "qrcode-persist")
	public void handleQrcodePersist(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();

		iQrcodeService.generateQrcode(UUID.fromString(idCaseFile));
		
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}


	@ZeebeWorker(type = "casefile-update")
	public void handleCasefileUpdate(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		String message = (String) inputVariablesMap.getOrDefault("message", headersMap.get("message"));
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();

		final Integer statoPratica = Integer.valueOf(headersMap.get("stateType"));
		caseFileService.updateCaseFile(UUID.fromString(idCaseFile), statoPratica , message);

		if(statoPratica == 4) {
			outputVariablesMap.put("amount", iCaseFileRepository.findByCodice(UUID.fromString(idCaseFile)).get().getImporto().toString());
		}
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	@ZeebeWorker(type = "pdf-attachment-management")
	public void handlePdfAttachmentManagement(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		String filePath = inputVariablesMap.get("dirPath").toString();
		String caseFileId = inputVariablesMap.get("idCaseFile").toString();

		/*
		 * prendo gli allegati dalla cartella e popolo il dto con i l base 64 prendo il
		 * pdf generato dal path tmp e popolo la propieta del base64 creo un metodo che
		 * richiama il documentale per fare fare l'upload atomico
		 */

		ArrayList<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();

		log.info("Invoking manageAttachmentFiles");
		manageAttachmentFiles(attachments, filePath);

		log.info("delete the tmp dir");
		caseFileService.deleteDirectory(filePath);

		outputVariablesMap.put("pathCaseFilePDF", "");
		outputVariablesMap.put("pathAttachments", "");

		log.info("persit the documents ;)");
		documentService.createDocumentList(attachments, UUID.fromString(caseFileId));


		CaseFileEntity caseFileEntity =  iCaseFileRepository.findByCodice(UUID.fromString(caseFileId)).get();

		//Nel primo caso è sempre null
		if (caseFileEntity.getNumeroProtocollo()!=null) {
			String numero = caseFileEntity.getNumeroProtocollo();
			final String anno = String.valueOf(caseFileEntity.getDataProtocollo().getYear());
			RegistryDTO registryDTO = new RegistryDTO();
			registryDTO.setNumero(numero);
			registryDTO.setAnno(anno);
			iRegistryService.protocollaAllegati(anno, numero, attachments);
		}

		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	@ZeebeWorker(type = "casefile-registry")
	public void handleCasefileRegistry(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();
		String dirPath = inputVariablesMap.get("dirPath").toString();


		RegistryDTO registryDTO = iRegistryService.registryCaseFile(UUID.fromString(idCaseFile), dirPath);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(registryDTO.getData()+" "+registryDTO.getOra(), formatter);

		caseFileService.updateRegistry(UUID.fromString(idCaseFile), registryDTO.getNumero(), dateTime);

		outputVariablesMap.put("idRegistry", registryDTO.getNumero());

		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	@ZeebeWorker(type = "email-send")
	public void handleEmailSend(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		String notificationType = headersMap.get("notificationType");
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();
		String iuv = inputVariablesMap.get("iuv") != null ? inputVariablesMap.get("iuv").toString() : null;
		String idDebt = (String) inputVariablesMap.getOrDefault("idDebt", null);
		
		final CaseFileEntity pratica = iCaseFileRepository.findByCodice(UUID.fromString(idCaseFile)).get();
        String codiceEnte = pratica.getEnte().getCodice();
        
		//emailService.sendEmailWithSMTP(UUID.fromString(idCaseFile), idDebt, notificationType);
		emailService.sendEmail(UUID.fromString(idCaseFile), idDebt, notificationType, notificationType+"_"+codiceEnte, iuv);
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	@ZeebeWorker(type = "pg-email-send")
	public void handlePraticaGenericaEmailSend(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		String notificationType = headersMap.get("notificationType");
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();

		final CaseFileEntity pratica = iCaseFileRepository.findByCodice(UUID.fromString(idCaseFile)).get();
		String codiceEnte = pratica.getEnte().getCodice();

		//emailService.sendEmailWithSMTP(UUID.fromString(idCaseFile), idDebt, notificationType);
		emailService.sendPraticaGenericaEmail(UUID.fromString(idCaseFile), notificationType, notificationType+"_"+codiceEnte);
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}



	private void manageAttachmentFiles(ArrayList<AttachmentDTO> attachments, String filePath) {
		File file = new File(filePath);
		log.info("_-_-_- Location: " + filePath + "_-_-_-");
		File[] children = file.listFiles();

		if (children != null) {

			Arrays.stream(children).forEach(ThrowingConsumer.unchecked(child -> {
				String fileName = child.getName();
				String fileNameSplit[] = fileName.split("d=");
				String base64 = BoliteUtils.readFileToBase64(child.getAbsolutePath());
				AttachmentDTO attachmentDTO = new AttachmentDTO();
				attachmentDTO.setDescription(fileNameSplit.length == 2 ? fileNameSplit[1] : "");
				attachmentDTO.setFileName(fileNameSplit[0]);
				attachmentDTO.setBase64Content(base64);
				log.info("Sending attachments to Documentale....");
				String id = documentaleService.uploadDocument(attachmentDTO.getBase64Content(),
						attachmentDTO.getFileName());
				attachmentDTO.setIdDocument(id);
				log.info("Upload finished!");
				// spostare nella cartella padre
				log.info("Moving the file to father dir");
				child.renameTo(new File(file.getParent(), fileNameSplit[0]));
				attachments.add(attachmentDTO);
			}));

		}
	}

	@ZeebeWorker(type = "debt-persist")
	public void handleDebtPersist(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		String stato = headersMap.get("stateType");

		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();
		String amount = inputVariablesMap.get("amount").toString();
		final String dirittiSegreteria = headersMap.get("dirittiSegreteria");


		String  idDebt = outstandingDebtService.createOutstandingDebt(UUID.fromString(idCaseFile), new BigDecimal(amount), stato, dirittiSegreteria.equals("SI") ? true:false);
		outputVariablesMap.put("idDebt", idDebt);

		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	@ZeebeWorker(type = "debt-open")
	public void handleDebtOpen(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		
		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();
		String idDebt = inputVariablesMap.get("idDebt").toString();
		final String dirittiSegreteria = headersMap.get("dirittiSegreteria");

		String iuv = outstandingDebtService.openOutsandingDebt(UUID.fromString(idCaseFile), idDebt);
		outputVariablesMap.put("iuv", iuv);

		final CaseFileOutstandingDebtEntity byIuv = outstandingDebtService.findById(idDebt);

		Period period = Period.between(byIuv.getCreationDate().toLocalDate(), byIuv.getDueDate());
		outputVariablesMap.put("remainingTime", period.toString());


		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}


	@ZeebeWorker(type = "debt-cancel")
	public void handleDebtCancel(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();

		String idCaseFile = inputVariablesMap.get("idCaseFile").toString();
		String idDebt = (String) inputVariablesMap.getOrDefault("idDebt", null);
		if(idDebt != null) {
			outstandingDebtService.cancelOutsandingDebt(UUID.fromString(idCaseFile), idDebt);
		}
		jobClient.newCompleteCommand(activatedJob.getKey()).send().join();

	}


	@ZeebeWorker(type = "debt-update-iuv")
	public void handleDebtUpdateIUV(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();

		String idPosizioneDebitoria = inputVariablesMap.get("idDebt").toString();
		String iuv = inputVariablesMap.get("iuv").toString();

		outstandingDebtService.updateOutstandingDebtIUV(idPosizioneDebitoria, iuv);

		jobClient.newCompleteCommand(activatedJob.getKey()).send().join();

	}

	@ZeebeWorker(type = "cf-debt-update-state")
	public void handleCfDebtUpdateState(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);

		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();

		Map<String, String> headersMap = activatedJob.getCustomHeaders();
		
		
		String idPosizioneDebitoria = (String) inputVariablesMap.get("idDebt");
		String stateTypeOutstandingDebt = headersMap.get("stateTypeOutstandingDebt");

		if(stateTypeOutstandingDebt.equals("CHIUSA")){
			outputVariablesMap.put("idDebt",null);
		}
		if(idPosizioneDebitoria != null) {
			iTransactionalService.caseFileOutstandingDebtUpdateState(idPosizioneDebitoria, stateTypeOutstandingDebt);
		}
		jobClient.newCompleteCommand(activatedJob.getKey()).variables(outputVariablesMap).send().join();

	}

	
	@ZeebeWorker(type = "diritti-segreteria")
	public void handleDirittiSegreteria(final JobClient jobClient, final ActivatedJob activatedJob) {
		logJob(activatedJob);
/*
		Map<String, Object> inputVariablesMap = activatedJob.getVariablesAsMap();
		Map<String, Object> outputVariablesMap = new HashMap<String, Object>();
		String caseFileId = inputVariablesMap.get("idCaseFile").toString();

		CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(UUID.fromString(caseFileId)).get();
		final String tributo_id = caseFileEntity.getTributo_id();
		final EnteEntity ente = caseFileEntity.getEnte();

		TributeId tributeId = new TributeId();
		tributeId.setEnte(ente.getCodice());
		tributeId.setId(tributo_id);
		final TributeEntity tributeEntity = iTributeRepository.findById(tributeId).get();
		if(tributeEntity.getPaga_diritti_segreteria() == true){
			final TributeEntity ds = iTributeRepository.findDirittiDiSegreteria(ente.getCodice()).get();
			final BigDecimal diritti_segreteria = ds.getTariffe().getImporto();
			outputVariablesMap.put("pagamentoDiritti", true);
			outputVariablesMap.put("amount", diritti_segreteria);
		}
		else{
			outputVariablesMap.put("pagamentoDiritti", false);
		}



 */
		jobClient.newCompleteCommand(activatedJob.getKey()).send().join();
	}

}