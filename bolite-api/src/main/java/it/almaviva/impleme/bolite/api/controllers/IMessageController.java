package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.error.Error401DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error403DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error404DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error409DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/v2/messages")
@Api(tags = {"workflow"}, authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface IMessageController {

    /**
     * invocato da apim
     * @param idCaseFile
     */
    @ApiOperation(value = "Annulla il wf di una pratica", nickname = "cancelOrder", notes = "Il servizio è ad uso esclusivo del cittadino e permette di annullare in qualsiasi momento la lavorazione della pratica")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Pratica annullata" )})
    @DeleteMapping(value="/casefiles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void cancelOrder(@ApiParam(required = true, value = "Identificativo della pratica", example = "84a08fab-d031-40bd-93c5-016178dcb1ab") @PathVariable("id") UUID idCaseFile);

    /**
     * invocato da servizi interni non esporre
     *
     * @param idCaseFile
     */
    @Deprecated
    @ApiOperation(value = "Invia il messaggio di pagamento ricevuto per la pratica identificata da: id", nickname = "paymentRcv", notes = "Sblocca il wf rimasto in attesa del pagamento")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "Payment Received" ), @ApiResponse(code = 404, message = "Casefile not found in DB" )})
    @PutMapping(value="/casefiles/{id}/outstandingdebts", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void paymentRcv(@ApiParam(required = true, value = "Identificativo della pratica", example = "84a08fab-d031-40bd-93c5-016178dcb1ab") @PathVariable("id") UUID idCaseFile);

   }
