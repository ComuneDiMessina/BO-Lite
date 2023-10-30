package it.almaviva.impleme.bolite.core.mappers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.RichiedenteDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileDocumentEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {JsonParseException.class, IOException.class,ObjectMapper.class ,  Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICaseFileMapper {


    @Mapping(target = "causale", source = "causale")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "importo", source = "importo")
    @Mapping(target = "idCaseFile", source = "codice")
    @Mapping(target = "state", source = "stato.id")
    @Mapping(target = "ente", source = "ente.codice")
    @Mapping(target = "attachments", source = "documentEntities")
    @Mapping(target = "tributo", source = "tributo_id")
    @Mapping(target = "tipologia", source = "caseFileTypeEntity.name")
    @Mapping(target = "details", expression = "java( new ObjectMapper().readTree(entity.getDetails()))")
    CaseFileDTO map(CaseFileEntity entity) throws JsonParseException, IOException;

    List<CaseFileDTO> map(List<CaseFileEntity> entity);

    @Mapping(target = "fileName", source = "name")
    @Mapping(target = "description", source = "descrizione")
    @Mapping(target = "idDocument", source = "idDocument")
    AttachmentDTO map(CaseFileDocumentEntity entity);

    List<AttachmentDTO> mapAllegati(Iterable<CaseFileDocumentEntity> entity);

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
}


