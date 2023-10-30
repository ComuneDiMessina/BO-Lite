package it.almaviva.impleme.bolite.domain.dto.booking;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel(value = "Revoca Prenotazione")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscardDTO {

    @JsonProperty(required = true)
    @NotEmpty
    @ApiModelProperty(required = true,position = 1 ,example = "Crollo struttura", notes = "Motivo per cui si sta revocando la prenotazione")
    private String motivo;
}
