package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.booking.books.EventDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.reservation.NewReservationDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.*;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.UpdateServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error401DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error403DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error404DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error409DTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequestMapping
@Api(tags = {"rooms"}, authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface IRoomController {

    @ApiOperation(value = "Verifica Disponibilità", tags = {"rooms","availability"}, notes = "Restituisce il calendario su base oraria e giornaliera", nickname = "verificaDisponibilita", response = RoomDTO.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = AvailabilityDTO.class)})
    @GetMapping(value="v2/public/rooms/{roomId}/availabilitis", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AvailabilityDTO verificaDisponibilita(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della Struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                                          @ApiParam(required = true, format = "yyyy-MM-dd", value = "Giorno Da",example = "2020-01-01" ) @RequestParam(value = "dayStart") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dayStart,
                                          @ApiParam(format = "yyyy-MM-dd", value = "Giorno A", example = "2020-01-31") @RequestParam(required = false, value = "dayEnd")   @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dayEnd,
                                          @RequestParam @ApiParam( required = true,  example = "true") Boolean smart,
                                          @RequestParam @ApiParam(example = "1", required = true) Integer tariffa);

    @ApiOperation(value = "Dettaglio struttura", notes = "Restituisce le caratteristiche di una struttura", nickname = "getRoom",response = RoomDTO.class, tags = {"rooms"})
    @GetMapping(value = "v2/public/rooms/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Room is retrieved", response = RoomDTO.class)
            })
    @ResponseStatus(HttpStatus.OK)
    RoomDTO getRoom(@PathVariable("roomId") @ApiParam(required = true,
            value = "Identificativo della struttura",
            example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID id);

    @ApiOperation(value = "Elenco strutture per comune", notes = "Servizio per il cittadino", nickname = "getRooms", response = RoomDTO.class, responseContainer = "List", tags = {"rooms"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = RoomDTO.class, responseContainer = "List" )})
    @GetMapping(value = "v2/public/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<RoomDTO> getRooms(@RequestParam(required = true) @ApiParam(value =  "Identificativo del comune", name = "comune", example = "SIF07", required = true )  String comune,
                           @RequestParam(required = false) @ApiParam(value =  "Tipo struttura", name = "tipologia", example = "1", type = "integer")  Integer tipologia,
                           @RequestParam(required = false) @ApiParam(value =  "Categoria strutura", name = "categoria", example = "1", type = "integer")  Integer categoria);

    @ApiOperation(value = "Elenco strutture", notes = "Servizio diposnibile per l'operatore comunale. Il servizio restituisce solo le strutture del proprio comune", nickname = "getRoomsForAdmin", response = RoomDTO.class, responseContainer = "List", tags = {"rooms"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = RoomDTO.class, responseContainer = "List" )})
    @GetMapping(value="v2/rooms",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<RoomDTO> getRoomsForAdmin(Integer tipologia, Integer categoria);

    @ApiOperation(value = "Elimina una struttura",nickname = "deleteRoom", tags = {"rooms"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Room was successfully deleted")
           })
    @DeleteMapping(value="v2/rooms/{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoom(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID id);

    @ApiOperation(value = "Inserisce una nuova struttura", nickname = "createRoom", response = RoomDTO.class, tags = {"rooms"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Room created", response = RoomDTO.class)
           })
    @PostMapping(value="v2/rooms",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RoomDTO createRoom(@RequestBody  @ApiParam(value = "Dettaglio della struttura.", required = true ) @Valid NewRoomDTO body);


    @ApiOperation(value = "Aggiorna una struttura esistente", notes = " L'elenco degli attributi " +
            "sarà sempre complessivo. Eventuali attributi prima inviati e non più presenti, dovranno\n" +
            "essere eliminati.", nickname = "updateRoom", response = RoomDTO.class, tags = {"rooms"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Room created", response = RoomDTO.class)
          })
    @PutMapping(value="v2/rooms/{roomId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    RoomDTO updateRoom(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId, @RequestBody  @ApiParam(value = "Room to add. Cannot null or empty.", required = true ) @Valid UpdateRoomDTO body);


    @ApiOperation(value = "Inserisce un nuovo servizio", nickname = "createServizio", response = RoomDTO.class, tags = {"rooms","services"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Services created", response = RoomDTO.class)
            })
    @PostMapping(value="v2/rooms/{roomId}/services",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RoomDTO createServizio(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID id,
                           @RequestBody  @ApiParam(value = "Dattaglio del servizio", required = true ) @Valid NewServiceDTO body);

    @ApiOperation(value = "Cancella un servizio",nickname = "deleteServiceOfRoom", tags = {"rooms", "services"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Service was successfully deleted")
    })
    @DeleteMapping(value="v2/rooms/{roomId}/services/{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteServiceOfRoom(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                             @PathVariable("serviceId") @ApiParam(required = true, value = "Identificativo del servizio", example = "1") Integer serviceId );

    @ApiOperation(value = "Aggiorna un servizio",nickname = "updateServiceOfRoom", tags = {"rooms", "services"} ,response = ServiceDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Service was successfully deleted")
    })
    @PutMapping(value="v2/rooms/{roomId}/services/{serviceId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ServiceDTO updateServiceOfRoom(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                                   @PathVariable("serviceId") @ApiParam(required = true, value = "Identificativo del servizio", example = "1") Integer serviceId,
                                   @RequestBody  @ApiParam(value = "Dattaglio del servizio", required = true ) @Valid UpdateServiceDTO body);




    @ApiOperation(value = "Inserisce una nuova restrizione", nickname = "createReservation", response = RoomDTO.class, tags = {"rooms","reservations"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Reservation created", response = RoomDTO.class)
           })
    @PostMapping(value="v2/rooms/{roomId}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RoomDTO createReservation(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID id,
                              @RequestBody  @ApiParam(value = "Dettaglio della restrizione", required = true ) @Valid NewReservationDTO body);


    @ApiOperation(value = "Cancella una restrizione",nickname = "deleteReservationOfRoom", tags = {"rooms", "reservations"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Reservation was successfully deleted")
    })
    @DeleteMapping(value="v2/rooms/{roomId}/reservations/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteReservationOfRoom(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                                 @PathVariable("reservationId") @ApiParam(required = true, value = "Identificativo della restrizione", example = "1") Integer reservationId );


    @ApiOperation(value = "Inserisce un nuovo tariffario", nickname = "createTariffario", response = RoomDTO.class, tags = {"rooms","tariff"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tariff created", response = RoomDTO.class)
           })
    @PostMapping(value="v2/rooms/{roomId}/tariff",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RoomDTO createTariffario(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID id,
                           @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true ) @Valid NewTariffarioDTO body);

    @ApiOperation(value = "Cancella una tariffa",nickname = "deleteTariffario", tags = {"rooms", "tariff"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Tariff was successfully deleted")
        })
    @DeleteMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTariffario(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                                 @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId );

    @ApiOperation(value = "Aggiorna una tariffa",nickname = "updateTariffario", tags = {"rooms", "tariff"}, response = TariffarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Tariff was successfully updated")
    })
    @PutMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}", produces = MediaType.APPLICATION_JSON_VALUE,consumes =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    TariffarioDTO updateTariffario(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
                          @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId ,   @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true )  @Valid UpdateTariffarioDTO body);



    @ApiOperation(value = "Inserimento nuova categoria", nickname = "createCategory", response = TypologyDTO.class, tags = {"rooms", "categories"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Category created", response = CategoryDTO.class)
           })
    @PostMapping(value = "v2/rooms/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDTO createCategory(@RequestBody  @ApiParam(value = "Dettaglio della categoria", required = true ) @Valid NewCategoryDTO body);


    @ApiOperation(value = "Elenco categorie", tags = {"rooms","categories"}, notes = "Elenco di tutte le caterogie",nickname = "getCategoria", response = CategoryDTO.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation" ,responseContainer = "List", response = CategoryDTO.class)})
    @GetMapping( value = "v2/public/rooms/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CategoryDTO> getCategoria();


    @ApiOperation(value = "Eliminazione della categoria",nickname = "deleteCategoria",  notes = "Eliminazione della categoria", tags = {"rooms", "categories"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Category was successfully deleted")
           })
    @DeleteMapping(value="v2/rooms/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategoria(@PathVariable("id") @ApiParam(required = true, value = "Identificativo della categoria", example = "1") Integer id);


    @ApiOperation(value = "Restituisce tutte le tipologie di strutture", nickname = "getTypologies", response = EventDTO.class, responseContainer = "List", tags = {"typologies","rooms"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation",  response = TypologyDTO.class, responseContainer = "List")})
    @GetMapping(value="v2/public/rooms/typologies", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<TypologyDTO> getTypologies();

    @ApiOperation(value = "Eliminazione tipologia struttura",nickname = "deleteTipologiaStruttura",  notes = "Elimina una tipologia di struttura", tags = {"rooms", "typologies"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Category was successfully deleted")
           })
    @DeleteMapping(value="v2/rooms/typologies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTipologiaStruttura(@PathVariable("id") @ApiParam(required = true, value = "Identificativo della tipologia", example = "1") Integer id);

    @ApiOperation(value = "Crea una nuova tipologia di struttura", nickname = "createTypology", response = TypologyDTO.class, tags = {"rooms", "typologies"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TypologyDTO created", response = TypologyDTO.class)
            })
    @PostMapping(value="v2/rooms/typologies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TypologyDTO createTypology(@RequestBody  @ApiParam(value = "Dettaglio della tipologia", required = true ) @Valid NewTypologyDTO body);


    @ApiOperation(value = "Elenco eventi", tags = {"rooms","events"}, notes = "Elenco di tutte le tipologie di evento per struttura ",nickname = "getTipologieEvento", response = EventDTO.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation" ,responseContainer = "List", response = EventDTO.class)})
    @GetMapping( value = "v2/public/rooms/{roomId}/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<EventDTO> getTipologieEvento(@PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della Struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId);


    @ApiOperation(value = "Eliminazione fascia giornaliera",nickname = "deleteGiorno",  notes = "Elimina un intero giorno", tags = {"rooms", "tariff"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Category was successfully deleted")
    })
    @DeleteMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}/costoFasce/{cfId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteGiorno(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo dello spazio", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a")  UUID roomId,
            @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId ,
            @PathVariable("cfId") @ApiParam(required = true, value = "Identificativo del costoFascia", example = "1") Integer cfId );


    @ApiOperation(value = "Aggiorna una fascia oraria",nickname = "updateFascia", tags = {"rooms", "tariff"}, response = TariffarioDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Tariff was successfully updated")
    })
        @PutMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}/costoFasce/{cfId}/fascia/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    TariffarioDTO updateFascia(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId ,
            @PathVariable("cfId") @ApiParam(required = true, value = "Identificativo del costoFascia", example = "1") Integer cfId ,
            @PathVariable("id") @ApiParam(required = true, value = "Identificativo della fascia", example = "1") Integer id,
            @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true )  @Valid UpdateFasciaDTO body);

    @ApiOperation(value = "Eliminazione slot orario",nickname = "deleteFascia",  notes = "Elimina uno lost orario", tags = {"rooms", "tariff"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Category was successfully deleted")
    })
    @DeleteMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}/costoFasce/{cfId}/fascia/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteFascia(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId ,
            @PathVariable("cfId") @ApiParam(required = true, value = "Identificativo del costoFascia", example = "1") Integer cfId ,
            @PathVariable("id") @ApiParam(required = true, value = "Identificativo della fascia", example = "1") Integer id);


    @ApiOperation(value = "Nuova fascia giornaliera", nickname = "createGiorno", tags = {"rooms", "tariff"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Tariff was successfully updated")
    })
    @PostMapping(value="v2/rooms/{roomId}/tariff/{tariffarioId}/costoFasce", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    TariffarioDTO createGiorno(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @PathVariable("tariffarioId") @ApiParam(required = true, value = "Identificativo della tariffa", example = "1") Integer tariffId ,
            @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true )  @Valid NewRangeDTO body);


    @ApiOperation(value = "Eliminazione giorno di apertura",nickname = "deleteApertura",  notes = "Elimina un giorno di apertura", tags = {"rooms", "openings"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Category was successfully deleted")
    })
    @DeleteMapping(value="v2/rooms/{roomId}/openings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteApertura(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @PathVariable("id") @ApiParam(required = true, value = "Identificativo della fascia", example = "1") Integer id);

    @ApiOperation(value = "Nuovo giorno di apertura", nickname = "crateApertura", tags = {"rooms", "openings"}, response = RoomDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Tariff was successfully updated")
    })
    @PostMapping(value="v2/rooms/{roomId}/openings", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    RoomDTO crateApertura(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true )  @Valid NewAperturaDTO body);

    @ApiOperation(value = "Aggiorna orario di apertura", nickname = "crateApertura", tags = {"rooms", "openings"}, response = RoomDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Tariff was successfully updated")
    })
    @PutMapping(value="v2/rooms/{roomId}/openings/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    RoomDTO updateApertura(
            @PathVariable("roomId") @ApiParam(required = true, value = "Identificativo della struttura", example = "9f3d7187-e938-4bc8-83dd-8d874db66b6a") UUID roomId,
            @PathVariable("id") @ApiParam(required = true, value = "Identificativo della fascia", example = "1") Integer id,
            @RequestBody  @ApiParam(value = "Dettaglio della tariffa", required = true )  @Valid UpdateAperturaDTO body);

}
