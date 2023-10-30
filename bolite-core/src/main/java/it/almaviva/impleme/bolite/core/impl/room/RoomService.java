package it.almaviva.impleme.bolite.core.impl.room;

import it.almaviva.impleme.bolite.core.mappers.IRoomMapper;
import it.almaviva.impleme.bolite.core.mappers.room.IRoomCategoryMapper;
import it.almaviva.impleme.bolite.core.mappers.room.IRoomServiceMapper;
import it.almaviva.impleme.bolite.core.mappers.room.IRoomStructureTypeMapper;
import it.almaviva.impleme.bolite.core.mappers.room.IRoomTariffMapper;
import it.almaviva.impleme.bolite.core.room.IRoomService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.domain.dto.booking.books.EventDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.reservation.NewReservationDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.*;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.UpdateServiceDTO;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.room.*;
import it.almaviva.impleme.bolite.integration.repositories.booking.IBookingRepository;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import it.almaviva.impleme.bolite.integration.repositories.room.*;
import it.almaviva.impleme.bolite.utils.BoliteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Transactional
@Slf4j
@Service
public class RoomService implements IRoomService {

	private final IRoomMapper roomMapper;
	private final IRoomCategoryMapper iRoomCategoryMapper;
	private final IRoomStructureTypeMapper iRoomStructureTypeMapper;
	private final IRoomTariffMapper iRoomTariffMapper;
	private final IRoomServiceMapper iRoomServiceMapper;

	private final IRoomRepository roomRepository;
	private final IRoomTariffRepository roomTariffRepository;
	private final IRoomCategoryRepository iRoomCategoryRepository;
	private final IEnteRepository iEnteRepository;
	private final IRoomTypologyRepository iRoomTypologyRepository;
	private final IRoomServiceRepository iRoomServiceRepository;
	private final IRoomReservationRepository iRoomReservationRepository;
	private final IBookingRepository iBookingRepository;
	private final ICaseFileRepository iCaseFileRepository;
	private final IRoomSlotRepository iRoomSlotRepository;
	private final IRoomDailyRepository iRoomDailyRepository;
	private final IRoomAperturaRepository iRoomAperturaRepository;



	public RoomService(IRoomMapper roomMapper,
					   IRoomCategoryMapper iRoomCategoryMapper, IRoomStructureTypeMapper iRoomStructureTypeMapper,
					   IRoomTariffMapper iRoomTariffMapper, IRoomServiceMapper iRoomServiceMapper, IRoomRepository roomRepository, IRoomTariffRepository roomTariffRepository,
					   IRoomCategoryRepository iRoomCategoryRepository, IEnteRepository iEnteRepository,
					   IRoomTypologyRepository iRoomTypologyRepository,
					   IRoomServiceRepository iRoomServiceRepository, IRoomReservationRepository iRoomReservationRepository,
					   IBookingRepository iBookingRepository, ICaseFileRepository iCaseFileRepository, IRoomSlotRepository iRoomSlotRepository, IRoomDailyRepository iRoomDailyRepository, IRoomAperturaRepository iRoomAperturaRepository) {
		this.roomMapper = roomMapper;
		this.iRoomCategoryMapper = iRoomCategoryMapper;
		this.iRoomStructureTypeMapper = iRoomStructureTypeMapper;
		this.iRoomTariffMapper = iRoomTariffMapper;
		this.iRoomServiceMapper = iRoomServiceMapper;
		this.roomRepository = roomRepository;
		this.roomTariffRepository = roomTariffRepository;
		this.iRoomCategoryRepository = iRoomCategoryRepository;
		this.iEnteRepository = iEnteRepository;
		this.iRoomTypologyRepository = iRoomTypologyRepository;
		this.iRoomServiceRepository = iRoomServiceRepository;
		this.iRoomReservationRepository = iRoomReservationRepository;
		this.iBookingRepository = iBookingRepository;
		this.iCaseFileRepository = iCaseFileRepository;
		this.iRoomSlotRepository = iRoomSlotRepository;
		this.iRoomDailyRepository = iRoomDailyRepository;
		this.iRoomAperturaRepository = iRoomAperturaRepository;
	}

