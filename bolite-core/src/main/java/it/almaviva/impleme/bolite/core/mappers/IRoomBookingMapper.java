package it.almaviva.impleme.bolite.core.mappers;

import it.almaviva.impleme.bolite.domain.dto.booking.books.BookingDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.LegaleDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.OrganizzatoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.PresidenteDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingServiceEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoomBookingMapper {

    @Mapping(target = "roomId", source = "booking.room.codice")
    @Mapping(target = "nome", source = "booking.room.nome")
    @Mapping(target = "numeroPratica", source = "codice")
    @Mapping(target = "importo", source = "importo")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "dataPrenotazione", source = "creationDate")
    @Mapping(target = "stato", source = "stato.id")
    @Mapping(target = "protocollo", source = "numeroProtocollo")
    @Mapping(target = "dataProtocollo", source = "dataProtocollo")
    @Mapping(target = "giornoDa", source = "booking.bookingStartDate")
    @Mapping(target = "giornoA", source = "booking.bookingEndDate")
    @Mapping(target = "oraDa", source = "booking.bookingStartHour")
    @Mapping(target = "oraA", source = "booking.bookingEndHour")
    @Mapping(target = "tipoEnvento", source = "booking.eventType")
    @Mapping(target = "descrizioneEvento", source = "booking.eventDescription")
    @Mapping(target = "titoloEvento", source = "booking.eventTitle")
    @Mapping(target = "serviziUtilizzati", source = "booking.services")
    @Mapping(target = "ente", source = "ente.codice")
    @Mapping(target = "importoServizi", source = "booking.amountServizi")
    BookingDTO map (CaseFileEntity entity);

    @Mapping(target = "ragioneSociale", source = "ente_ragione_sociale")
    @Mapping(target = "name", source = "nome")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "codiFisc", source = "cf")
    @Mapping(target = "piva", source = "piva")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telephoneNumber", source = "telephoneNumber")
    @Mapping(target = "dataNascita", source = "birthDate")
    @Mapping(target = "luogoNascita", source = "birthPlace")
    @Mapping(target = "flagOrganizzatore", source = "flag_organizzatore")
    @Mapping(target = "flagEnte", source = "flag_ente")
    @Mapping(target = "indirizzo.stato", source = "residenza_stato")
    @Mapping(target = "indirizzo.provincia", source = "residenza_provincia")
    @Mapping(target = "indirizzo.comune", source = "residenza_comune")
    @Mapping(target = "indirizzo.indirizzo", source = "residenza_address")
    @Mapping(target = "indirizzo.civico", source = "residenza_civico")
    @Mapping(target = "indirizzo.cap", source = "residenza_cap")
    RichiedenteDTO mapRichiedente(CaseFileUserEntity entity);

    @Mapping(target = "ragioneSociale", source = "ente_ragione_sociale")
    @Mapping(target = "name", source = "nome")
    @Mapping(target = "surname", source = "surname")
    @Mapping(target = "codiFisc", source = "cf")
    @Mapping(target = "piva", source = "piva")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telephoneNumber", source = "telephoneNumber")
    @Mapping(target = "dataNascita", source = "birthDate")
    @Mapping(target = "luogoNascita", source = "birthPlace")
    @Mapping(target = "flagEnte", source = "flag_ente")
    @Mapping(target = "indirizzo.stato", source = "residenza_stato")
    @Mapping(target = "indirizzo.provincia", source = "residenza_provincia")
    @Mapping(target = "indirizzo.comune", source = "residenza_comune")
    @Mapping(target = "indirizzo.indirizzo", source = "residenza_address")
    @Mapping(target = "indirizzo.civico", source = "residenza_civico")
    @Mapping(target = "indirizzo.cap", source = "residenza_cap")
    OrganizzatoreDTO mapOrganizzatore(CaseFileUserEntity entity);

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cognome", source = "surname")
    @Mapping(target = "codiFisc", source = "cf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefono", source = "telephoneNumber")
    @Mapping(target = "dataNascita", source = "birthDate")
    @Mapping(target = "luogoNascita", source = "birthPlace")
    LegaleDTO mapLegale(CaseFileUserEntity entity);

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "cognome", source = "surname")
    @Mapping(target = "codiFisc", source = "cf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "telefono", source = "telephoneNumber")
    @Mapping(target = "dataNascita", source = "birthDate")
    @Mapping(target = "luogoNascita", source = "birthPlace")
    PresidenteDTO mapPresidente(CaseFileUserEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "codice", source = "code")
    @Mapping(target = "descrizione", source = "description")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "importo", source = "amount")
    ServiceDTO map(BookingServiceEntity entity);

    List<ServiceDTO> map(List<BookingServiceEntity> entity);


}


