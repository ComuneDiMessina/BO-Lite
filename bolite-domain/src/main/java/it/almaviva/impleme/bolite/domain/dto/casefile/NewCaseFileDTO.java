package it.almaviva.impleme.bolite.domain.dto.casefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.NewAttachmentDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.DebitoreDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.users.FruitoreDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Valid
@Data
@ApiModel(value = "NuovaPratica")
public class NewCaseFileDTO {

	@NotBlank(message = "Valorizzare il campo ente")
	@ApiModelProperty(required = true, value = "Identifica il Comune", notes = "Verrà aperta una posizione debitoria con riferimento all'ente", example = "SIF07")
	private String ente;

	@Valid
	@JsonProperty
	private DebitoreDTO fruitore;

	@NotNull(message = "Valorizzare il campo richiedente")
	@Valid
	@JsonProperty(required = true)
	@ApiModelProperty(required = true, value = "richiedente")
	private FruitoreDTO richiedente;

	@NotBlank(message = "Valorizzare il campo causale")
	@JsonProperty(required = true, value = "libero")
	@ApiModelProperty(required = true, value = "Causale", example = "Richiesta passo carrabile")
	private String causale;

	@JsonProperty(required = true, value = "tipologia" )
	@NotNull(message = "Valorizzare il campo  tipologia")
	@ApiModelProperty(example = "1", required = true, notes = "Tipo Pratica")
	private Integer tipologia;

	@Valid
	@ApiModelProperty(value = "Allegati alla pratica")
	private List<@NotNull @Valid NewAttachmentDTO> attachments;

	@ApiModelProperty( required = true, example = "true", notes = "Informativa Privacy")
	@NotNull(message = "Valorizzare il campo  flagPrivacy1")
	private Boolean flagPrivacy1;

	@NotNull(message = "Valorizzare il campo  flagPrivacy2")
	@ApiModelProperty( required = true, example = "true", notes = "Statuto legale")
	private Boolean flagPrivacy2;

	@NotNull(message = "Valorizzare il campo  flagPrivacy3")
	@ApiModelProperty( required = true, example = "true", notes = "Principi costituzione")
	private Boolean flagPrivacy3;

	@NotNull(message = "Valorizzare il campo  flagPrivacy4")
	@ApiModelProperty( required = true, example = "true", notes = "Condizioni Giunta Comunale")
	private Boolean flagPrivacy4;

}