	@Override
	@Transactional
	public RoomDTO createRoom(NewRoomDTO roomDTO, String ente) {

		RoomEntity entity = new RoomEntity();
		entity.setNome(roomDTO.getNome());
		entity.setBlocked(roomDTO.getBlocked());
		entity.setCapienza(roomDTO.getCapienza());
		entity.setCatering(roomDTO.getCatering());
		entity.setCodice(roomDTO.getId());
		entity.setCondizioniUtilizzo(roomDTO.getCondizioniUtilizzo());
		entity.setGiorniAnticipo(roomDTO.getGiorniAnticipo());
		entity.setTerzeParti(roomDTO.getTerzeParti());

		final Optional<RoomCategoryEntity> categoryEntity = iRoomCategoryRepository.findById(roomDTO.getCategoria());
		entity.setCategory(categoryEntity.orElseThrow(()->new NotFoundException(roomDTO.getCategoria())));

		final Optional<EnteEntity> comuneEntity = iEnteRepository.findByCodice(ente);
		entity.setEnte(comuneEntity.orElseThrow(()->new NotFoundException(ente)));

		final Optional<RoomStructureTypeEntity> tipoStrutturaEntity = iRoomTypologyRepository
				.findById(roomDTO.getTipoStruttura());
		entity.setStructure(tipoStrutturaEntity.orElseThrow(()->new NotFoundException(roomDTO.getTipoStruttura())));

		final List<NewTariffarioDTO> tariffarioDTOList = roomDTO.getTariffario();
		if(tariffarioDTOList != null) {

			for (NewTariffarioDTO item : tariffarioDTOList) {
				RoomTariffEntity tariffEntity = new RoomTariffEntity();
				tariffEntity.setCostoInteraGiornata(item.getCostoInteraGiornata());
				tariffEntity.setCostoOrario(item.getCostoOrario());
				tariffEntity.setCostoSettimanale(item.getCostoSettimanale());
				tariffEntity.setFlagInteraSettimana(item.getFlagInteraSettimana());
				tariffEntity.setFlagInteraGiornata(item.getFlagInteraGiornata());
				tariffEntity.setNote(item.getNote());
				tariffEntity.setEventType(item.getTipoEvento());


				final List<NewRangeDTO> fasceDtoList = item.getCostoFasce();
				if (fasceDtoList != null) {
					for (NewRangeDTO fasciaDto : fasceDtoList) {
						RoomDailyTariffEntity daily = new RoomDailyTariffEntity();
						daily.setGiorno(fasciaDto.getGiorno().getMeters());
						final List<NewFasciaDTO> fasce = fasciaDto.getFasce();
						for (NewFasciaDTO fascia : fasce) {
							RoomRowEntity rre = new RoomRowEntity();
							rre.setCostoFascia(fascia.getCostoFascia());
							rre.setOraDa(fascia.getOraDa().withMinute(0).withSecond(0));
							rre.setOraA(fascia.getOraA().withMinute(0).withSecond(0));
							daily.addFascia(rre);
						}
						tariffEntity.addRange(daily);
					}
				}

				entity.addTariff(tariffEntity);
			}
		}

		final List<NewAperturaDTO> aperture = roomDTO.getAperture();
		if(aperture != null) {
			for (NewAperturaDTO item : aperture) {
				RoomAperturaEntity rae = new RoomAperturaEntity();
				rae.setDay_of_week(item.getGiorno().getMeters());
				rae.setOraDa(item.getOraDa());
				rae.setOraA(item.getOraA());
				entity.addApertura(rae);
			}
		}

		final List<NewServiceDTO> servizi = roomDTO.getServizi();
		if(servizi != null){
			for(NewServiceDTO serviceDTO : servizi){
				RoomServiceEntity rse = new RoomServiceEntity();
				rse.setCodice(serviceDTO.getCodice());
				rse.setDescrizione(serviceDTO.getDescrizione());
				rse.setImporto(serviceDTO.getImporto());
				rse.setNote(serviceDTO.getNote());
				entity.addService(rse);
			}
		}
		entity = roomRepository.save(entity);
		final RoomDTO roomDTO1 = roomMapper.entityToDto(entity);
		final Set<RoomTariffEntity> tariffario = entity.getTariffario();
		final List<EventDTO> results = new ArrayList<>(0);
		if(tariffario != null) {
			for (RoomTariffEntity t : tariffario) {
				EventDTO e = new EventDTO();
				e.setDescrizione(t.getNote());
				e.setId(t.getId());
				e.setEvento(t.getEventType());
				results.add(e);
			}
		}
		roomDTO1.setEventi(results);
		return roomDTO1;
	}

	@Override
	@Transactional
	public void deleteRoom(UUID codice)  {
		RoomEntity re = roomRepository.findByCodice(codice).orElseThrow(()->new NotFoundException(codice.toString()));
		List<BookingEntity> bookings = iBookingRepository.findByRoomId(re.getId());
		if (!bookings.isEmpty()) {
			throw new OverlapException("Non si pu&ograve; eliminare una stanza con delle prenotazioni");
		} else {
			re.removeTariffe();
			re.removeServizi();
			re.removeAperture();
			re.removeRiserve();
			roomRepository.deleteByCodice(codice);
		}
	}

	@Override
	public void deleteServiceOfRoom(UUID roomId, Integer serviceId) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomServiceEntity service = iRoomServiceRepository.findById(serviceId).orElseThrow(()->new NotFoundException(serviceId));
		re.getServizi().remove(service);

