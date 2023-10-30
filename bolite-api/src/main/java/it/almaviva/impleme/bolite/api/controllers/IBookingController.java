package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.booking.DiscardDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.BookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.NewBookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.books.StatoDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.OpenDoorDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.VerificaPrezzoDTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error401DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error403DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error404DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error409DTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("v2/roombookings")
@Api(tags = {"bookings"}, authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface IBookingController {

    @ApiOperation(value = "Inserisce una prenotazione", tags = "bookings", nickname = "createRoomBooking", notes = "Servizio per il Cittadino", response = BookingDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Success", response = BookingDTO.class)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    BookingDTO createRoomBooking(@RequestBody @ApiParam(name = "body", value = "Dati della prenotazione", required = true) @Valid NewBookingDTO body);

    @ApiOperation(value = "Richiede apertura di una serratura", tags = "bookings", nickname = "OpenDoor")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success")})
    @PutMapping(value = "/doors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    <T> ResponseEntity<T> openDoor(@RequestBody @ApiParam(name = "body", value = "Dati della richiesta", required = true) @Valid OpenDoorDTO body) throws Exception;
    
    @ApiOperation(value = "Restituisce tutte le prenotazioni", notes = "Il servizio è disponibile agli operatori comunali. Il servizio filtra le pratiche in base al proprio comune",tags = "bookings",  response = BookingDTO.class, responseContainer = "List", nickname = "filterBookings")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success",  response = BookingDTO.class, responseContainer = "List")})
    @GetMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BookingDTO> filterBookings(
            @ApiParam(value = "Codice Fiscale Richiedente.", example = "PNCNDR80C25A390K") @RequestParam(value = "codiFisc", required = false)  String codiFisc,
            @ApiParam(value = "Stato della pratica", name = "stato", example = "1" , type = "integer") @RequestParam(value = "stato", required = false) Integer stato,
            @ApiParam(value = "Identificativo della struttura", name = "roomId", example = "6c54f8a7-f672-4e7c-b197-bf4b6ae249a7") @RequestParam(value = "roomId", required = false) UUID roomId,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per data inserimento a partire Da",example = "2020-01-01 09:00:00" ) @RequestParam(value = "bookingStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime bookingStart,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per data inserimento fino A", example = "2020-01-01 20:00:00") @RequestParam(value = "bookingEnd", required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime bookingEnd);

    @ApiOperation(value = "Dettaglio prenotazione", notes = "Restituice il dettaglio di una singola prenotazione", tags = "bookings",  response = BookingDTO.class, nickname = "getRoomBookingCaseFile")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = BookingDTO.class)})
    @GetMapping(value = "/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    BookingDTO getRoomBookingCaseFile(@PathVariable("id") @ApiParam(value = "Id della prenotazione.", example = "5c660bc3-a494-4098-8d60-aff92cc1ef56", required = true) UUID idCaseFile);

    @ApiOperation(value = "Restituisce le mie prenotazioni", tags = "bookings", nickname = "getMiePrenotazioni", notes = "Servizio per il Cittadino", response = BookingDTO.class, responseContainer = "list")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = BookingDTO.class, responseContainer = "List")})
    @GetMapping(value = "/bookings/users/{cf}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<BookingDTO> getMiePrenotazioni(@ApiParam(value = "Codice Fiscale Richiedente", example = "PNCNDR80C25A390K" ,required = true) @PathVariable("cf") String cf,
                                        @ApiParam(value = "Ente ovvero il Comune" , example = "SIF07", name = "enteId") @RequestParam(value = "enteId", required = false) String enteId,
                                        @ApiParam(value = "Stato della pratica." , type = "integer", example = "1", name = "statoId") @RequestParam(value = "statoId", required = false) Integer statoId,
                                        @ApiParam(value = "Identificativo della struttura", name = "roomId", example = "6c54f8a7-f672-4e7c-b197-bf4b6ae249a7") @RequestParam(value = "roomId", required = false) UUID roomId,
                                     @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca a partire Da", example = "2020-01-01 09:00:00" ) @RequestParam(value = "bookingStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime bookingStart,
                                     @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca fino A", example = "2020-01-01 20:00:00") @RequestParam(value = "bookingEnd", required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime bookingEnd);


    @ApiOperation(value = "Elenco stati", notes = "Elenco di tutti gli stati associabili ad una prenotazione",nickname = "getStati", response = StatoDTO.class, responseContainer = "List", tags = {"bookings","status"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = StatoDTO.class, responseContainer = "List")})
    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<StatoDTO> getStati();


    /**
     * invocato da apim
     * @param idCaseFile
     */
    @ApiOperation(value = "Revoca della prenotazione", nickname = "discardPratica", notes = "L'operatore può revocare una pratica validata per qualsiasi motivo")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Casefile Cancelled" ), @ApiResponse(code = 500, message = "The Casefile has already been canceled" ), @ApiResponse(code = 404, message = "Casefile not found in DB" )})
    @DeleteMapping(value="/bookings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void discardPratica(@ApiParam(required = true, value = "Id of the Casefile to be cancelled. Cannot be empty.", example = "84a08fab-d031-40bd-93c5-016178dcb1ab") @PathVariable("id") UUID idCaseFile,
                        @RequestBody  @ApiParam(value = "Motivo", required = true ) @Valid DiscardDTO body);

    @ApiOperation(value = "Verifica Prezzo", tags = {"prices"}, notes = "Calcolai il prezzo totale della prenotazione", nickname = "verificaPrezzo", response = VerificaPrezzoDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = VerificaPrezzoDTO.class)})
    @GetMapping(value="/bookings/prices", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    VerificaPrezzoDTO verificaPrezzo(@ApiParam(required = true, value = "Identificativo della struttura", name = "roomId", example = "6c54f8a7-f672-4e7c-b197-bf4b6ae249a7")  @RequestParam(value = "roomId") UUID roomId,
                                     @ApiParam(required = true, name = "eventId", value = "Identificativo evento/tariffa",example = "1" )@RequestParam(value = "eventId") Integer eventId,
                                     @ApiParam( name = "services" , allowMultiple = true, value = "Servizi di cui si vuole usufruire", example = "1" ) @RequestParam(required = false) List<Integer> services,
                                     @ApiParam(required = true, format = "yyyy-MM-dd", value = "Giorno Inizio prenotazione",example = "2020-01-01" ) @RequestParam(value = "dayStart") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dayStart,
                                     @ApiParam(format = "yyyy-MM-dd", value = "Giorno Fine prenotazione", example = "2020-01-31") @RequestParam(required = false, value = "dayEnd")   @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dayEnd,
                                     @ApiParam(format = "HH:mm:ss", value = "Ora Inizio Prenotazione",example = "09:00:00" ) @RequestParam(required = false, value = "hourStart") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime hourStart,
                                     @ApiParam(format = "HH:mm:ss", value = "Ora Fine Prenotazione", example = "20:00:00") @RequestParam(required = false, value = "hourEnd")   @DateTimeFormat(pattern = "HH:mm:ss")   LocalTime hourEnd,
                                     @ApiParam(required = true, value = "Indica se si vuole prenotare per l'intera giornata", example = "true", allowableValues = "true,false") @RequestParam(value = "interaGiornata") boolean interaGiornata
                                     );

}
