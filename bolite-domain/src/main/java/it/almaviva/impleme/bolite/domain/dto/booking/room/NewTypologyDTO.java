package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Valid
@ApiModel(value = "NewTypology", description = "Tipo della struttura")
public class NewTypologyDTO {

    @JsonProperty(required = true)
    @NotBlank(message = "Valorizzare il campo tipo")
    @ApiModelProperty(example = "Struttura chiusa", notes = "label tipo struttura")
    private String tipo;
    
    @ApiModelProperty(example = "Descrizione", notes = "descrizione tipo struttura")
    private String descrizione;
}
