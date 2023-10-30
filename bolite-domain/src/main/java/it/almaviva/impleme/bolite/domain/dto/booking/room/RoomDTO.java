package it.almaviva.impleme.bolite.domain.dto.booking.room;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.books.EventDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.reservation.ReservationDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@ApiModel(value = "DettaglioStruttura")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {

    @NotNull
    @ApiModelProperty(required = true, example = "2ca3a7bb-8785-4fba-831d-085ce7f67f4d", notes = "Identificativo dello spazio")
    private UUID id;

    @ApiModelProperty(required = true, example = "Sala Ovale", value = "Nome della stanza")
    private String nome;

    @ApiModelProperty(required = true, example = "SIF07", notes = "Identificativo del Comune")
    @JsonProperty("comune")
    private String comune;

    @ApiModelProperty(required = true, example = "1", notes = "Identificativo del tipo Struttura")
    @JsonProperty("tipoStruttura")
    private Integer tipoStruttura;

    @ApiModelProperty(required = true, example = "1", notes = "Identificativo della categoria")
    @JsonProperty("categoria")
    private Integer categoria;

    @ApiModelProperty( example = "10.30", notes = "Capienza")
    @JsonProperty("capienza")
    private Integer capienza;

    @ApiModelProperty( example = "bla bla bla", notes = "Condizioni di utilizzo della sala")
    private String condizioniUtilizzo;

    @ApiModelProperty( example = "15", notes = "Giorni Aniticipo")
    @JsonProperty(value = "giorniAnticipo", defaultValue = "15")
    private Integer giorniAnticipo;

    @ApiModelProperty(notes = "Servizi accessori disponibili")
    @JsonProperty("servizi")
    private List<ServiceDTO> servizi;

    @ApiModelProperty(required = true, notes = "Elenco restrizioni attive")
    @JsonProperty("riserve")
    private List<ReservationDTO> riserve;

    @ApiModelProperty(required = true, notes = "Tariffario giorno/fasce")
    @JsonProperty("tariffario")
    private List<TariffarioDTO> tariffario;

    @ApiModelProperty(required = true, value = "Orari di apertura della struttura")
    @JsonProperty("aperture")
    private List<AperturaDTO> aperture;

    @ApiModelProperty(example = "true", notes = "Identifica se la stanza è bloccata a tempo indeterminato")
    @JsonProperty(value = "blocked")
    private Boolean blocked;

    @ApiModelProperty(example = "true", value = "Indica se è consentito il servizio catering", notes = "Indica se è consentito il servizio catering")
    @JsonProperty(value = "catering")
    private Boolean catering;

    @ApiModelProperty(example = "true", notes = "Indica se sono ammesse strutture tecniche di terze parti")
    @JsonProperty(value = "terzeParti")
    private Boolean terzeParti;

    @ApiModelProperty(value = "Eventi disponibili per la Struttura")
    @JsonProperty("eventi")
    private List<EventDTO> eventi;

}
