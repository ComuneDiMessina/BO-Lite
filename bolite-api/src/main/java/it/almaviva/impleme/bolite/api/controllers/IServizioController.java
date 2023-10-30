package it.almaviva.impleme.bolite.api.controllers;

import io.swagger.annotations.*;
import it.almaviva.impleme.bolite.domain.dto.NewServizioDTO;
import it.almaviva.impleme.bolite.domain.dto.ServizioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping
@Api(tags = { "casefiles-types" }, authorizations = { @Authorization(value = "X-Auth-Token"),
        @Authorization(value = "Bearer") })
public interface IServizioController {

    @ApiOperation(value = "Restituisce l'elenco di tipologie di pratiche disponibili", response = ServizioDTO.class, responseContainer = "List", nickname = "getServizi")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ServizioDTO.class, responseContainer = "List") })
    @GetMapping(value =  "v2/public/types", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<ServizioDTO> getServizi();

    @ApiOperation(value = "Inserisce una nuova tipologia di pratica", nickname = "createServizio", response = ServizioDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Servizio created", response = ServizioDTO.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Servizio already exists") })
    @PostMapping(value =  "v2/types", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ServizioDTO createServizio(@RequestBody @ApiParam(value = "Servizio to add. Cannot null or empty.", required = true) @Valid NewServizioDTO body);

    @ApiOperation(value = "Elimina una tipologia di pratica", nickname = "deleteServizio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Tariffa not found") })
    @DeleteMapping(value = "v2/types/{idServizio}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    void deleteServizio(@PathVariable("idServizio") @ApiParam(value = "Id del servizio", example = "1", required = true) Integer idServizio);

    @ApiOperation(value = "Aggiorna una tipologia di pratica", nickname = "updateServizio", response = ServizioDTO.class)
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Servizio updated", response = ServizioDTO.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Servizio already exists") })
    @PutMapping(value = "v2/types/{idServizio}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    ServizioDTO updateServizio(@PathVariable("idServizio") @ApiParam(value = "Id del servizio", example = "1", required = true) Integer idServizio, @RequestBody @ApiParam(value = "Servizio to add. Cannot null or empty.", required = true) @Valid ServizioDTO body);

}
