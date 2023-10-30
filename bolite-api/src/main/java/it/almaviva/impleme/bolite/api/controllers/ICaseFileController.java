package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.CaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewCaseFileDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.NewSpontaneoDTO;
import it.almaviva.impleme.bolite.domain.dto.casefile.SpontaneoDTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error401DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error403DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error404DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error409DTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("/v2/casefiles")
@Api( tags = {"lavorazione pratica"}, authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface ICaseFileController {

    @ApiOperation(value = "Elenco pratiche: restituisce tutte le pratiche", notes = "Servizio per Amministratori ed Operatori Comunali",tags = "lavorazione pratica",  response = CaseFileDTO.class, responseContainer = "List", nickname = "filterPratiche")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success",  response = CaseFileDTO.class, responseContainer = "List")})
    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CaseFileDTO> filterPratiche(
            @ApiParam(value = "Codice Fiscale del richiedente.", example = "PNCNDR80C25A390K") @RequestParam(value = "codiFisc", required = false)  String codiFisc,
            @ApiParam(value = "Identificativo stato della pratica", name = "stato", example = "1" , type = "integer") @RequestParam(value = "stato", required = false) Integer stato,
            @ApiParam(value = "Identificativo il tipo della pratica", name = "tipo", example = "1" , type = "integer") @RequestParam(value = "tipo", required = false) Integer tipo,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per Data creazione Da",example = "2020-01-01 09:00:00") @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per Data crezione A", example = "2020-01-01 20:00:00") @RequestParam(value = "end", required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime end);

    @ApiOperation(value = "Avvia una pratica generica con lavorazione manuale da parte dell'operatore", nickname = "createManualeCaseFile", tags = {"lavorazione pratica","pratica generica"}, notes = "Servizio dietro autenticazione", response = CaseFileDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "success", response = SpontaneoDTO.class) })
    @PostMapping(value = "/manuale" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    SpontaneoDTO createManualeCaseFile(@RequestBody @Valid @ApiParam(value = "Dati del pagamento", required = true) NewCaseFileDTO caseFileDTO);

    @ApiOperation(value = "Crea una nuova pratica per i pagamenti spontanei detti anche bollettino bianco", nickname = "createSpontaneousCaseFile", tags = {"lavorazione pratica","pratica automatica"}, notes = "Il servizio è senza autenticazione", response = SpontaneoDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "success", response = SpontaneoDTO.class) })
    @PostMapping(value = "/spontaneous" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    SpontaneoDTO createSpontaneousCaseFile(@RequestBody @Valid @ApiParam(value = "Dati del pagamento", required = true) NewSpontaneoDTO caseFileDTO);

    @ApiOperation(value = "Visualizza le mie pratiche", notes = "Il sistema visualizza le pratiche del richiedente filtrando per codice fiscale",tags = "lavorazione pratica",  response = CaseFileDTO.class, responseContainer = "List", nickname = "getMiePratiche")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success",  response = CaseFileDTO.class, responseContainer = "List")})
    @GetMapping( value = "/users/{cf}",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<CaseFileDTO> getMiePratiche(
            @ApiParam(value = "Codice Fiscale Richiedente.", required = true, example = "PNCNDR80C25A390K") @PathVariable(value = "cf")  String codiFisc,
            @ApiParam(value = "Identificativo dell stato della pratica", name = "stato", example = "1" , type = "integer") @RequestParam(value = "stato", required = false) Integer stato,
            @ApiParam(value = "Ente ovvero il Comune" , example = "SIF07", name = "enteId") @RequestParam(value = "enteId", required = false) String enteId,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per Data creazione Da",example = "2020-01-01 09:00:00" ) @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @ApiParam(format = "yyyy-MM-dd HH:mm:ss", value = "Cerca per Data creazione A", example = "2020-01-01 20:00:00") @RequestParam(value = "end", required = false)   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  LocalDateTime end,
            @ApiParam(value = "Identificativo del tipo della pratica", name = "tipo", example = "1" , type = "integer") @RequestParam(value = "tipo", required = false) Integer tipo);

    @ApiOperation(value = "Restituisce una determinanata pratica", tags = "lavorazione pratica", notes = "Filtra per identificativo pratica UUID. Il servizio è disponibile a tutti",response = CaseFileDTO.class, nickname = "getCaseFile")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = CaseFileDTO.class)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    CaseFileDTO getCaseFile(@PathVariable("id") @ApiParam(value = "Id della pratica.", example = "5c660bc3-a494-4098-8d60-aff92cc1ef56", required = true) UUID idCaseFile);


    @ApiOperation(value = "Restituisce gli allegati afferenti ad una pratica", tags = {"lavorazione pratica", "bookings", "documents"}, response = AttachmentDTO.class ,responseContainer = "List" )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success", response = AttachmentDTO.class,responseContainer = "List") })
    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AttachmentDTO> getCaseFileAttachments(@PathVariable("id") @ApiParam(value = "Id della pratica", example = "5c660bc3-a494-4098-8d60-aff92cc1ef56", required = true) UUID idCaseFile);

    @ApiOperation(value = "Aggiunge allegati alla pratica",  tags = {"lavorazione pratica", "bookings", "documents"}, notes = "Il servizio consente il caricamento di uno o più files")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "success") })
    @PostMapping(value = "/{id}/documents",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void loadCaseFileAttachments(@PathVariable("id") @ApiParam(value = "Id della pratica.", example = "5c660bc3-a494-4098-8d60-aff92cc1ef56", required = true) UUID idCaseFile,
                                        @RequestBody @ApiParam(value = "Lista di allegati.", required = true) List<NewAttachmentDTO> documentDTO) throws IOException;
}
