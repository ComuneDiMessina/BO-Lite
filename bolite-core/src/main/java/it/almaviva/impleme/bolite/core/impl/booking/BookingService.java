package it.almaviva.impleme.bolite.core.impl.booking;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import it.almaviva.impleme.bolite.core.IEmailService;
import it.almaviva.impleme.bolite.core.IJobStarterService;
import it.almaviva.impleme.bolite.core.booking.IBookingService;
import it.almaviva.impleme.bolite.core.casefile.ICaseFileService;
import it.almaviva.impleme.bolite.core.mappers.ICaseFileMapper;
import it.almaviva.impleme.bolite.core.mappers.IRoomBookingMapper;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.core.throwable.UnauthorizedException;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.DiscardDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.BookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.NewBookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.StatoDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.OpenDoorDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.*;
import it.almaviva.impleme.bolite.integration.client.rest.ISerratureClient;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingServiceEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.*;
import it.almaviva.impleme.bolite.integration.entities.room.*;
import it.almaviva.impleme.bolite.integration.entities.tributes.TributeEntity;
import it.almaviva.impleme.bolite.integration.repositories.ITributeRepository;
import it.almaviva.impleme.bolite.integration.repositories.booking.IBookingRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileStateRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileTypeRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IEnteRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IRoomRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IRoomServiceRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.IRoomTariffRepository;
import it.almaviva.impleme.bolite.integration.serrature.model.RequestOpenDoor;
import it.almaviva.impleme.bolite.integration.serrature.model.ResponseOpenDoor;
import it.almaviva.impleme.bolite.utils.BoliteUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Transactional
@Service
@Slf4j
public class BookingService implements IBookingService {

	private final ICaseFileStateRepository iCaseFileStateRepository;
	private final ICaseFileTypeRepository iCaseFileTypeRepository;
	private final IRoomRepository iRoomRepository;
	private final IBookingRepository iBookingRepository;
	private final ICaseFileRepository iCaseFileRepository;
	private final IEnteRepository iEnteRepository;
	private final IRoomBookingMapper iRoomBookingMapper;
	private final ICaseFileMapper iCaseFileMapper;
	private final ICaseFileService iCaseFileService;
	private final IEmailService iEmailService;
	private final IRoomTariffRepository iRoomTariffRepository;

	private final IRoomServiceRepository iRoomServiceRepository;
	private final IJobStarterService iJobStarterService;
	private final ITributeRepository iTributeRepository;

	private final EntityManager entityManager;

	private final ISerratureClient iSerratureClient;

	@Value("${porta.username}")
	private String username;

	@Value("${porta.password}")
	private String password;

	public BookingService(ICaseFileStateRepository iCaseFileStateRepository,
			ICaseFileTypeRepository iCaseFileTypeRepository,
			IRoomRepository iRoomRepository, ICaseFileRepository iCaseFileRepository, IEnteRepository iEnteRepository,
			IRoomBookingMapper iRoomBookingMapper, ICaseFileMapper iCaseFileMapper, ICaseFileService iCaseFileService,
			IEmailService iEmailService, IRoomTariffRepository iRoomTariffRepository,
			IRoomServiceRepository iRoomServiceRepository, IJobStarterService iJobStarterService,
			IBookingRepository iBookingRepository, ITributeRepository iTributeRepository, EntityManager entityManager,
			ISerratureClient iSerratureClient) {
		this.iCaseFileStateRepository = iCaseFileStateRepository;
		this.iCaseFileTypeRepository = iCaseFileTypeRepository;
		this.iRoomRepository = iRoomRepository;
		this.iCaseFileRepository = iCaseFileRepository;
		this.iEnteRepository = iEnteRepository;
		this.iRoomBookingMapper = iRoomBookingMapper;
		this.iCaseFileMapper = iCaseFileMapper;
		this.iCaseFileService = iCaseFileService;
		this.iEmailService = iEmailService;
		this.iRoomTariffRepository = iRoomTariffRepository;
		this.iRoomServiceRepository = iRoomServiceRepository;
		this.iJobStarterService = iJobStarterService;
		this.iBookingRepository = iBookingRepository;
		this.iTributeRepository = iTributeRepository;
		this.entityManager = entityManager;
		this.iSerratureClient = iSerratureClient;
	}

