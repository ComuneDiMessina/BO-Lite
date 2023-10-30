package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error401DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error403DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error404DTO;
import it.almaviva.impleme.bolite.domain.dto.error.Error409DTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v2/documents")
@Api( tags = "documents", authorizations =  {@Authorization(value = "X-Auth-Token"), @Authorization(value = "Bearer") })
@ApiResponses(value = {
        @ApiResponse(code = 403, message = "Forbidden", response = Error403DTO.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error401DTO.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error404DTO.class),
        @ApiResponse(code = 409, message = "Invalid", response = Error409DTO.class)})
public interface IDocumentController {
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fornisce i metadati dell'allegato", notes = "L'identificato dell'allegato è l'id del documentale", response = AttachmentDTO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = AttachmentDTO.class)})
    @ResponseStatus(HttpStatus.OK)
    AttachmentDTO getDocument(@PathVariable("id") @ApiParam(required = true, value = "Identificativo dell'allegato.", example = "12353285-a725-4373-824f-d482fa89206f") String idDocument);

    @ApiOperation(value = "Fornisce il contenuto dell'allegato",  notes = "Restituisce il byte[]", response = byte[].class)
    @GetMapping(value="/{id}/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = byte[].class)})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    ResponseEntity<Object> getDocumentContent(@PathVariable("id") @ApiParam(required = true, value = "Identificativo dell'allegato.", example = "12353285-a725-4373-824f-d482fa89206f") String idDocument);
}
