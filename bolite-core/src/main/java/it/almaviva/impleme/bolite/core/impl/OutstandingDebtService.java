package it.almaviva.impleme.bolite.core.impl;

import it.almaviva.impleme.bolite.core.IOutstandingDebtService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.integration.client.rest.IPmPayClient;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeId;
import it.almaviva.impleme.bolite.integration.enums.EOutstandingDebtStates;
import it.almaviva.impleme.bolite.integration.pmpay.model.AnnullaPosizioneDebitoriaRequest;
import it.almaviva.impleme.bolite.integration.pmpay.model.AnnullaPosizioneDebitoriaResponse;
import it.almaviva.impleme.bolite.integration.pmpay.model.PosizioneDebitoriaRequest;
import it.almaviva.impleme.bolite.integration.pmpay.model.PosizioneDebitoriaResponse;
import it.almaviva.impleme.bolite.integration.repositories.ITributeRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.IOutstandingDebtRepository;
import it.almaviva.impleme.bolite.utils.BoliteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class OutstandingDebtService implements IOutstandingDebtService {

	private final ICaseFileRepository iCaseFileRepository;
	private final IPmPayClient pmPayClient;
	private final IOutstandingDebtRepository iOutstandingDebtRepository;
	private final ITributeRepository iTributeRepository;

	public OutstandingDebtService(ICaseFileRepository iCaseFileRepository, IPmPayClient pmPayClient, IOutstandingDebtRepository iOutstandingDebtRepository, ITributeRepository iTributeRepository) {
		this.iCaseFileRepository = iCaseFileRepository;
		this.pmPayClient = pmPayClient;
		this.iOutstandingDebtRepository = iOutstandingDebtRepository;
		this.iTributeRepository = iTributeRepository;
	}

	@Override
	public String openOutsandingDebt(UUID codice, String idDebt) {
		CaseFileOutstandingDebtEntity outstandingDebtEntity = findById(idDebt);
		CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).orElseThrow(()->new NotFoundException(codice.toString()));
		final CaseFileUserEntity caseFileUserEntity = pratica.getUsers().stream().filter(entity -> entity.getFlag_richiedente()).findFirst()
				.orElseThrow(()->new NotFoundException("Richiedente not found"));

		String ente = pratica.getEnte().getCodice();

		TributeEntity tributeEntity = outstandingDebtEntity.getTributeEntity();
		String servizioPagamento = tributeEntity.getDescrizioneTributo();

		String idTributo = tributeEntity.getTributeId().getId();
		String descrTributo = tributeEntity.getDescrizioneTributo();
		String annoTributo = tributeEntity.getAnno();
		String numeroTributo = tributeEntity.getTributeId().getId();
		String numeroPosizione = ""; //refuso
		String anagraficaDebitore = caseFileUserEntity.getNome() + " " + caseFileUserEntity.getSurname();
		String cfPivaDebitore = caseFileUserEntity.getCf();
		String indirizzoDebitore = caseFileUserEntity.getResidenza_address();
		String localitaDebitore = caseFileUserEntity.getResidenza_comune();
		String provinciaLocalita = caseFileUserEntity.getResidenza_provincia();
		String dataEmissione = LocalDate.now().format( DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String importoDebito = outstandingDebtEntity.getAmount().toPlainString();
		String dataScadenza = outstandingDebtEntity.getDueDate().toString();
		dataScadenza = dataScadenza.split("-")[2]+"/"+dataScadenza.split("-")[1]+"/"+dataScadenza.split("-")[0];
		String causaleDebito = pratica.getCausale();
		String rata = pratica.getRata(); //00 nessuna rata
		String codContabilita = "";//refuso
		String dataNotifica = "";//refuso
		String emailRt = caseFileUserEntity.getEmail(); // serve per i pagamenti spontanei
		String descrizioneRt = tributeEntity.getDescrizioneRT(); // serve per i pagamenti spontanei
		String iuvRataUnica = "";//refuso


		PosizioneDebitoriaRequest posizioneDebitoriaRequest = new PosizioneDebitoriaRequest(ente, idTributo, descrTributo, annoTributo,
				numeroTributo, numeroPosizione, anagraficaDebitore, cfPivaDebitore, indirizzoDebitore, localitaDebitore,
				provinciaLocalita, dataEmissione, importoDebito, dataScadenza, causaleDebito, rata, codContabilita,
				dataNotifica, emailRt, descrizioneRt, iuvRataUnica, servizioPagamento);

		log.info("PosizioneDebitoriaRequest ----> "+ BoliteUtils.formatJSON(posizioneDebitoriaRequest));
		ResponseEntity<PosizioneDebitoriaResponse> responseEntity = pmPayClient.insertAndSend(posizioneDebitoriaRequest);
		if(!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Errore nel servizio openOutsandingDebt");
		}
		if(responseEntity.getBody().getEsito().equals("KO")){
			throw new RuntimeException("Errore nel servizio openOutsandingDebt");
		}
		log.info("PosizioneDebitoriaResponse ----> "+BoliteUtils.formatJSON(responseEntity.getBody()));
		final String iuv = responseEntity.getBody().getIuv();
		outstandingDebtEntity.setIuv(iuv);
		iOutstandingDebtRepository.save(outstandingDebtEntity);
		return iuv;
	}

	@Override
	public void cancelOutsandingDebt(UUID codice, String idDebt) {
		CaseFileOutstandingDebtEntity outstandingDebtEntity = findById(idDebt);
		CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).get();
		String ente = pratica.getEnte().getCodice();
		AnnullaPosizioneDebitoriaRequest request = new AnnullaPosizioneDebitoriaRequest();
		request.setEnte(ente);
		request.setIuv(outstandingDebtEntity.getIuv());
		log.info("AnnullaPosizioneDebitoriaRequest ----> "+ BoliteUtils.formatJSON(request));
		ResponseEntity<AnnullaPosizioneDebitoriaResponse> responseEntity = pmPayClient.delete(request);
		if(!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Errore nel servizio cancelOutsandingDebt");
		}
		log.info("AnnullaPosizioneDebitoriaResponse ----> "+BoliteUtils.formatJSON(responseEntity.getBody()));
	}


	@Override
	public String createOutstandingDebt(UUID codice, BigDecimal amount, String stato, Boolean speseIstruttoria) {
		CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).get();


		CaseFileOutstandingDebtEntity caseFileOutstandingDebtEntity = new CaseFileOutstandingDebtEntity();

		caseFileOutstandingDebtEntity.setCreationDate(LocalDateTime.now());
		caseFileOutstandingDebtEntity.setAmount(amount);
		caseFileOutstandingDebtEntity.setState(EOutstandingDebtStates.valueOf(stato));
		caseFileOutstandingDebtEntity.setCaseFileEntity(caseFileEntity);

		final TributeEntity tributeEntity;
		if(speseIstruttoria){
			tributeEntity = iTributeRepository.findDirittiDiSegreteria(caseFileEntity.getEnte().getCodice()).orElseThrow(()->new NotFoundException("Codice tributo errato :"+ caseFileEntity.getTributo_id()));
			caseFileOutstandingDebtEntity.setCausale("Pagamento Spese di Istruttoria");
		}else{
			TributeId tributeId = new TributeId();
			tributeId.setEnte(caseFileEntity.getEnte().getCodice());
			tributeId.setId(caseFileEntity.getTributo_id());

			tributeEntity= iTributeRepository.findById(tributeId).orElseThrow(()->new NotFoundException("Codice tributo errato :"+ caseFileEntity.getTributo_id()));
			caseFileOutstandingDebtEntity.setCausale(caseFileEntity.getCausale());
		}

		caseFileOutstandingDebtEntity.setTributeEntity(tributeEntity);
		int giorniScadenza =  tributeEntity.getGiorni_scadenza();
		caseFileOutstandingDebtEntity.setDueDate(LocalDate.now().plusDays(giorniScadenza));


		if(!speseIstruttoria) {
			caseFileEntity.setImporto(amount);
			iCaseFileRepository.save(caseFileEntity);
		}

		final CaseFileOutstandingDebtEntity save = iOutstandingDebtRepository.save(caseFileOutstandingDebtEntity);

		return save.getIdOutstandingDebt();
	}


	@Override
	public void updateOutstandingDebtIUV(String idDebt, String iuv) {
		CaseFileOutstandingDebtEntity outstandingDebtEntity = findById(idDebt);
		outstandingDebtEntity.setIuv(iuv);

		final CaseFileEntity caseFileEntity = outstandingDebtEntity.getCaseFileEntity();
		caseFileEntity.setIuv(iuv);

		iOutstandingDebtRepository.save(outstandingDebtEntity);
		iCaseFileRepository.save(caseFileEntity);

	}



	@Override
	public void updateOutstandingDebtState(String idDebt, String stato) {
		CaseFileOutstandingDebtEntity outstandingDebtEntity = findById(idDebt);
		outstandingDebtEntity.setState(EOutstandingDebtStates.valueOf(stato));
		iOutstandingDebtRepository.save(outstandingDebtEntity);
	}

	@Override
	public CaseFileOutstandingDebtEntity findById(String idRichiesta) {
		return iOutstandingDebtRepository.findById(idRichiesta).orElseThrow(() -> new RuntimeException("Error 404: not found"));
	}

	@Override
	public CaseFileOutstandingDebtEntity findByIuv(String iuv) {
		return iOutstandingDebtRepository.findByIuv(iuv).orElseThrow(() -> new RuntimeException("Error 404: not found"));
	}


}