	@Override
	@SneakyThrows
	@Transactional
	public BookingDTO createRoomBooking(NewBookingDTO body) {

		UUID codicePratica = UUID.randomUUID();

		boolean overlap;
		final RoomEntity roomEntity = iRoomRepository.findByCodice(body.getRoomID())
				.orElseThrow(() -> new NotFoundException("Errore nella prenotazione: la struttura non esiste"));

		if (roomEntity.getBlocked()) {
			throw new OverlapException("Errore nella prenotazione: la struttura è momentaneamente non disponibile");
		}

		final LocalDate giornoA = body.getGiornoA() != null ? body.getGiornoA() : body.getGiornoDa();
		LocalTime oraDa = body.getOraDa(); //possibile null
		LocalTime oraA = body.getOraA(); //possibile null

		if(giornoA.isBefore( body.getGiornoDa())){
			throw new OverlapException("Errore nella prenotazione: intervallo orario errato");
		}

		final Boolean flagInteraGiornata = body.getFlagInteraGiornata();

		final RoomTariffEntity roomTariffEventTypeEntity = iRoomTariffRepository
						.findById(body.getTipoEnvento()).orElseThrow(() -> new NotFoundException("Errore nella prenotazione: la tariffa non esiste"));
		boolean settimanale = roomTariffEventTypeEntity.getFlagInteraSettimana();

		final List<ServiceDTO> serviziPrenotati = body.getServiziPrenotati();
		List<Integer> elencoServiziPrenotati = new ArrayList<>();
		if (serviziPrenotati != null) {
			elencoServiziPrenotati = serviziPrenotati.stream().map(s -> s.getId()).collect(Collectors.toList());
		}

		final BigDecimal importoServizi = calcolaCostoServizi(elencoServiziPrenotati);

		final BigDecimal importo = verificaPrezzo(body.getRoomID(), roomTariffEventTypeEntity.getId(), elencoServiziPrenotati,
						body.getGiornoDa(), giornoA, oraDa, oraA, flagInteraGiornata);


		final Set<RoomAperturaEntity> aperture = roomEntity.getAperture();
		List<BookingEntity> bookings = iBookingRepository.findByRoomId(roomEntity.getId());

		final LocalDate dueDate = LocalDate.now().plusDays(roomEntity.getGiorniAnticipo());
		if (body.getGiornoDa().isBefore(dueDate)) {
			throw new OverlapException("Errore nella prenotazione: occorre prenotare con un minimo di "
							+ roomEntity.getGiorniAnticipo() + " giorni di anticipo: il primo giorno utile è: "+dueDate);
		}


		if (flagInteraGiornata) {

			final RoomAperturaEntity roomAperturaEntity = aperture.stream()
					.filter(a -> a.getDay_of_week().equals(body.getGiornoDa().getDayOfWeek().getValue())).findFirst()
					.orElseThrow(() -> new OverlapException(
							"Errore nella prenotazione: la struttura non è aperta nel giorno selezionato"));
			oraDa = roomAperturaEntity.getOraDa();
			oraA = roomAperturaEntity.getOraA();
		}

		Set<RoomReservationEntity> reservations = roomEntity.getRiserve();



		if(settimanale){

			if(aperture.size()<7){
				throw new OverlapException(
								"Errore nella prenotazione: la struttura non è aperta 7/7");
			}

			final List<LocalDate> datesBetween = getDatesBetween(body.getGiornoDa(), body.getGiornoA());
			if(datesBetween.size() != 7){
				throw new OverlapException(
								"Errore nella prenotazione: selezionare un intervallo di 7 giorni consecutivi");
			}

			//check reservations
			for(LocalDate item: datesBetween){
				final RoomAperturaEntity apertura = aperture.stream()
								.filter(a -> a.getDay_of_week().equals(item.getDayOfWeek().getValue())).findFirst()
								.orElseThrow(() -> new OverlapException(
												"Errore nella prenotazione: la struttura non è aperta nel giorno selezionato"));

				for (RoomReservationEntity roomReservationEntity : reservations) {
					overlap = BoliteUtils.overlapDate(item, item, apertura.getOraDa(), apertura.getOraA(), roomReservationEntity);
					if (overlap) {
						throw new OverlapException("Errore nella prenotazione: la stanza non è diponibile");
					}
				}

				//check bookings
				for (BookingEntity bookingEntity : bookings) {

					final Boolean flagWeek = bookingEntity.getFlagWeek();
					if(flagWeek){
						overlap = BoliteUtils.overlapOnlyDate(item, item, bookingEntity);
					}else {
						overlap = BoliteUtils.overlapDate(item, item, apertura.getOraDa(), apertura.getOraA(), bookingEntity);
					}
					if (overlap) {
						CaseFileEntity caseFile = iCaseFileRepository.findByBookingId(bookingEntity.getId());
						Integer stato = caseFile.getStato().getId();
						if (stato == 5)
							throw new OverlapException("Errore nella prenotazione: la struttura è occupata");
					}
				}
			}
		}else {


			for (RoomReservationEntity roomReservationEntity : reservations) {
				overlap = BoliteUtils.overlapDate(body.getGiornoDa(), giornoA, oraDa, oraA, roomReservationEntity);
				if (overlap) {
					throw new OverlapException("Errore nella prenotazione: la stanza non è diponibile");
				}
			}

			for (BookingEntity bookingEntity : bookings) {
				final Boolean flagWeek = bookingEntity.getFlagWeek();
				if(flagWeek){
					overlap = BoliteUtils.overlapOnlyDate(body.getGiornoDa(), giornoA, bookingEntity);
				}else {
					overlap = BoliteUtils.overlapDate(body.getGiornoDa(), giornoA, oraDa, oraA, bookingEntity);
				}

				if (overlap) {
					CaseFileEntity caseFile = iCaseFileRepository.findByBookingId(bookingEntity.getId());
					Integer stato = caseFile.getStato().getId();
					if (stato == 5)
						throw new OverlapException("Errore nella prenotazione: la struttura è occupata");
				}
			}

		}




		final CaseFileStateEntity INSERITA = iCaseFileStateRepository.findById(3).get();
		final CaseFileTypeEntity ROOM_BOOKING = iCaseFileTypeRepository.findByCodice(CaseFileTypeEntity.ROOM_BOOKING).get();

		CaseFileEntity cfe = new CaseFileEntity();
		cfe.setCodice(codicePratica);
		cfe.setImporto(importo);
		cfe.setStato(INSERITA);
		cfe.setCreationDate(LocalDateTime.now());
		cfe.setCaseFileTypeEntity(ROOM_BOOKING);

		BookingEntity be = new BookingEntity();
		be.setAmount(importo);
		be.setAmountServizi(importoServizi);
		be.setBookingDate(LocalDateTime.now());
		be.setBookingStartDate(body.getGiornoDa());
		be.setBookingEndDate(giornoA);
		be.setBookingStartHour(oraDa);
		be.setBookingEndHour(oraA);
		be.setFlagWeek(settimanale);

		be.setEventDescription(body.getDescrizioneEvento());
		be.setEventTitle(body.getTitoloEvento());

		be.setEventType(roomTariffEventTypeEntity.getEventType());

		final EnteEntity ente = roomEntity.getEnte();
		be.setRoom(roomEntity);
		cfe.setEnte(ente);

		if(serviziPrenotati != null) {
			for (ServiceDTO ser : serviziPrenotati) {
				BookingServiceEntity bookingServiceEntity = new BookingServiceEntity();
				bookingServiceEntity.setAmount(ser.getImporto());
				bookingServiceEntity.setCode(ser.getCodice());
				bookingServiceEntity.setDescription(ser.getDescrizione());
				bookingServiceEntity.setNote(ser.getNote());
				be.addService(bookingServiceEntity);
			}
		}

		final List<NewAttachmentDTO> allegati = body.getAllegati();
		String	path = iCaseFileService.saveAttachments(codicePratica, allegati);

		final RichiedenteDTO richiedenteDTO = body.getRichiedente();

		CaseFileUserEntity richiedenteEntity = new CaseFileUserEntity();
		richiedenteEntity.setNome(richiedenteDTO.getName());
		richiedenteEntity.setSurname(richiedenteDTO.getSurname());
		richiedenteEntity.setBirthPlace(richiedenteDTO.getLuogoNascita());
		richiedenteEntity.setBirthDate(richiedenteDTO.getDataNascita());
		richiedenteEntity.setTelephoneNumber(richiedenteDTO.getTelephoneNumber());
		richiedenteEntity.setEmail(richiedenteDTO.getEmail());
		richiedenteEntity.setCf(richiedenteDTO.getCodiFisc());
		richiedenteEntity.setPiva(richiedenteDTO.getPiva());
		richiedenteEntity.setEnte_ragione_sociale(richiedenteDTO.getRagioneSociale());
		final IndirizzoDTO indirizzo = richiedenteDTO.getIndirizzo();
		richiedenteEntity.setResidenza_stato(indirizzo.getStato());
		richiedenteEntity.setResidenza_provincia(indirizzo.getProvincia());
		richiedenteEntity.setResidenza_comune(indirizzo.getComune());
		richiedenteEntity.setResidenza_cap(indirizzo.getCap());
		richiedenteEntity.setResidenza_civico(indirizzo.getCivico());
		richiedenteEntity.setResidenza_address(indirizzo.getIndirizzo());
		richiedenteEntity.setFlag_richiedente(true);
		richiedenteEntity.setFlag_organizzatore(false);
		richiedenteEntity.setFlag_ente(richiedenteDTO.getFlagEnte());
		cfe.addUsers(richiedenteEntity);

		if (richiedenteDTO.getFlagOrganizzatore() == true) {
			richiedenteEntity.setFlag_organizzatore(true);
		} else {
			OrganizzatoreDTO organizzatoreDTO = body.getOrganizzatore();
			CaseFileUserEntity organizzatoreEntity = new CaseFileUserEntity();
			organizzatoreEntity.setFlag_ente(organizzatoreDTO.getFlagEnte());
			organizzatoreEntity.setNome(organizzatoreDTO.getName());
			organizzatoreEntity.setSurname(organizzatoreDTO.getSurname());
			organizzatoreEntity.setBirthPlace(organizzatoreDTO.getLuogoNascita());
			organizzatoreEntity.setBirthDate(organizzatoreDTO.getDataNascita());
			organizzatoreEntity.setTelephoneNumber(organizzatoreDTO.getTelephoneNumber());
			organizzatoreEntity.setEmail(organizzatoreDTO.getEmail());
			organizzatoreEntity.setCf(organizzatoreDTO.getCodiFisc());
			organizzatoreEntity.setPiva(organizzatoreDTO.getPiva());
			organizzatoreEntity.setEnte_ragione_sociale(organizzatoreDTO.getRagioneSociale());
			final IndirizzoDTO indirizzoOrganizzatore = organizzatoreDTO.getIndirizzo();
			organizzatoreEntity.setResidenza_stato(indirizzoOrganizzatore.getStato());
			organizzatoreEntity.setResidenza_provincia(indirizzoOrganizzatore.getProvincia());
			organizzatoreEntity.setResidenza_comune(indirizzoOrganizzatore.getComune());
			organizzatoreEntity.setResidenza_cap(indirizzoOrganizzatore.getCap());
			organizzatoreEntity.setResidenza_civico(indirizzoOrganizzatore.getCivico());
			organizzatoreEntity.setResidenza_address(indirizzoOrganizzatore.getIndirizzo());
			organizzatoreEntity.setFlag_richiedente(false);
			organizzatoreEntity.setFlag_organizzatore(true);
			organizzatoreEntity.setFlag_ente(organizzatoreDTO.getFlagEnte());
			cfe.addUsers(organizzatoreEntity);
		}

		final LegaleDTO legaleDTO = body.getLegale();
		if (legaleDTO != null) {
			CaseFileUserEntity legaleEntity = new CaseFileUserEntity();
			legaleEntity.setFlag_legale(true);
			legaleEntity.setFlag_presidente(false);
			legaleEntity.setCf(legaleDTO.getCodiFisc());
			legaleEntity.setNome(legaleDTO.getNome());
			legaleEntity.setSurname(legaleDTO.getCognome());
			legaleEntity.setBirthDate(legaleDTO.getDataNascita());
			legaleEntity.setEmail(legaleDTO.getEmail());
			legaleEntity.setBirthPlace(legaleDTO.getLuogoNascita());
			legaleEntity.setTelephoneNumber(legaleDTO.getTelefono());
			cfe.addUsers(legaleEntity);
		}

		final PresidenteDTO presidenteDTO = body.getPresidente();
		if (presidenteDTO != null) {
			CaseFileUserEntity presidenteEntity = new CaseFileUserEntity();
			presidenteEntity.setFlag_presidente(true);
			presidenteEntity.setFlag_legale(false);
			presidenteEntity.setCf(presidenteDTO.getCodiFisc());
			presidenteEntity.setNome(presidenteDTO.getNome());
			presidenteEntity.setSurname(presidenteDTO.getCognome());
			presidenteEntity.setBirthDate(presidenteDTO.getDataNascita());
			presidenteEntity.setEmail(presidenteDTO.getEmail());
			presidenteEntity.setBirthPlace(presidenteDTO.getLuogoNascita());
			presidenteEntity.setTelephoneNumber(presidenteDTO.getTelefono());
			cfe.addUsers(presidenteEntity);
		}

		cfe.setBooking(be);

		final TributeEntity tributeEntity = iTributeRepository.findPrenotazioneSpazio(ente.getCodice()).get();
		cfe.setTributo_id(tributeEntity.getTributeId().getId());
		cfe.setRata("00");
		cfe.setCausale(body.getTitoloEvento());

		final CaseFileEntity save = iCaseFileRepository.save(cfe);
		final BookingDTO result = iRoomBookingMapper.map(save);

		final Set<CaseFileDocumentEntity> documentEntities = save.getDocumentEntities();
		if (documentEntities != null) {
			final List<AttachmentDTO> attachmentDTOS = iCaseFileMapper.mapAllegati(documentEntities);
			result.setAllegati(attachmentDTOS);
		}

		final Set<CaseFileUserEntity> users = save.getUsers();
		for (CaseFileUserEntity u : users) {
			if (u.getFlag_legale().equals(true)) {
				result.setLegale(iRoomBookingMapper.mapLegale(u));
			}

			if (u.getFlag_organizzatore().equals(true)) {
				result.setOrganizzatore(iRoomBookingMapper.mapOrganizzatore(u));
			}

			if (u.getFlag_richiedente().equals(true)) {
				result.setRichiedente(iRoomBookingMapper.mapRichiedente(u));
			}

			if (u.getFlag_presidente().equals(true)) {
				result.setPresidente(iRoomBookingMapper.mapPresidente(u));
			}

		}

		iEmailService.sendUserNotificatore(richiedenteDTO.getCodiFisc(),richiedenteDTO.getEmail());
		iJobStarterService.createBookingCaseFile(codicePratica, path, cfe.getImporto(), ente.getCodice()+"_02");
		return result;
	}

