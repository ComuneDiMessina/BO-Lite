package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.services.NewServiceDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@ApiModel(value = "NuovaStruttura")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewRoomDTO{

	@NotNull(message = "Valorizzare il campo id")
	@ApiModelProperty(required = true, example = "2ca3a7bb-8785-4fba-831d-085ce7f67f4d", notes = "Identificativo dello spazio")
	private UUID id;

	@NotBlank(message = "Il nome della struttura non è valido")
	@ApiModelProperty(required = true, example = "Sala Ovale", value = "Nome della stanza")
	private String nome;

	@ApiModelProperty(required = true, example = "SIF07", value = "Identificativo del Comune")
	@NotBlank(message = "Il codice del comune non è valido")
	private String comune;

	@ApiModelProperty(required = true, example = "1", value = "Identificativo del tipo Struttura")
	@NotNull(message = "Il tipo della struttura non è valido")
	private Integer tipoStruttura;

	@ApiModelProperty(required = true, example = "1", value = "Identificativo della categoria")
	@NotNull(message = "Il tipo della categoria non è valido")
	private Integer categoria;

	@ApiModelProperty(example = "100", value = "Capienza")
	private Integer capienza;

	@ApiModelProperty(required = true, example = "15", value = "Giorni di Anticipo")
	@JsonProperty(value = "giorniAnticipo", defaultValue = "15", required = true)
	@NotNull(message = "Il campo giorniAnticipo deve essere valorizzato")
	private Integer giorniAnticipo;

	@ApiModelProperty(example = "false", value = "Indica se la stanza è bloccata a tempo indeterminato", required = true)
	@JsonProperty(value = "blocked", required = true)
	@NotNull(message = "Il campo blocked deve essere valorizzato")
	private Boolean blocked;

	@ApiModelProperty( example = "bla bla bla", value = "Condizioni di utilizzo della sala")
	private String condizioniUtilizzo;

	@ApiModelProperty(required = true, value = "Tariffario per giorni e fasce orarie")
	@JsonProperty(value = "tariffario", required = true)
	@NotEmpty(message = "Il campo tariffario deve essere valorizzato con almeno una tariffa")
	private List<@Valid @NotNull NewTariffarioDTO> tariffario;

	@ApiModelProperty(required = true, value = "Orari di apertura della struttura")
	@JsonProperty(value =  "aperture", required = true)
	@NotEmpty(message = "Il campo aperture deve essere valorizzato con almeno una apertura")
	private List<@Valid @NotNull  NewAperturaDTO> aperture;

	@ApiModelProperty(notes = "Servizi accessori disponibili")
	@JsonProperty("servizi")
	private List<@Valid @NotNull NewServiceDTO> servizi;

	@ApiModelProperty(required = true, example = "true", value = "Indica se è consentito il servizio catering", notes = "Indica se è consentito il servizio catering")
	@JsonProperty(value = "catering", required = true)
	@NotNull(message = "Il campo catering deve essere valorizzato")
	private Boolean catering;

	@ApiModelProperty(required = true, example = "true", value = "Indica se sono ammesse strutture tecniche di terze parti", notes = "Indica se sono ammesse strutture tecniche di terze parti")
	@JsonProperty(required = true, value = "terzeParti")
	@NotNull(message = "Il campo terzeParti deve essere valorizzato")
	private Boolean terzeParti;

}