package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
@ApiModel(value = "Typology", description = "Tipo della struttura")
public class TypologyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(required = true, example = "1", dataType = "integer")
    private Integer id;

    @JsonProperty(required = true)
    @ApiModelProperty(example = "Struttura chiusa", notes = "label tipo struttura")
    private String tipo;
}