	@Override
	@Transactional
	public BookingDTO getRoomBookingCaseFile(UUID idCaseFile) {
		final CaseFileEntity pratica = iCaseFileRepository
				.findByCodiceAndCaseFileTypeEntity_Codice(idCaseFile, CaseFileTypeEntity.ROOM_BOOKING)
				.orElseThrow(() -> new NotFoundException(idCaseFile.toString()));

		final BookingDTO result = iRoomBookingMapper.map(pratica);

		final Set<CaseFileDocumentEntity> documentEntities = pratica.getDocumentEntities();
		if (documentEntities != null) {
			final List<AttachmentDTO> attachmentDTOS = iCaseFileMapper.mapAllegati(documentEntities);
			result.setAllegati(attachmentDTOS);
		}

		final Set<CaseFileUserEntity> users = pratica.getUsers();
		for (CaseFileUserEntity u : users) {
			if (u.getFlag_legale().equals(true)) {
				result.setLegale(iRoomBookingMapper.mapLegale(u));
			}

			if (u.getFlag_organizzatore().equals(true)) {
				result.setOrganizzatore(iRoomBookingMapper.mapOrganizzatore(u));
			}

			if (u.getFlag_richiedente().equals(true)) {
				result.setRichiedente(iRoomBookingMapper.mapRichiedente(u));
			}

			if (u.getFlag_presidente().equals(true)) {
				result.setPresidente(iRoomBookingMapper.mapPresidente(u));
			}

		}

		return result;
	}

