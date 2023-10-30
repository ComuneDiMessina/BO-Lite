package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.eai.ljsa.sdk.core.security.LjsaUser;
import it.almaviva.eai.pm.core.grpc.Group;
import it.almaviva.impleme.bolite.core.room.IRoomService;
import it.almaviva.impleme.bolite.core.throwable.NotFoundException;
import it.almaviva.impleme.bolite.core.throwable.OverlapException;
import it.almaviva.impleme.bolite.domain.dto.booking.books.EventDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.reservation.NewReservationDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.*;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.UpdateServiceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class RoomController implements IRoomController {

	private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public RoomDTO getRoom(UUID id) {
        return roomService.getRoom(id);
    }

    @Override
    public List<RoomDTO> getRooms(String comune, Integer tipologia, Integer categoria) {
        return roomService.getRooms(comune, tipologia, categoria);
    }

    @Override
    @RolesAllowed({"SUPER_AMMINISTRATORE","AMMINISTRATORE_SERVIZIO","AMMINISTRATORE_SISTEMA","OPERATORE"})
    public List<RoomDTO> getRoomsForAdmin(Integer tipologia, Integer categoria) {
        LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Group group = (Group) ljsaUser.getGroups().stream().findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Gruppo"));
        //SIF07
        Group.DomainValue dv = group.getDomainvaluesList().stream().filter(domainValue -> domainValue.getDomain().getName().equals("COMUNE")).findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Domain Value"));

        return roomService.getRoomsForAdmin(tipologia, categoria, dv.getValue());
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteRoom(UUID id) {
        roomService.deleteRoom(id);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO createRoom(@Valid NewRoomDTO body) { LjsaUser ljsaUser = (LjsaUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();final Group group = (Group) ljsaUser.getGroups().stream().findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Gruppo"));
      Group.DomainValue dv = group.getDomainvaluesList().stream().filter(domainValue -> domainValue.getDomain().getName().equals("COMUNE")).findFirst().orElseThrow(()-> new NotFoundException("Errore Profile Manager: Domain Value"));
      checkTariffario(body.getTariffario());
      checkAperture(body.getAperture());
      return roomService.createRoom(body, dv.getValue());
    }

    private void checkAperture(List<NewAperturaDTO> aperture) {
        if(aperture != null || aperture.isEmpty()){
            return;
        }


        final List<NewAperturaDTO> collect = aperture.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(n -> n.getValue() > 1)
                .map(n -> n.getKey())
                .collect(Collectors.toList());

        if(!collect.isEmpty()){
            throw new OverlapException("Errore negli orari di apertura: valori duplicati");

        }
    }

    private void checkTariffario(List<NewTariffarioDTO> tariffario) {
        if(tariffario == null || tariffario.isEmpty()){
            return;
        }

        for(NewTariffarioDTO t : tariffario){
            final Boolean flagInteraSettimana = t.getFlagInteraSettimana();
            final BigDecimal costoSettimanale = t.getCostoSettimanale();
            final Boolean flagInteraGiornata = t.getFlagInteraGiornata();
            final BigDecimal costoInteraGiornata = t.getCostoInteraGiornata();
            final List<NewRangeDTO> costoFasce = t.getCostoFasce();
            final BigDecimal costoOrario = t.getCostoOrario();

            if(flagInteraSettimana && costoSettimanale == null || !flagInteraSettimana && costoSettimanale != null){
                throw new OverlapException("Tariffa errata: verificare la coerenza dei campi flagInteraSettimana e costoSettimanale");
            }

            if(flagInteraSettimana && costoSettimanale != null){
                if(flagInteraGiornata || costoInteraGiornata != null || costoOrario != null  || costoFasce != null){
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

                if(costoFasce != null && !costoFasce.isEmpty()){
                    throw new OverlapException("Tariffa errata: le tariffe giornaliera e fascia si escludono a  vicenda");
                }
            }

            if(costoOrario != null && costoOrario.compareTo(BigDecimal.ZERO) > 0 && costoFasce != null && !costoFasce.isEmpty()){
                throw new OverlapException("Tariffa errata: le tariffe oraria e fascia si escludono a  vicenda");
            }
        }
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO updateRoom(UUID roomId, @Valid UpdateRoomDTO body) {
        checkTariffario(body.getTariffario());
        checkAperture(body.getAperture());
        return roomService.updateRoom(roomId, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteServiceOfRoom(UUID roomId, Integer serviceId) {
        roomService.deleteServiceOfRoom(roomId, serviceId);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public ServiceDTO updateServiceOfRoom(UUID roomId, Integer serviceId, @Valid UpdateServiceDTO body) {
        return roomService.updateServiceOfRoom(roomId, serviceId, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteReservationOfRoom(UUID roomId, Integer reservationId) {
        roomService.deleteReservationOfRoom(roomId, reservationId);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO createServizio(UUID id, NewServiceDTO body) {
        return roomService.createServizio(id, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO createReservation(UUID id, NewReservationDTO body) {
        return roomService.createReservation(id, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO createTariffario(UUID id, @Valid  NewTariffarioDTO body) {
        checkTariffario(Arrays.asList(body));
        return roomService.createTariffario(id, body);

    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteTariffario(UUID roomId, Integer tariffId) {
        roomService.deleteTariffario(roomId, tariffId);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public TariffarioDTO updateTariffario(UUID roomId, Integer tariffId, @Valid UpdateTariffarioDTO body) {

        return roomService.updateTariffario(roomId,tariffId, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public CategoryDTO createCategory(@Valid NewCategoryDTO body) {
        return roomService.createCategory(body);
    }

    @Override
    public List<CategoryDTO> getCategoria() {
       return roomService.getCategorie();
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteCategoria(Integer id) {
        roomService.deleteCategoria(id);
    }

    @Override
    public List<TypologyDTO> getTypologies() {
        return roomService.getTypologies();
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteTipologiaStruttura(Integer id) {
        roomService.deleteTipologiaStruttura(id);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public TypologyDTO createTypology(@Valid NewTypologyDTO body) {
        return roomService.createTypology(body);
    }

    @Override
    public List<EventDTO> getTipologieEvento(UUID roomId) {
        return roomService.getTipologieEvento(roomId);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteGiorno(UUID roomId, Integer tariffId, Integer cfId) {
        roomService.deleteGiorno(roomId, tariffId, cfId);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public TariffarioDTO updateFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id, @Valid UpdateFasciaDTO body) {
        return roomService.updateFascia(roomId, tariffId, cfId,id, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteFascia(UUID roomId, Integer tariffId, Integer cfId, Integer id) {
        roomService.deleteFascia(roomId, tariffId, cfId,id);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public TariffarioDTO createGiorno(UUID roomId, Integer tariffId, @Valid NewRangeDTO body) {
        return roomService.crateGiorno(roomId, tariffId, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public void deleteApertura(UUID roomId, Integer id) {
        roomService.deleteApertura(roomId, id);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO crateApertura(UUID roomId, @Valid NewAperturaDTO body) {
        return roomService.createApertura(roomId, body);
    }

    @Override
    @RolesAllowed({"AMMINISTRATORE_SERVIZIO"})
    public RoomDTO updateApertura(UUID roomId, Integer id, @Valid UpdateAperturaDTO body) {
        return roomService.updateApertura(roomId, id, body);
    }

    @Override
    public AvailabilityDTO verificaDisponibilita(UUID roomId, LocalDate dayStart, LocalDate dayEnd, Boolean smart, Integer tariffa) {
        if(dayEnd != null && (dayEnd.equals(dayStart)|| dayEnd.isBefore(dayStart))){
            throw new OverlapException("Intervallo temporale non valido");
        }

        if(dayEnd != null && smart){ //FIXME CHECK TARIFFA fascia
            return roomService.verificaDisponibilitaSmart(roomId, dayStart, dayEnd);
        }

        return roomService.verificaDisponibilita(roomId, dayStart, dayEnd, tariffa);
    }


}
