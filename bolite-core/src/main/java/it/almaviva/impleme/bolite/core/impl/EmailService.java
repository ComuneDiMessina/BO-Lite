package it.almaviva.impleme.bolite.core.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import it.almaviva.impleme.bolite.core.IEmailService;
import it.almaviva.impleme.bolite.integration.client.rest.INotificatoreClient;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileOutstandingDebtEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileStateEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import it.almaviva.impleme.bolite.integration.entities.room.RoomEntity;
import it.almaviva.impleme.bolite.integration.entities.templates.EntiTemplatesEntity;
import it.almaviva.impleme.bolite.integration.notificatore.model.*;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.IOutstandingDebtRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IEnteTemplateRepository;
import it.almaviva.impleme.bolite.utils.BoliteUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Transactional
@Service
@Slf4j
public class EmailService implements IEmailService {

	private String subjectRegistrata = "";
	private String subjectIntegrazione = "";
	private String subjectPagabile = "";
	private String subjectAnnullata = "";
	private String subjectConfermata = "";
	private String subjectRifiutata = "";

	private final INotificatoreClient iNotificatoreClient;
	private final ICaseFileRepository iCaseFileRepository;

	private final Configuration freemarkerConfig;

	private final JavaMailSender javaMailSender;

	private final IEnteTemplateRepository iEnteTemplateRepository;
	private final IOutstandingDebtRepository iOutstandingDebtRepository;

	public EmailService(INotificatoreClient iNotificatoreClient, ICaseFileRepository iCaseFileRepository,
			Configuration freemarkerConfig, JavaMailSender javaMailSender,
			IEnteTemplateRepository iEnteTemplateRepository, IOutstandingDebtRepository iOutstandingDebtRepository) {
		this.iNotificatoreClient = iNotificatoreClient;
		this.iCaseFileRepository = iCaseFileRepository;
		this.freemarkerConfig = freemarkerConfig;
		this.javaMailSender = javaMailSender;
		this.iEnteTemplateRepository = iEnteTemplateRepository;
		this.iOutstandingDebtRepository = iOutstandingDebtRepository;
	}

	@SneakyThrows
	@Override
	public void sendEmail(UUID codice, String idDebt, String type, String emailTemplateType, String iuv) {

		CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).get();
		String idPratica = codice.toString();
		String note = pratica.getNote();
		String numeroProtocollo = pratica.getNumeroProtocollo();
		String dataProtocollo = "";
		String roomName = "";
		String dataPrenotazione= "";
		String orarioPrenotazione= "";
		String importo = pratica.getImporto().toString();
		String causale = "";
		String dataScadenza = "";
		String anno = "";

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH.mm");
		
		final BookingEntity booking = pratica.getBooking();
		if (booking != null) {
			final RoomEntity room = booking.getRoom();
			final LocalDate bookingStartDate = booking.getBookingStartDate();
			final LocalDate bookingEndDate = booking.getBookingEndDate();
			final LocalTime bookingStartHour = booking.getBookingStartHour();
			final LocalTime bookingEndHour = booking.getBookingEndHour();
			
			roomName = room.getNome();
			
			if(bookingStartDate.isEqual(bookingEndDate))
				dataPrenotazione = bookingStartDate.format(formatters);
			else
			{
				dataPrenotazione = bookingStartDate.format(formatters)+" - "+bookingEndDate.format(formatters);
			}

			if(!booking.getFlagWeek()) {
				orarioPrenotazione = bookingStartHour.format(formatterHour) + " - " + bookingEndHour.format(formatterHour);
			}

		}
		
		if (idDebt != null) {
			final CaseFileOutstandingDebtEntity debt = iOutstandingDebtRepository.findById(idDebt).get();
			importo = debt.getAmount().toString();
			causale = debt.getCausale();
			dataScadenza = debt.getDueDate().format(formatters);
			iuv = debt.getIuv();
			anno = debt.getTributeEntity().getAnno();
		}
		
		final CaseFileUserEntity richiedente = pratica.getUsers().stream()
				.filter(entity -> entity.getFlag_richiedente()).findFirst().get();

		final EntiTemplatesEntity byEnte_idAndNome = iEnteTemplateRepository
				.findByEnte_IdAndNome(pratica.getEnte().getId(), type).get();
		String oggetto = byEnte_idAndNome.getSub();