	@Override
	@Transactional
	public List<BookingDTO> getMiePrenotazioni(String cf, String enteId, Integer statoId, UUID roomId,
			LocalDateTime bookingStart, LocalDateTime bookingEnd) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CaseFileEntity> query = builder.createQuery(CaseFileEntity.class);
		Root<CaseFileEntity> root = query.from(CaseFileEntity.class);

		List<Predicate> predicates = new ArrayList<>(0);

		final Join<CaseFileEntity, CaseFileUserEntity> users = root.join("users", JoinType.INNER);
		Predicate isCodiFisc = builder.equal(users.get("cf"), cf);
		Predicate isRichiedente = builder.equal(users.get("flag_richiedente"), true);
		predicates.add(isCodiFisc);
		predicates.add(isRichiedente);

		final Join<CaseFileEntity, CaseFileTypeEntity> tipoJoin = root.join("caseFileTypeEntity", JoinType.INNER);
		Predicate isBooking = builder.equal(tipoJoin.get("id"), CaseFileTypeEntity.ROOM_BOOKING);
		predicates.add(isBooking);

		if (enteId != null) {
			final EnteEntity enteEntity = iEnteRepository.findByCodice(enteId).get();
			final Join<CaseFileEntity, EnteEntity> enteJoin = root.join("ente", JoinType.INNER);
			Predicate isEnte = builder.equal(enteJoin.get("id"), enteEntity.getId());
			predicates.add(isEnte);
		}

