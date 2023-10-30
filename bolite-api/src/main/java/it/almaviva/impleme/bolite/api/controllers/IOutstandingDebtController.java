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

@RequestMapping("/v2/outstandingDebts")
@Api(tags = "posizione debitoria", authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface IOutstandingDebtController {

    @ApiOperation(value = "Notifica la ricezione della posizione debitoria", nickname = "paymentRcv", tags = "posizione debitoria" )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
    @PutMapping(value="/{id}/completed", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void paymentRcv(@ApiParam(required = true, value = "Identificativo della pratica", example = "84a08fab-d031-40bd-93c5-016178dcb1ab")@PathVariable("id") UUID id);



    @ApiOperation(value = "Notifica la ricezione della posizione debitoria", notes = "Identifica la pratica con IUV ed Ente", nickname = "paymentRcvByIuv", tags = "posizione debitoria" )
    @ApiResponses(value = { @ApiResponse(code = 200, message = "success") })
    @PutMapping(value="/completed", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void paymentRcvByIuv(@ApiParam(required = true, value = "IUV", example = "111111D11111")  @RequestParam(value = "iuv", name = "iuv") String iuv, @ApiParam(required = true, value = "Identificativo Ente", example = "SI07")  @RequestParam(value = "ente", name = "ente") String ente);
}
