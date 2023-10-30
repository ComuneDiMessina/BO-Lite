package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@ApiModel(value = "UpdateStruttura")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRoomDTO {

    @ApiModelProperty(required = true, example = "Sala Ovale", value = "Nome della stanza")
    @NotBlank(message = "Valorizzare il campo nome")
    private String nome;

    @ApiModelProperty(required = true, example = "1", notes = "Identificativo del tipo Struttura")
    @NotNull(message = "Valorizzare il campo tipoStruttura")
    private Integer tipoStruttura;

    @ApiModelProperty(required = true, example = "1", notes = "Identificativo della categoria")
    @NotNull(message = "Valorizzare il campo categoria")
    private Integer categoria;

    @ApiModelProperty( example = "10.30", notes = "Capienza")
    private Integer capienza;

    @ApiModelProperty( example = "bla bla bla", notes = "Condizioni di utilizzo della sala")
    private String condizioniUtilizzo;

    @ApiModelProperty(required = true, example = "15", notes = "Giorni Aniticipo")
    @JsonProperty(required = true, value = "giorniAnticipo", defaultValue = "15")
    @NotNull(message = "Valorizzare il campo giorniAnticipo")
    private Integer giorniAnticipo;

    @ApiModelProperty(required = true, example = "true", notes = "Identifica se la stanza è bloccata a tempo indeterminato")
    @NotNull(message = "Valorizzare il campo blocked")
    private Boolean blocked;

    @ApiModelProperty(required = true, example = "true", value = "Indica se è consentito il servizio catering", notes = "Indica se è consentito il servizio catering")
    @NotNull(message = "Valorizzare il campo catering")
    private Boolean catering;

    @ApiModelProperty(required = true, example = "true", notes = "Indica se sono ammesse strutture tecniche di terze parti")
    @NotNull(message = "Valorizzare il campo terzeParti")
    private Boolean terzeParti;

    @ApiModelProperty(value = "Tariffario per giorni e fasce orarie")
    private List<@Valid @NotNull NewTariffarioDTO> tariffario;

    @ApiModelProperty( value = "Orari di apertura della struttura")
    private List<@Valid @NotNull NewAperturaDTO> aperture;

    @ApiModelProperty(notes = "Servizi accessori disponibili")
    private List<@Valid @NotNull NewServiceDTO> servizi;

}