		if (statoId != null) {
			final Join<CaseFileEntity, CaseFileStateEntity> cfStato = root.join("stato", JoinType.INNER);
			Predicate isStato = builder.equal(cfStato.get("id"), statoId);
			predicates.add(isStato);
		}

		if (roomId != null) {
			final Join<CaseFileEntity, BookingEntity> cfBookingRoom = root.join("booking", JoinType.INNER);
			final Join<CaseFileEntity, BookingEntity> cfRoom = cfBookingRoom.join("room", JoinType.INNER);
			Predicate isRoomId = builder.equal(cfRoom.get("codice"), roomId);
			predicates.add(isRoomId);
		}

		if (bookingStart != null && bookingEnd != null) {
			final Join<CaseFileEntity, BookingEntity> cfBooking = root.join("booking", JoinType.INNER);
			Predicate isBetween = builder.between(cfBooking.get("bookingDate"), bookingStart, bookingEnd);
			predicates.add(isBetween);
		}

		query.distinct(true).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		final List<CaseFileEntity> casefiles = entityManager.createQuery(query.select(root)).getResultList();

		List<BookingDTO> bookings = new ArrayList<BookingDTO>();
		for (CaseFileEntity c : casefiles) {
			BookingDTO b = iRoomBookingMapper.map(c);
			bookings.add(b);
		}
		
		Collections.sort(bookings, new Comparator<BookingDTO>() {
			  @Override
			  public int compare(BookingDTO b1, BookingDTO b2) {
			    return b2.getDataPrenotazione().compareTo(b1.getDataPrenotazione());
			  }
			});
		
