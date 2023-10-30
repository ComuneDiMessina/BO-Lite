package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.eai.ljsa.sdk.core.security.LjsaUser;
import it.almaviva.eai.pm.core.grpc.Group;
import it.almaviva.eai.pm.core.grpc.Role;
import it.almaviva.impleme.bolite.core.booking.IBookingService;
import it.almaviva.impleme.bolite.core.room.IRoomService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.core.throwable.UnauthorizedException;
import it.almaviva.impleme.bolite.domain.dto.booking.DiscardDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.BookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.NewBookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.StatoDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.OpenDoorDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.VerificaPrezzoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class BookingController implements IBookingController {

	private final IBookingService iBookingService;
	private final IRoomService iRoomService;

	public BookingController(IBookingService iBookingService, IRoomService iRoomService) {
		this.iBookingService = iBookingService;
		this.iRoomService = iRoomService;
	}

	@Override
	@RolesAllowed({ "CITTADINO" })
	public BookingDTO createRoomBooking(@Valid NewBookingDTO body) {

		checkPrivacy(body.getFlagPrivacy1(), body.getFlagPrivacy2(), body.getFlagPrivacy3(), body.getFlagPrivacy4());

		checkBody(body);

		return iBookingService.createRoomBooking(body);
	}

	private void checkPrivacy(Boolean flagPrivacy1, Boolean flagPrivacy2, Boolean flagPrivacy3, Boolean flagPrivacy4) {

		if(flagPrivacy1 == false || flagPrivacy2 == false ||  flagPrivacy3 == false ||  flagPrivacy4 == false ){
			throw new OverlapException(
							"Accettare i campi privacy");
		}
	}

	private void checkBody(NewBookingDTO body) {
		if(body.getRichiedente().getFlagOrganizzatore().equals(false) && body.getOrganizzatore() == null){
			throw new OverlapException(
					"JSON non valido: il campo organizzatore è obbligatorio");
		}
	}


	@Override
	@RolesAllowed({ "CITTADINO" ,"OPERATORE", "AMMINISTRATORE_SERVIZIO" })
	public BookingDTO getRoomBookingCaseFile(UUID idCaseFile) {
		return iBookingService.getRoomBookingCaseFile(idCaseFile);
	}

	@Override
	@RolesAllowed({ "CITTADINO" })
	public List<BookingDTO> getMiePrenotazioni(String cf, String enteId, Integer statoId, UUID roomId,
			LocalDateTime bookingStart, LocalDateTime bookingEnd) {
		LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String fiscalNumber = (String) ljsaUser.getClaims().get("fiscalNumber");
		if (fiscalNumber == null) {
			throw new UnauthorizedException("Utente non autorizzato");
		}

		if (!(fiscalNumber).equalsIgnoreCase("TINIT-" + cf)) {
			throw new UnauthorizedException("Utente non autorizzato");
		}
		return iBookingService.getMiePrenotazioni(cf, enteId, statoId, roomId, bookingStart, bookingEnd);
	}

	@Override
	@RolesAllowed({ "SUPER_AMMINISTRATORE", "OPERATORE", "AMMINISTRATORE_SISTEMA", "AMMINISTRATORE_SERVIZIO" })
	public List<BookingDTO> filterBookings(String codiFisc, Integer stato, UUID roomId, LocalDateTime bookingStart,
			LocalDateTime bookingEnd) {
		LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Role> roles = (List<Role>) ljsaUser.getRoles().stream().filter(role -> role instanceof Role)
				.map(Role.class::cast).collect(Collectors.toList());

		final Group group = (Group) ljsaUser.getGroups().stream().findFirst()
				.orElseThrow(() -> new NotFoundException("Errore Profile Manager: Gruppo"));
		
		Group.DomainValue dvC = group.getDomainvaluesList().stream()
				.filter(domainValue -> domainValue.getDomain().getName().equals("COMUNE")).findFirst()
				.orElseThrow(() -> new NotFoundException("Errore Profile Manager: Domain Value"));

		if (roles.stream().filter(r -> r.getName().equals("SUPER_AMMINISTRATORE")).findFirst().isPresent()) {
			return iBookingService.filterBookings(codiFisc, stato, roomId, bookingStart, bookingEnd, dvC.getValue(), null);
		}

		Group.DomainValue dvT = group.getDomainvaluesList().stream()
				.filter(domainValue -> domainValue.getDomain().getName().equals("TRIBUTO")).findFirst()
				.orElseThrow(() -> new NotFoundException("Errore Profile Manager: Domain Value"));

		return iBookingService.filterBookings(codiFisc, stato, roomId, bookingStart, bookingEnd, dvC.getValue(),
				dvT.getValue());
	}

	@Override
	@PermitAll
	public List<StatoDTO> getStati() {
		return iBookingService.getStati();
	}

	@Override
	@RolesAllowed({ "OPERATORE" })
	public void discardPratica(UUID idCaseFile, DiscardDTO motivo) {
		iBookingService.discardPratica(idCaseFile, motivo);
	}

	@Override
	@PermitAll
	public VerificaPrezzoDTO verificaPrezzo(UUID roomId, Integer eventId, List<Integer> services, LocalDate dayStart,
			LocalDate dayEnd, LocalTime hourStart, LocalTime hourEnd, boolean flagInteraGiornata) {

		final LocalDate giornoA = dayEnd != null ? dayEnd : dayStart;

		final BigDecimal importo = iBookingService.verificaPrezzo(roomId, eventId, services, dayStart, giornoA,
				hourStart, hourEnd, flagInteraGiornata);
		VerificaPrezzoDTO result = new VerificaPrezzoDTO();
		result.setImporto(importo);
		return result;
	}

	@Override
	public <T> ResponseEntity<T> openDoor(@Valid OpenDoorDTO body) throws Exception {

		iBookingService.OpenDoor(body);

		ResponseEntity<T> res = ResponseEntity.ok().body(null);
		return res;
	}

}