		if (type.equalsIgnoreCase("CHIUSA")) {
			String qrCodebase64 = pratica.getQrcode();
			InsertAttachmentRequest insertAttachmentRequest = new InsertAttachmentRequest(qrCodebase64);
			ResponseEntity<InsertAttachmentResponse> responseAttachmentEntity = iNotificatoreClient
					.insertAttachment(insertAttachmentRequest);
			InvioNotificaRequest invioNotificaRequest = new InvioNotificaRequest(richiedente.getCf(), emailTemplateType,
					oggetto);
			ContentParams contentParams = new ContentParams(idPratica, richiedente.getNome(), richiedente.getSurname(),
					roomName, importo, iuv, note, numeroProtocollo, dataProtocollo, dataPrenotazione, orarioPrenotazione, causale, dataScadenza, anno);
			invioNotificaRequest.setContentParams(contentParams);
			invioNotificaRequest.setAttachment(responseAttachmentEntity.getBody().getUuid());
			log.info("InvioNotificaRequest ----> " + BoliteUtils.formatJSON(invioNotificaRequest));
			ResponseEntity<InvioNotificaResponse> responseEntity = iNotificatoreClient
					.invioNotifica(invioNotificaRequest);
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				throw new RuntimeException("Errore nel servizio invioNotifica");
			}
			log.info("InvioNotificaResponse ----> " + BoliteUtils.formatJSON(responseEntity.getBody()));
		} else {
			
			if(numeroProtocollo!=null) {
				final LocalDateTime dp = pratica.getDataProtocollo();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				dataProtocollo = dp.format(formatter);
			}
			
			if (type.equalsIgnoreCase("PS_PROTOCOLLATA") || type.equalsIgnoreCase("PG_PROTOCOLLATA")) {
				oggetto = String.format(oggetto, numeroProtocollo, dataProtocollo);
			}
			
			InvioNotificaRequest invioNotificaRequest = new InvioNotificaRequest(richiedente.getCf(), emailTemplateType,
					oggetto);
			ContentParams contentParams = new ContentParams(idPratica, richiedente.getNome(), richiedente.getSurname(),
					roomName, importo, iuv, note, numeroProtocollo, dataProtocollo,dataPrenotazione,orarioPrenotazione, causale, dataScadenza, anno);
			invioNotificaRequest.setContentParams(contentParams);

			if(pratica.getStato().getId().equals(CaseFileStateEntity.RICHIESTA_PAGAMENTO)) {
				invioNotificaRequest.setAppio("PRENOTA_SPAZIO_APPIO");
			}

			log.info("InvioNotificaRequest ----> " + BoliteUtils.formatJSON(invioNotificaRequest));
			ResponseEntity<InvioNotificaResponse> responseEntity = iNotificatoreClient
					.invioNotifica(invioNotificaRequest);
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				throw new RuntimeException("Errore nel servizio invioNotifica");
			}
			log.info("InvioNotificaResponse ----> " + BoliteUtils.formatJSON(responseEntity.getBody()));
		}

	}

	@Override
	public void sendUserNotificatore(String cf, String email) {
		// TODO Auto-generated method stub

		UtenteNotificatoreRequest utenteNotificatoreRequest = new UtenteNotificatoreRequest(cf, email);
		log.info("UtenteNotificatoreRequest ----> " + BoliteUtils.formatJSON(utenteNotificatoreRequest));
		ResponseEntity<UtenteNotificatoreResponse> responseEntity = iNotificatoreClient
				.utente(utenteNotificatoreRequest);
		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Errore nel servizio sendUserNotificatore");
		}
		log.info("UtenteNotificatoreResponse ----> " + BoliteUtils.formatJSON(responseEntity.getBody()));

	}

	@Override
	@SneakyThrows
	@Deprecated
	public void sendEmailWithSMTP(UUID codice, String idDebt, String template) {

		Map<String, Object> model = new HashMap<>();

		CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).get();
		final CaseFileUserEntity richiedente = pratica.getUsers().stream()
				.filter(entity -> entity.getFlag_richiedente()).findFirst().get();
		final String email = richiedente.getEmail();
		model.put("nome_ric", richiedente.getNome());
		model.put("cognome_ric", richiedente.getSurname());

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		model.put("causale", pratica.getCausale());
		if (idDebt != null) {
			final CaseFileOutstandingDebtEntity debt = iOutstandingDebtRepository.findById(idDebt).get();
			model.put("importo", debt.getAmount());
			model.put("causale", debt.getCausale());
			model.put("data_scadenza", debt.getDueDate());
			model.put("iuv", debt.getIuv());
			model.put("anno", debt.getTributeEntity().getAnno());
		}

		final String numeroProtocollo = pratica.getNumeroProtocollo();
		final LocalDateTime dataProtocollo = pratica.getDataProtocollo();

		final BookingEntity booking = pratica.getBooking();
		if (booking != null) {
			final RoomEntity room = booking.getRoom();
			final LocalDate bookingStartDate = booking.getBookingStartDate();

			model.put("spazio", room.getNome());
			model.put("data_prenotazione", bookingStartDate.format(formatters));
		}

		if (pratica.getQrcode() != null) {

			String imgpath = "\"data:image/png;base64," + pratica.getQrcode() + "\"";
			log.debug(imgpath);
			model.put("qrcode", imgpath);
		}

		final String note = pratica.getNote();
		if (note != null) {
			model.put("note", pratica.getNote());
		}

		final EntiTemplatesEntity byEnte_idAndNome = iEnteTemplateRepository
				.findByEnte_IdAndNome(pratica.getEnte().getId(), template).get();
		Template t = new Template(template, byEnte_idAndNome.getTemplate(), freemarkerConfig);

		String oggetto = byEnte_idAndNome.getSub();

		if (numeroProtocollo != null) {
			model.put("numero_protocollo", numeroProtocollo);
			model.put("data_protocollo", dataProtocollo.format(formatters));
			oggetto = String.format(oggetto, numeroProtocollo, dataProtocollo.format(formatters));
		}

		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

		System.out.println("html email-> " + html);

		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		// helper.setFrom("noreply@gmail.com");
		msg.setFrom("noreply@gmail.com");
		helper.setTo(email);
		helper.setSubject(oggetto);
		helper.setText(html, true);
		javaMailSender.send(msg);

	}

	@Override
	public void sendPraticaGenericaEmail(UUID codice, String notificationType, String emailTemplateType) {
		CaseFileEntity pratica = iCaseFileRepository.findByCodice(codice).get();
		String idPratica = codice.toString();
		String note = pratica.getNote();
		String numeroProtocollo = pratica.getNumeroProtocollo();
		String iuv = pratica.getIuv();
		String dataProtocollo = null;

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter formatterHour = DateTimeFormatter.ofPattern("HH.mm");

		final CaseFileOutstandingDebtEntity debt = pratica.getOutstandingDebtEntities().stream().findFirst().get();
		String importo = debt.getAmount().toString();
		String causale = pratica.getCausale();
		String dataScadenza = debt.getDueDate().format(formatters);
		String anno = debt.getTributeEntity().getAnno();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		final LocalDateTime dp = pratica.getDataProtocollo();
		if(dp != null) {
			dataProtocollo = dp.format(formatter);
		}


		final CaseFileUserEntity richiedente = pratica.getUsers().stream()
						.filter(entity -> entity.getFlag_richiedente()).findFirst().get();

		final EntiTemplatesEntity byEnte_idAndNome = iEnteTemplateRepository
						.findByEnte_IdAndNome(pratica.getEnte().getId(), notificationType).get();
		String oggetto = byEnte_idAndNome.getSub();


			InvioNotificaRequest invioNotificaRequest = new InvioNotificaRequest(richiedente.getCf(), emailTemplateType, oggetto);
			ContentParams contentParams = new ContentParams(idPratica, richiedente.getNome(), richiedente.getSurname(),
							null, importo, iuv, note, numeroProtocollo, dataProtocollo,null,null, causale, dataScadenza, anno);
			invioNotificaRequest.setContentParams(contentParams);


			log.info("InvioNotificaRequest ----> " + BoliteUtils.formatJSON(invioNotificaRequest));
			ResponseEntity<InvioNotificaResponse> responseEntity = iNotificatoreClient
							.invioNotifica(invioNotificaRequest);
			if (!responseEntity.getStatusCode().is2xxSuccessful()) {
				throw new RuntimeException("Errore nel servizio invioNotifica");
			}
			log.info("InvioNotificaResponse ----> " + BoliteUtils.formatJSON(responseEntity.getBody()));

	}

}