		roomRepository.save(re);
	}

	@Override
	public void deleteReservationOfRoom(UUID roomId, Integer reservationId) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomReservationEntity reservation = iRoomReservationRepository.findById(reservationId).orElseThrow(()->new NotFoundException(reservationId));
		re.getRiserve().remove(reservation);

		roomRepository.save(re);
	}

	@Override
	@Transactional
	public RoomDTO getRoom(UUID roomId) {
		final RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		final RoomDTO roomDTO = roomMapper.entityToDto(re);

		final Set<RoomTariffEntity> tariffario = re.getTariffario();
		final List<EventDTO> results = new ArrayList<>(0);
		if(tariffario != null) {
			for (RoomTariffEntity t : tariffario) {
				EventDTO e = new EventDTO();
				e.setDescrizione(t.getNote());
				e.setId(t.getId());
				e.setEvento(t.getEventType());
				results.add(e);
			}
		}
		roomDTO.setEventi(results);
		return roomDTO;
	}

	@Override
	@Transactional
	public List<RoomDTO> getRooms(String comune, Integer tipologia, Integer categoria) {
		final List<RoomEntity> rooms = roomRepository.getRooms(comune, tipologia, categoria);
		return roomMapper.entityListToDtoList(rooms);
	}

	@Override
	public RoomDTO createServizio(UUID roomId, NewServiceDTO body) {
		RoomEntity roomEntity = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomServiceEntity service = new RoomServiceEntity();
		service.setCodice(body.getCodice());
		service.setImporto(body.getImporto());
		service.setNote(body.getNote());
		service.setDescrizione(body.getDescrizione());

		roomEntity.addService(service);
		roomEntity = roomRepository.save(roomEntity);

		return roomMapper.entityToDto(roomEntity);
	}

	@Override
	public RoomDTO createReservation(UUID roomId, NewReservationDTO body) {
		RoomEntity roomEntity = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomReservationEntity reservation = new RoomReservationEntity();
		reservation.setMotivo(body.getMotivo());
		reservation.setNote(body.getNote());
		reservation.setGiornoDa(body.getGiornoDa());
		reservation.setGiornoA(body.getGiornoA());
		reservation.setOraDa(body.getOraDa().withMinute(0).withSecond(0));
		reservation.setOraA(body.getOraA().withMinute(0).withSecond(0));

		roomEntity.addReservation(reservation);
		roomEntity = roomRepository.save(roomEntity);

		return roomMapper.entityToDto(roomEntity);
	}

	@Override
	public RoomDTO createTariffario(UUID roomId, NewTariffarioDTO body) {
		RoomEntity roomEntity = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));

		int numGiorni = roomEntity.getAperture().size();
		if(body.getFlagInteraSettimana() && numGiorni <7){
			throw new OverlapException("Errore: non si può inserire la tariffa settimanale, perché la sala non è aperta 7/7");
		}

		RoomTariffEntity tariffEntity = new RoomTariffEntity();
		tariffEntity.setCostoInteraGiornata(body.getCostoInteraGiornata());
		tariffEntity.setCostoOrario(body.getCostoOrario());
		tariffEntity.setFlagInteraGiornata(body.getFlagInteraGiornata());
		tariffEntity.setFlagInteraSettimana(body.getFlagInteraSettimana());
		tariffEntity.setCostoSettimanale(body.getCostoSettimanale());
		tariffEntity.setNote(body.getNote());
		tariffEntity.setEventType(body.getTipoEvento());
		roomEntity.addTariff(tariffEntity);

		final List<NewRangeDTO> fasceDtoList = body.getCostoFasce();
		if(fasceDtoList != null) {
			for (NewRangeDTO fasciaDto : fasceDtoList) {
				RoomDailyTariffEntity daily = new RoomDailyTariffEntity();
				daily.setGiorno(fasciaDto.getGiorno().getMeters());

				final List<NewFasciaDTO> fasce = fasciaDto.getFasce();
				if (fasce != null) {
					for (NewFasciaDTO fascia : fasce) {
						RoomRowEntity rre = new RoomRowEntity();
						rre.setCostoFascia(fascia.getCostoFascia());
						rre.setOraDa(fascia.getOraDa().withMinute(0).withSecond(0));
						rre.setOraA(fascia.getOraA().withMinute(0).withSecond(0));
						daily.addFascia(rre);
					}
				}
				tariffEntity.addRange(daily);
			}
		}


		roomEntity = roomRepository.save(roomEntity);
		return roomMapper.entityToDto(roomEntity);
	}

	@Override
	@Transactional
	public void deleteTariffario(UUID roomId, Integer tariffId) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomTariffEntity tariff = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException(tariffId));
		re.getTariffario().remove(tariff);

		roomRepository.save(re);
	}


	@Transactional
	protected AvailabilityDayHourDTO verificaDisponibilita(UUID roomId, LocalDate dayStart, LocalDate dayEnd, LocalTime hourStart,
			LocalTime hourEnd) {
		AvailabilityDayHourDTO availability = new AvailabilityDayHourDTO();
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));

		final LocalDate dueDate = LocalDate.now().plusDays(re.getGiorniAnticipo());
		if (dayStart.isBefore(dueDate)) {
			availability.setStato(AvailabilityDayHourDTO.EStato.CHIUSA);
			return availability;
		}


		final Set<RoomAperturaEntity> aperture = re.getAperture();
		final List<LocalDate> datesBetween = getDatesBetween(dayStart, dayEnd);
		for(LocalDate currentDay : datesBetween){
			final int value = currentDay.getDayOfWeek().getValue();
			final Optional<RoomAperturaEntity> any = aperture.stream().filter(a -> a.getDay_of_week().equals(value)).findAny();
			if(!any.isPresent()) {
				availability.setStato(AvailabilityDayHourDTO.EStato.CHIUSA);
				return availability;
			}
			final RoomAperturaEntity roomAperturaEntity = any.get();
			if(hourStart.isBefore(roomAperturaEntity.getOraDa()) || hourStart.isAfter(roomAperturaEntity.getOraA()) || hourEnd.isAfter(roomAperturaEntity.getOraA())){
				availability.setStato(AvailabilityDayHourDTO.EStato.CHIUSA);
				return availability;
			}
		}

		Set<RoomReservationEntity> reservations = re.getRiserve();
		boolean overlapReservation;
		for (RoomReservationEntity roomReservationEntity : reservations) {
			overlapReservation = BoliteUtils.overlapDate(dayStart, dayEnd, hourStart, hourEnd, roomReservationEntity);
			if(overlapReservation) {
				availability.setStato(AvailabilityDayHourDTO.EStato.RISERVATA);
				return availability;
			}
		}



		boolean overlapBooking;
		List<BookingEntity> bookings = iBookingRepository.findByRoomId(re.getId());
		final List<BookingEntity> bookingsWeeks = bookings.stream().filter(p -> p.getFlagWeek()).collect(Collectors.toList());
		bookings.removeIf(p ->p.getFlagWeek());

		for(BookingEntity be : bookingsWeeks){
			final List<LocalDate> datesBetweenInWeek = getDatesBetween(dayStart, dayEnd);
			for(LocalDate ld : datesBetweenInWeek) {
				final int value = ld.getDayOfWeek().getValue();
				final Optional<RoomAperturaEntity> any = aperture.stream().filter(a -> a.getDay_of_week().equals(value)).findAny();
				BookingEntity finta = new BookingEntity();
				finta.setRoom(be.getRoom());
				finta.setBookingStartDate(ld);
				finta.setBookingEndDate(ld);
				finta.setBookingStartHour(any.get().getOraDa());
				finta.setBookingEndHour(any.get().getOraA());
				finta.setId(be.getId());
				bookings.add(finta);
			}
		}



		//TODO settimanale

		int richiesteInAttesa = 0;
		for (BookingEntity bookingEntity : bookings) {
			overlapBooking = BoliteUtils.overlapDate(dayStart, dayEnd, hourStart, hourEnd, bookingEntity);
			if(overlapBooking)
				{
				  CaseFileEntity caseFile = iCaseFileRepository.findByBookingId(bookingEntity.getId());
				  Integer stato = caseFile.getStato().getId();
				  if(stato>=1 && stato<5) {
					  richiesteInAttesa++;
				  }
				  else if(stato==5) {
					availability.setStato(AvailabilityDayHourDTO.EStato.OCCUPATA);
					return availability;
				  }
				}
		}
		if(richiesteInAttesa>0) {
			availability.setStato(AvailabilityDayHourDTO.EStato.DISPONIBILE_CON_RISERVA);
			availability.setNumeroInAttesa(richiesteInAttesa);
			return availability;
		}
		availability.setStato(AvailabilityDayHourDTO.EStato.DISPONIBILE);
		return availability;
	}

	@Override
	public TypologyDTO createTypology(NewTypologyDTO body) {
		RoomStructureTypeEntity rste = new RoomStructureTypeEntity();
		rste.setStruttura(body.getTipo());
		rste.setDescrizione(body.getDescrizione());

		final RoomStructureTypeEntity save = iRoomTypologyRepository.save(rste);
		return iRoomStructureTypeMapper.map(save);
	}

	@Override
	public List<TypologyDTO> getTypologies() {
		final List<RoomStructureTypeEntity> all = iRoomTypologyRepository.findAll();
		return iRoomStructureTypeMapper.map(all);
	}

	@Override
	public void deleteTipologiaStruttura(Integer id) {
		iRoomTypologyRepository.deleteById(id);
	}


	@Override
	public CategoryDTO createCategory(NewCategoryDTO body) {

		RoomCategoryEntity rce = new RoomCategoryEntity();
		rce.setCategory(body.getCategoria());
		rce.setDescrizione(body.getDescrizione());

		final RoomCategoryEntity save = iRoomCategoryRepository.save(rce);
		return iRoomCategoryMapper.map(save);
	}

	@Override
	public List<CategoryDTO> getCategorie() {
		final List<RoomCategoryEntity> all = iRoomCategoryRepository.findAll();
		return iRoomCategoryMapper.map(all);
	}

	@Override
	public void deleteCategoria(Integer id) {
		iRoomCategoryRepository.deleteById(id);
	}


	@Override
	@Transactional
	public List<RoomDTO> getRoomsForAdmin(Integer tipologia, Integer categoria, String ente) {

		EnteEntity enteEntity = iEnteRepository.findByCodice(ente).orElseThrow(()->new NotFoundException(ente));
		final List<RoomEntity> rooms = roomRepository.getRooms(enteEntity.getCodice(), tipologia, categoria);
		return roomMapper.entityListToDtoList(rooms);
	}

	@Override
	@Transactional
	public RoomDTO updateRoom(UUID roomId, UpdateRoomDTO body) {
		RoomEntity re = roomRepository.findByCodice(roomId)
				.orElseThrow(()->new NotFoundException("La struttura identificata con id "+roomId.toString()+ " non esiste"));

		re.setGiorniAnticipo(body.getGiorniAnticipo());
		re.setCatering(body.getCatering());
		re.setCapienza(body.getCapienza());
		re.setNome(body.getNome());
		re.setBlocked(body.getBlocked());
		re.setTerzeParti(body.getTerzeParti());
		re.setCondizioniUtilizzo(body.getCondizioniUtilizzo());

		final Optional<RoomCategoryEntity> categoryEntity = iRoomCategoryRepository.findById(body.getCategoria());
		re.setCategory(categoryEntity.orElseThrow(()->new NotFoundException("La categoria selezionata non esiste")));

		final Optional<RoomStructureTypeEntity> tipoStrutturaEntity = iRoomTypologyRepository
				.findById(body.getTipoStruttura());
		re.setStructure(tipoStrutturaEntity.orElseThrow(()->new NotFoundException("Il tipo struttura selezionato non esiste")));

		re.removeAperture();
		re.removeServizi();
		re.removeTariffe();
		re = roomRepository.saveAndFlush(re);

		final List<NewTariffarioDTO> tariffarioDTOList = body.getTariffario();
		if(tariffarioDTOList != null) {

			for (NewTariffarioDTO item : tariffarioDTOList) {
				RoomTariffEntity tariffEntity = new RoomTariffEntity();
				tariffEntity.setCostoInteraGiornata(item.getCostoInteraGiornata());
				tariffEntity.setCostoOrario(item.getCostoOrario());
				tariffEntity.setCostoSettimanale(item.getCostoSettimanale());
				tariffEntity.setFlagInteraSettimana(item.getFlagInteraSettimana());
				tariffEntity.setFlagInteraGiornata(item.getFlagInteraGiornata());
				tariffEntity.setNote(item.getNote());
				tariffEntity.setEventType(item.getTipoEvento());


				final List<NewRangeDTO> fasceDtoList = item.getCostoFasce();
				if (fasceDtoList != null) {
					for (NewRangeDTO fasciaDto : fasceDtoList) {
						RoomDailyTariffEntity daily = new RoomDailyTariffEntity();
						daily.setGiorno(fasciaDto.getGiorno().getMeters());
						final List<NewFasciaDTO> fasce = fasciaDto.getFasce();
						for (NewFasciaDTO fascia : fasce) {
							RoomRowEntity rre = new RoomRowEntity();
							rre.setCostoFascia(fascia.getCostoFascia());
							rre.setOraDa(fascia.getOraDa().withMinute(0).withSecond(0));
							rre.setOraA(fascia.getOraA().withMinute(0).withSecond(0));
							daily.addFascia(rre);
						}
						tariffEntity.addRange(daily);
					}
				}

				re.addTariff(tariffEntity);
			}
		}

		final List<NewAperturaDTO> aperture = body.getAperture();
		if(aperture != null) {
			for (NewAperturaDTO item : aperture) {
				RoomAperturaEntity rae = new RoomAperturaEntity();
				rae.setDay_of_week(item.getGiorno().getMeters());
				rae.setOraDa(item.getOraDa());
				rae.setOraA(item.getOraA());
				re.addApertura(rae);
			}
		}

		final List<NewServiceDTO> servizi = body.getServizi();
		if(servizi != null){
			for(NewServiceDTO serviceDTO : servizi){
				RoomServiceEntity rse = new RoomServiceEntity();
				rse.setCodice(serviceDTO.getCodice());
				rse.setDescrizione(serviceDTO.getDescrizione());
				rse.setImporto(serviceDTO.getImporto());
				rse.setNote(serviceDTO.getNote());
				re.addService(rse);
			}
		}

		re = roomRepository.saveAndFlush(re);
		final RoomDTO roomDTO = roomMapper.entityToDto(re);

		final Set<RoomTariffEntity> tariffario = re.getTariffario();
		final List<EventDTO> results = new ArrayList<>(0);
		if(tariffario != null) {
			for (RoomTariffEntity t : tariffario) {
				EventDTO e = new EventDTO();
				e.setDescrizione(t.getNote());
				e.setId(t.getId());
				e.setEvento(t.getEventType());
				results.add(e);
			}
		}
		roomDTO.setEventi(results);
		return roomDTO;
	}

	/**
	 * aggiorna la tariffa non le fasce
	 * @param roomId
	 * @param tariffId
	 * @param body
	 * @return
	 */
	@Override
	public TariffarioDTO updateTariffario(UUID roomId, Integer tariffId, UpdateTariffarioDTO body) {

		final RoomEntity roomEntity = roomRepository.findByCodice(roomId).orElseThrow(() -> new NotFoundException("La struttura con id " + roomId.toString() + " non esiste"));
		RoomTariffEntity tariffEntity = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException("La tariffa selezionata non esiste"));

		if(!tariffEntity.getFasce().isEmpty()){
			throw new OverlapException("Errore: non si può inserire la tariffa oraria, perché è presente una tariffa per fasce");
		}

		final BigDecimal costoOrario = body.getCostoOrario();
		final BigDecimal costoInteraGiornata = body.getCostoInteraGiornata();
		final Boolean flagInteraGiornata = body.getFlagInteraGiornata();
		final BigDecimal costoSettimanale = body.getCostoSettimanale();
		final Boolean flagInteraSettimana = body.getFlagInteraSettimana();

		if(flagInteraSettimana && costoSettimanale == null || !flagInteraSettimana && costoSettimanale != null){
			throw new OverlapException("Tariffa errata: verificare la coerenza dei campi flagInteraSettimana e costoSettimanale");
		}

		if(flagInteraSettimana && costoSettimanale != null){
			if(flagInteraGiornata || costoInteraGiornata != null || ( costoOrario != null && costoOrario.compareTo(BigDecimal.ZERO) > 0)){
				throw new OverlapException("Tariffa errata: le tariffe giornaliera, settimanale ,oraria e a fasce si escludono a vicenda");
			}
		}

		if(flagInteraGiornata && costoInteraGiornata == null || !flagInteraGiornata && costoInteraGiornata != null){
			throw new OverlapException("Tariffa errata: verificare la coerenza dei campi flagInteraGiornata costoInteraGiornata");
		}

		if(flagInteraGiornata && costoInteraGiornata != null){

			if(flagInteraSettimana || costoSettimanale != null){
				throw new OverlapException("Tariffa errata: le tariffe giornaliera e settimanale si escludono a vicenda");
			}

		}

		int numGiorni = roomEntity.getAperture().size();
		if(body.getFlagInteraSettimana() && numGiorni <7){
			throw new OverlapException("Errore: non si può inserire la tariffa settimanale, perché la sala non è aperta 7/7");
		}

		tariffEntity.setCostoInteraGiornata(costoInteraGiornata);
		tariffEntity.setCostoOrario(costoOrario);
		tariffEntity.setFlagInteraGiornata(flagInteraGiornata);
		tariffEntity.setFlagInteraSettimana(flagInteraSettimana);
		tariffEntity.setCostoSettimanale(costoSettimanale);
		tariffEntity.setNote(body.getNote());
		tariffEntity.setEventType(body.getTipoEvento());

		final RoomTariffEntity save = roomTariffRepository.save(tariffEntity);
		return iRoomTariffMapper.map(save);
	}

	@Override
	@Transactional
	public List<EventDTO> getTipologieEvento(UUID roomId) {
		final RoomEntity roomEntity = roomRepository.findByCodice(roomId).orElseThrow(() -> new NotFoundException("La struttura con id " + roomId.toString() + " non esiste"));
		final Set<RoomTariffEntity> tariffario = roomEntity.getTariffario();

		final List<EventDTO> results = new ArrayList<>(0);
		for(RoomTariffEntity t: tariffario){
			EventDTO e = new EventDTO();
			e.setDescrizione(t.getNote());
			e.setId(t.getId());
			e.setEvento(t.getEventType());
			results.add(e);
		}
		return results;
	}

	@Override
	@Transactional
	public void deleteGiorno(UUID roomId, Integer tariffId, Integer cfId) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomTariffEntity tariff = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException(tariffId));
		if(tariff.getFasce().size() == 1){
			throw  new OverlapException("Non è possibile eliminare l'unico giorno disponibile");
		}
		final RoomDailyTariffEntity roomDailyTariffEntity = iRoomDailyRepository.findById(cfId).orElseThrow(() -> new NotFoundException(cfId));
		iRoomDailyRepository.delete(roomDailyTariffEntity);
	}

	@Override
	@Transactional
	public void deleteFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomTariffEntity tariff = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException(tariffId));
		final RoomDailyTariffEntity roomDailyTariffEntity = iRoomDailyRepository.findById(cfId).orElseThrow(() -> new NotFoundException(cfId));
		if(roomDailyTariffEntity.getFasce().size() == 1){
			throw  new OverlapException("Non è possibile eliminare l'unica fascia disponibile");
		}
		final RoomRowEntity roomRowEntity = iRoomSlotRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
		iRoomSlotRepository.delete(roomRowEntity);
	}

	@Override
	public ServiceDTO updateServiceOfRoom(UUID roomId, Integer serviceId, UpdateServiceDTO body) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomServiceEntity service = iRoomServiceRepository.findById(serviceId).orElseThrow(()->new NotFoundException(serviceId));
		service.setDescrizione(body.getDescrizione());
		service.setNote(body.getNote());
		service.setImporto(body.getImporto());

		final RoomServiceEntity save = iRoomServiceRepository.save(service);
		return iRoomServiceMapper.map(save) ;
	}

	@Override
	public TariffarioDTO updateFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id, UpdateFasciaDTO body) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomTariffEntity tariff = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException(tariffId));
		final RoomDailyTariffEntity roomDailyTariffEntity = iRoomDailyRepository.findById(cfId).orElseThrow(() -> new NotFoundException(cfId));
		final RoomRowEntity roomRowEntity = iRoomSlotRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

		roomRowEntity.setCostoFascia(body.getCostoFascia());
		roomRowEntity.setOraDa(body.getOraDa().withMinute(0).withSecond(0));
		roomRowEntity.setOraA(body.getOraA().withMinute(0).withSecond(0));

		iRoomSlotRepository.saveAndFlush(roomRowEntity);

		return iRoomTariffMapper.map(tariff);
	}

	@Override
	public void deleteApertura(UUID roomId, Integer id) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		iRoomAperturaRepository.deleteById(id);
	}

	@Override
	public RoomDTO createApertura(UUID roomId, NewAperturaDTO body) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));

		RoomAperturaEntity e = new RoomAperturaEntity();
		e.setOraDa(body.getOraDa().withMinute(0).withSecond(0));
		e.setOraA(body.getOraA().withMinute(0).withSecond(0));
		e.setDay_of_week(body.getGiorno().getMeters());

		re.addApertura(e);


		roomRepository.save(re);
		return roomMapper.entityToDto(re);
	}

	@Override
	public RoomDTO updateApertura(UUID roomId, Integer id, UpdateAperturaDTO body) {
		RoomEntity re = roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException(roomId.toString()));
		RoomAperturaEntity ra = iRoomAperturaRepository.findById(id).orElseThrow(()->new NotFoundException(id));

		ra.setOraDa(body.getOraDa().withMinute(0).withSecond(0));
		ra.setOraA(body.getOraA().withMinute(0).withSecond(0));

		return roomMapper.entityToDto(re);
	}

	@Override
	public TariffarioDTO crateGiorno(UUID roomId, Integer tariffId, NewRangeDTO body) {
		roomRepository.findByCodice(roomId).orElseThrow(()->new NotFoundException("La struttura con id "+roomId.toString()+ " non esiste"));
		RoomTariffEntity tariffEntity = roomTariffRepository.findById(tariffId).orElseThrow(()->new NotFoundException("La tariffa selezionata non esiste"));

		final long count = tariffEntity.getFasce().stream().filter(t -> t.getGiorno().equals(body.getGiorno().getMeters())).count();
		if(count>0){
			throw new OverlapException("Esiste già una fascia per il giorno selezionato");
		}



		RoomDailyTariffEntity daily = new RoomDailyTariffEntity();
		daily.setGiorno(body.getGiorno().getMeters());
		final List<NewFasciaDTO> fasce = body.getFasce();
		for (NewFasciaDTO fascia : fasce) {
			RoomRowEntity rre = new RoomRowEntity();
			rre.setCostoFascia(fascia.getCostoFascia());
			rre.setOraDa(fascia.getOraDa().withMinute(0).withSecond(0));
			rre.setOraA(fascia.getOraA().withMinute(0).withSecond(0));
			daily.addFascia(rre);
		}
		tariffEntity.addRange(daily);

		final RoomTariffEntity save = roomTariffRepository.save(tariffEntity);

		return iRoomTariffMapper.map(save);
	}


	@Override
	public AvailabilityDTO verificaDisponibilita(UUID roomId, LocalDate dayStart, LocalDate dayEnd, Integer tariffa) {

		if(dayEnd == null){
			dayEnd = dayStart;
		}
		
		final RoomEntity roomEntity = roomRepository.findByCodice(roomId)
				.orElseThrow(() -> new NotFoundException("Lo spazio con identificativo "+roomId.toString()+ " non esiste"));

		if(roomEntity.getBlocked()){
			throw new OverlapException("Errore: per la struttura selezionata le prenotazioni sono momentaneamente sospese");
		}

		boolean mostraFasce = false;
		if(tariffa != null) {
			final RoomTariffEntity roomTariffEntity = roomTariffRepository.findById(tariffa)
							.orElseThrow(() -> new NotFoundException("La tariffa selezionata non esiste"));
			mostraFasce = roomTariffEntity.getFasce().isEmpty() ? false : true;
		}

		final Set<RoomAperturaEntity> aperture = roomEntity.getAperture();

		AvailabilityDTO availabilityDTO = new AvailabilityDTO();

		final List<LocalDate> datesBetween = getDatesBetween(dayStart, dayEnd);
		for(LocalDate currentDay : datesBetween){

			AvailabilityDayDTO availabilityDayDTO = new AvailabilityDayDTO();
			availabilityDayDTO.setGiorno(currentDay);

			final int value = currentDay.getDayOfWeek().getValue();
			final Optional<RoomAperturaEntity> any = aperture.stream().filter(a -> a.getDay_of_week().equals(value)).findAny();
			if(!any.isPresent()){ // quel giorno è chiusa
				for(int i = 0; i<24;){
					final LocalTime oraDa = LocalTime.of(i++, 0, 0);

					final LocalTime oraA;
					if(i == 24){
						oraA = LocalTime.of(23, 59, 59);
					}else {
						oraA = LocalTime.of(i, 0, 0);
					}
					final AvailabilityDayHourDTO availabilityDayHourDTO = new AvailabilityDayHourDTO();
					availabilityDayHourDTO.setOraDa(oraDa);
					availabilityDayHourDTO.setOraA(oraA);
					availabilityDayHourDTO.setStato(AvailabilityDayHourDTO.EStato.CHIUSA);
					availabilityDayDTO.getOre().add(availabilityDayHourDTO);
				}
				availabilityDTO.getGiorni().add(availabilityDayDTO);
				continue;
			}

			final RoomAperturaEntity roomAperturaEntity = any.get();

			for(int i = 0; i<24;){
				LocalTime oraDa = LocalTime.of(i++, 0, 0);

				LocalTime oraA;
				if(i == 24){
					oraA = LocalTime.of(23, 59, 59);
				}else {
					oraA = LocalTime.of(i, 0, 0);
				}



				final boolean aperta = hasOverlap(oraDa, oraA, roomAperturaEntity.getOraDa(), roomAperturaEntity.getOraA());

				final AvailabilityDayHourDTO availabilityDayHourDTO;
				if(!aperta) {
					availabilityDayHourDTO = new AvailabilityDayHourDTO();
					availabilityDayHourDTO.setStato(AvailabilityDayHourDTO.EStato.CHIUSA);
				}else {

					if(mostraFasce){
						final RoomTariffEntity roomTariffEntity = roomTariffRepository.findById(tariffa)
										.orElseThrow(() -> new NotFoundException("La tariffa selezionata non esiste"));
						final Optional<RoomDailyTariffEntity> fascia = roomTariffEntity.getFasce().stream().filter(roomDailyTariffEntity -> roomDailyTariffEntity.getGiorno().equals(currentDay.getDayOfWeek().getValue())).findAny();
						if(!fascia.isPresent()){ // è APERTA ma non c'è la fascia
							availabilityDayHourDTO = new AvailabilityDayHourDTO();
							availabilityDayHourDTO.setStato(AvailabilityDayHourDTO.EStato.NON_DISPONIBILE);
						}else{
							final Set<RoomRowEntity> fasce = fascia.get().getFasce();
							boolean isOverFascia = false;
							for(RoomRowEntity f : fasce){
								if(hasOverlap(oraDa, oraA, f.getOraDa(), f.getOraA())){
									isOverFascia = true;
									oraDa = f.getOraDa();
									oraA = f.getOraA();
									break;
								}
							}
							if(!isOverFascia) {
								availabilityDayHourDTO = new AvailabilityDayHourDTO();
								availabilityDayHourDTO.setStato(AvailabilityDayHourDTO.EStato.NON_DISPONIBILE);
							}else{
								availabilityDayHourDTO = verificaDisponibilita(roomId, currentDay, currentDay, oraDa, oraA);
								int hours = (int) ChronoUnit.HOURS.between(oraDa, oraA);
								if(hours > 1){
									i = i+hours;
								}
							}
						}
					}else {
						availabilityDayHourDTO = verificaDisponibilita(roomId, currentDay, currentDay, oraDa, oraA);
					}
				}
					availabilityDayHourDTO.setOraDa(oraDa);
					availabilityDayHourDTO.setOraA(oraA);
					availabilityDayDTO.getOre().add(availabilityDayHourDTO);
				}

			availabilityDTO.getGiorni().add(availabilityDayDTO);
		}

		final List<AvailabilityDayDTO> giorni = availabilityDTO.getGiorni();
		for(AvailabilityDayDTO day : giorni){
			final List<AvailabilityDayHourDTO> ore = day.getOre();
			final boolean disponibile = ore.stream().filter(p -> p.getStato().equals(AvailabilityDayHourDTO.EStato.DISPONIBILE) || p.getStato().equals(AvailabilityDayHourDTO.EStato.DISPONIBILE_CON_RISERVA)).findFirst().isPresent();
			final boolean occupata = ore.stream().filter(p ->  p.getStato().equals(AvailabilityDayHourDTO.EStato.NON_DISPONIBILE) || p.getStato().equals(AvailabilityDayHourDTO.EStato.OCCUPATA) || p.getStato().equals(AvailabilityDayHourDTO.EStato.RISERVATA) ).findFirst().isPresent();
			boolean libera = disponibile && !occupata;
			day.setLibera(libera);
		}

		return availabilityDTO;
	}

	@Override
	@Transactional
	public AvailabilityDTO verificaDisponibilitaSmart(UUID roomId, LocalDate dayStart, LocalDate dayEnd) {

		AvailabilityDTO availabilityDTO = new AvailabilityDTO();

		final List<LocalDate> datesBetween = getDatesBetween(dayStart, dayEnd);

		List<AvailabilityDayHourDTO> ore = new ArrayList<>(0);
		for (int i = 0; i < 24; ) {
			final LocalTime oraDa = LocalTime.of(i++, 0, 0);

			final LocalTime oraA;
			if (i == 24) {
				oraA = LocalTime.of(23, 59, 59);
			} else {
				oraA = LocalTime.of(i, 0, 0);
			}

			AvailabilityDayHourDTO availabilityDayHourDTO = verificaDisponibilita(roomId, dayStart, dayEnd, oraDa, oraA);
			availabilityDayHourDTO.setOraDa(oraDa);
			availabilityDayHourDTO.setOraA(oraA);
			ore.add(availabilityDayHourDTO);
		}

		for(LocalDate currentDay : datesBetween){
			AvailabilityDayDTO availabilityDayDTO = new AvailabilityDayDTO();
			availabilityDayDTO.setGiorno(currentDay);
			availabilityDayDTO.setOre(ore);
			availabilityDTO.getGiorni().add(availabilityDayDTO);

		}


		final List<AvailabilityDayDTO> giorni = availabilityDTO.getGiorni();
		for(AvailabilityDayDTO day : giorni){
			final List<AvailabilityDayHourDTO> ored = day.getOre();
			final boolean disponibile = ored.stream().filter(p -> p.getStato().equals(AvailabilityDayHourDTO.EStato.DISPONIBILE) || p.getStato().equals(AvailabilityDayHourDTO.EStato.DISPONIBILE_CON_RISERVA)).findFirst().isPresent();
			final boolean occupata = ored.stream().filter(p ->  p.getStato().equals(AvailabilityDayHourDTO.EStato.NON_DISPONIBILE) || p.getStato().equals(AvailabilityDayHourDTO.EStato.OCCUPATA) || p.getStato().equals(AvailabilityDayHourDTO.EStato.RISERVATA) ).findFirst().isPresent();
			boolean libera = disponibile && !occupata;
			day.setLibera(libera);
		}


		return availabilityDTO;
	}


	private static boolean hasOverlap(LocalTime t1Start, LocalTime t1End, LocalTime t2Start, LocalTime t2End) {
		return t1End.isAfter(t2Start) && t1Start.isBefore(t2End);
	}

	public static List<LocalDate> getDatesBetween(
			LocalDate startDate, LocalDate endDate) {
		
		List<LocalDate> allDates = null;

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		allDates = IntStream.iterate(0, i -> i + 1)
				.limit(numOfDaysBetween)
				.mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
		
		allDates.add(endDate);
		return allDates;
	}


}