		return bookings;

	}

	@Override
	public List<StatoDTO> getStati() {
		final List<CaseFileStateEntity> lista = iCaseFileStateRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		List<StatoDTO> stati = new ArrayList<StatoDTO>();
		for (CaseFileStateEntity caseFileStateEntity : lista) {
			StatoDTO s = new StatoDTO();
			s.setId(caseFileStateEntity.getId());
			s.setStato(caseFileStateEntity.getLabel());
			stati.add(s);
		}
		return stati;
	}

	@Override
	public List<BookingDTO> filterBookings(String codiFisc, Integer stato, UUID roomId, LocalDateTime bookingStart,
			LocalDateTime bookingEnd, String ente, String tributo) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CaseFileEntity> query = builder.createQuery(CaseFileEntity.class);
		Root<CaseFileEntity> root = query.from(CaseFileEntity.class);

		List<Predicate> predicates = new ArrayList<>(0);

		if (codiFisc != null) {
			final Join<CaseFileEntity, CaseFileUserEntity> users = root.join("users", JoinType.INNER);
			Predicate isCodiFisc = builder.equal(users.get("cf"), codiFisc);
			Predicate isRichiedente = builder.equal(users.get("flag_richiedente"), true);
			predicates.add(isCodiFisc);
			predicates.add(isRichiedente);
		}

		final EnteEntity enteEntity = iEnteRepository.findByCodice(ente).get();
		final Join<CaseFileEntity, EnteEntity> enteJoin = root.join("ente", JoinType.INNER);
		Predicate isEnte = builder.equal(enteJoin.get("id"), enteEntity.getId());
		predicates.add(isEnte);

		final Join<CaseFileEntity, CaseFileTypeEntity> tipoJoin = root.join("caseFileTypeEntity", JoinType.INNER);
		Predicate isBooking = builder.equal(tipoJoin.get("id"), CaseFileTypeEntity.ROOM_BOOKING);
		predicates.add(isBooking);
		
		if(tributo != null) {
        Predicate isTributo = builder.equal(root.get("tributo_id"), tributo);
        predicates.add(isTributo);
		}
		
		if (stato != null) {
			final Join<CaseFileEntity, CaseFileStateEntity> cfStato = root.join("stato", JoinType.INNER);
			Predicate isStato = builder.equal(cfStato.get("id"), stato);
			predicates.add(isStato);
		}

		if (roomId != null) {
			final Join<CaseFileEntity, BookingEntity> cfBookingRoom = root.join("booking", JoinType.INNER);
			final Join<CaseFileEntity, BookingEntity> cfRoom = cfBookingRoom.join("room", JoinType.INNER);
			Predicate isRoomId = builder.equal(cfRoom.get("codice"), roomId);
			predicates.add(isRoomId);
		}

		if (bookingStart != null && bookingEnd != null) {
			final Join<CaseFileEntity, BookingEntity> cfBooking = root.join("booking", JoinType.INNER);
			Predicate isBetween = builder.between(cfBooking.get("bookingDate"), bookingStart, bookingEnd);
			predicates.add(isBetween);
		}

		query.distinct(true).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

		final List<CaseFileEntity> casefiles = entityManager.createQuery(query.select(root)).getResultList();

		List<BookingDTO> bookings = new ArrayList<BookingDTO>();
		for (CaseFileEntity c : casefiles) {
			BookingDTO b = iRoomBookingMapper.map(c);
			bookings.add(b);
		}

		Collections.sort(bookings, new Comparator<BookingDTO>() {
			  @Override
			  public int compare(BookingDTO b1, BookingDTO b2) {
			    return b2.getDataPrenotazione().compareTo(b1.getDataPrenotazione());
			  }
			});
		
		return bookings;
	}

	@Override
	@SneakyThrows
	public byte[] qrCodeGenerator(UUID idCaseFile) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(idCaseFile.toString(), BarcodeFormat.QR_CODE, 200, 200);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

		// Create a byte array output stream.
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	@Override
	public void discardPratica(UUID idCaseFile, DiscardDTO motivo) {
		final CaseFileEntity pratica = iCaseFileRepository
				.findByCodiceAndCaseFileTypeEntity_Codice(idCaseFile, CaseFileTypeEntity.ROOM_BOOKING).get();
		final CaseFileStateEntity stato = pratica.getStato();
		if (!stato.getId().equals(CaseFileStateEntity.VALIDATA)) {
			throw new OverlapException("Si possono revocare solo le pratiche validate");
		}

		final CaseFileStateEntity statoRevocata = iCaseFileStateRepository.findById(CaseFileStateEntity.REVOCATA).get();
		pratica.setStato(statoRevocata);
		pratica.setNote(motivo.getMotivo());
		
		String codiceEnte = pratica.getEnte().getCodice();

		iCaseFileRepository.save(pratica);
		//iEmailService.sendEmailWithSMTP(idCaseFile, null, "DISCARD");
		iEmailService.sendEmail(idCaseFile, null, "DISCARD", "DISCARD_"+codiceEnte, pratica.getIuv());
	}

	private void verificaConsistenza(UUID roomId, Integer eventId, LocalDate dayStart, LocalDate dayEnd,
			LocalTime hourStart, LocalTime hourEnd, Boolean interaGiornata) {

		final RoomEntity roomEntity = iRoomRepository.findByCodice(roomId)
				.orElseThrow(() -> new NotFoundException(roomId.toString()));

		List<LocalDate> datesBetween;
		long daysBetween = 1;
		if (dayEnd.compareTo(dayStart) != 0) {

			if(dayEnd.isBefore(dayStart)){
				throw new OverlapException("Inserire un range di giorni valido");
			}

			daysBetween = ChronoUnit.DAYS.between(dayStart.atStartOfDay(), dayEnd.atStartOfDay())+1;
			datesBetween = getDatesBetween(dayStart, dayEnd);
		} else {
			datesBetween = new ArrayList<>(1);
			datesBetween.add(dayStart);
		}

		final RoomTariffEntity tariffarioEntity = iRoomTariffRepository
				.findById(eventId).orElseThrow(() -> new NotFoundException(eventId));

		if(!tariffarioEntity.getFlagInteraSettimana()){
			if(!interaGiornata){

				if(hourEnd == null || hourStart == null){
					throw new OverlapException("Inserire gli orari di inzio e fine prenotazione");
				}
				if(hourEnd.isBefore(hourStart) || hourStart.compareTo(hourEnd) == 0){
					throw new OverlapException("Inserire orari validi");
				}
			}
		}

		if (interaGiornata && !tariffarioEntity.getFlagInteraGiornata()) {
			throw new OverlapException("La tariffa selezionata non consente la prenotazione giornaliera");
		}

		if (interaGiornata&& daysBetween > 1) {
			throw new OverlapException("Non è possibile prenotare la giornata intera per più giorni");
		}

		if (tariffarioEntity.getFlagInteraSettimana() && datesBetween.size() != 7) {
			throw new OverlapException("Inserire esattamente 7 giorni consecutivi");
		}

		if(interaGiornata == true || tariffarioEntity.getFlagInteraSettimana()){
			return;
		}

		//check fasce selezionate
		if (tariffarioEntity.getFasce() != null && !tariffarioEntity.getFasce().isEmpty()) {
			for (LocalDate ld : datesBetween) {
				final int value = ld.getDayOfWeek().getValue();
				final RoomDailyTariffEntity roomDailyTariffEntity = tariffarioEntity.getFasce().stream()
						.filter(f -> f.getGiorno().equals(value)).findFirst()
						.orElseThrow(() -> new OverlapException("La giornata di "+ld.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())+" non è prenotabile"));
				final List<LocalTime> tutteDa = roomDailyTariffEntity.getFasce().stream().map(o -> o.getOraDa())
						.collect(Collectors.toList());
				final List<LocalTime> tutteA = roomDailyTariffEntity.getFasce().stream().map(o -> o.getOraA())
						.collect(Collectors.toList());
				if (!tutteDa.contains(hourStart) || !tutteA.contains(hourEnd)) {
					throw new OverlapException("La fascia oraria del giorno: " + ld + " non corrisponde all'intervallo orario selezionato");
				}
			}
		}

		// la prenotazione oraria deve rispettare gli orari di apertura/chiusura
		final Set<RoomAperturaEntity> aperture = roomEntity.getAperture();
		for (LocalDate ld : datesBetween) {
			final int value = ld.getDayOfWeek().getValue();
			RoomAperturaEntity rae = aperture.stream().filter(f -> f.getDay_of_week().equals(value)).findFirst()
					.orElseThrow(() -> new OverlapException("Il giorno " + ld + " la struttura è chiusa"));
			if (hourStart.isBefore(rae.getOraDa()) || hourEnd.isAfter(rae.getOraA())) {
				throw new OverlapException(
						"La fascia oraria selezionata non coincide con quelle previste."
								+ ld);
			}
		}

	}

	/**
	 * Non controlla la disponibilità
	 * 
	 * @param roomId
	 * @param eventId
	 * @param services
	 * @param dayStart
	 * @param dayEnd
	 * @param hourStart
	 * @param hourEnd
	 * @param interaGiornata
	 * @return
	 */
	@Override
	public BigDecimal verificaPrezzo(UUID roomId, Integer eventId, List<Integer> services, LocalDate dayStart,
			LocalDate dayEnd, LocalTime hourStart, LocalTime hourEnd, Boolean interaGiornata) {

		verificaConsistenza(roomId, eventId, dayStart, dayEnd, hourStart, hourEnd, interaGiornata);

		BigDecimal importo = BigDecimal.ZERO;

		final RoomEntity roomEntity = iRoomRepository.findByCodice(roomId).get();
		final Set<RoomAperturaEntity> aperture = roomEntity.getAperture();

		List<LocalDate> datesBetween;
		long daysBetween = 1;
		if (dayEnd != null) {
			daysBetween = ChronoUnit.DAYS.between(dayStart.atStartOfDay(), dayEnd.atStartOfDay()) + 1;
			datesBetween = getDatesBetween(dayStart, dayEnd);
		} else {
			datesBetween = new ArrayList<>(1);
			datesBetween.add(dayStart);
		}

		final RoomTariffEntity tariffarioEntity = iRoomTariffRepository
				.findById(eventId).get();
		final Set<RoomDailyTariffEntity> fasce = tariffarioEntity.getFasce();

		//intera giornata prenotabile al più per un solo giorno
		if (tariffarioEntity.getFlagInteraGiornata() && interaGiornata && daysBetween == 1) {
			final BigDecimal costoInteraGiornata = tariffarioEntity.getCostoInteraGiornata();
			importo = importo.add(costoInteraGiornata);

			final BigDecimal costoServizi = calcolaCostoServizi(services);
			final RoomAperturaEntity apertura = aperture.stream()
					.filter(d -> d.getDay_of_week().equals(dayStart.getDayOfWeek().getValue())).findFirst()
					.orElseThrow(() -> new OverlapException(
							"Errore nella prenotazione: la struttura non è aperta nel giorno selezionato"));
			importo = importo.add(costoServizi
					.multiply(BigDecimal.valueOf(ChronoUnit.HOURS.between(apertura.getOraDa(), apertura.getOraA()))));
			return  importo;
		}

		if (!interaGiornata && tariffarioEntity.getFlagInteraSettimana() && tariffarioEntity.getCostoSettimanale() != null && datesBetween.size() == 7) {
			final BigDecimal costoSettimanale = tariffarioEntity.getCostoSettimanale();
			importo = importo.add(costoSettimanale);

			final BigDecimal costoServizi = calcolaCostoServizi(services);

			long oreTotali = 0;
			for (LocalDate ld : datesBetween) {
				final RoomAperturaEntity apertura = aperture.stream()
						.filter(d -> d.getDay_of_week().equals(ld.getDayOfWeek().getValue())).findFirst().get();
				long oraGiorno = ChronoUnit.HOURS.between(apertura.getOraDa(), apertura.getOraA());
				oreTotali = oreTotali + oraGiorno;
			}

			importo = importo.add(costoServizi.multiply(BigDecimal.valueOf(oreTotali)));
			return  importo;
		}

		if (!interaGiornata && tariffarioEntity.getCostoOrario() != null && tariffarioEntity.getCostoOrario().compareTo(BigDecimal.ZERO) > 0) {
			final long hoursBetween = ChronoUnit.HOURS.between(hourStart, hourEnd);
			final BigDecimal totaleOre = BigDecimal.valueOf(daysBetween).multiply(BigDecimal.valueOf(hoursBetween));

			importo = importo.add(tariffarioEntity.getCostoOrario().multiply(totaleOre));
			final BigDecimal costoServizi = calcolaCostoServizi(services);
			importo = importo.add(costoServizi.multiply(totaleOre));
			return  importo;
		}

		// fascia
		if (!interaGiornata && !tariffarioEntity.getFlagInteraGiornata() && !tariffarioEntity.getFlagInteraSettimana() && (tariffarioEntity.getCostoOrario() == null || tariffarioEntity.getCostoOrario().compareTo(BigDecimal.ZERO) == 0)) {
			long oreTotali = 0;
			for (LocalDate ld : datesBetween) {
				final DayOfWeek dayOfWeek = ld.getDayOfWeek();
				final int value = dayOfWeek.getValue();
				final RoomDailyTariffEntity first = fasce.stream().filter(f -> f.getGiorno().equals(value)).findFirst()
						.get();

				for (RoomRowEntity rre : first.getFasce()) {
					if (rre.getOraDa().equals(hourStart) && rre.getOraA().equals(hourEnd)) {
						importo = importo.add(rre.getCostoFascia());
						long oraGiorno = ChronoUnit.HOURS.between(hourStart, hourEnd);
						oreTotali = oreTotali + oraGiorno;
					}
				}
			}

			final BigDecimal costoServizi = calcolaCostoServizi(services);
			importo = importo.add(costoServizi.multiply(BigDecimal.valueOf(oreTotali)));
			return  importo;
		}

		throw new OverlapException(
				"Errore nella prenotazione: la tariffa selezionata non è conforme con la prenotazione");
	}

	private BigDecimal calcolaCostoServizi(List<Integer> services) {
		if (services == null || services.isEmpty()) {
			return BigDecimal.ZERO;
		}

		final List<RoomServiceEntity> allById = iRoomServiceRepository.findAllById(services);
		final BigDecimal reduce = allById.stream().map(s -> s.getImporto()).reduce(BigDecimal.ZERO, BigDecimal::add);
		return reduce;
	}

	public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	@Override
	public void OpenDoor(OpenDoorDTO body) throws Exception {

		CaseFileEntity caseFile = iCaseFileRepository.findByCodice(body.getCasefileId())
				.orElseThrow(() -> new NotFoundException(body.getCasefileId().toString()));

		boolean checkValidBooking = BoliteUtils.checkBookingActivation(LocalDate.now(),
				LocalTime.now(), LocalTime.now(), caseFile.getBooking());

		if (checkValidBooking && caseFile.getBooking().getRoom().getCodice().equals(body.getLockId())) {

			byte[] bytes = (username + ":" + password).getBytes("UTF-8");
			String auth = "Basic " + Base64.getEncoder().encodeToString(bytes);

			RequestOpenDoor requestOpenDoor = RequestOpenDoor.builder().doorId(12345678).build();
			ResponseEntity<ResponseOpenDoor> response = iSerratureClient.openDoor(auth, requestOpenDoor);
			if (response.hasBody()) {
				boolean success = response.getBody().getSuccess();
				if (!success)
					throw new UnauthorizedException("Autorizzazione non adeguata");
			}

		} else
			throw new RuntimeException("Prenotazione non attiva in questo momento.");

	}


}
