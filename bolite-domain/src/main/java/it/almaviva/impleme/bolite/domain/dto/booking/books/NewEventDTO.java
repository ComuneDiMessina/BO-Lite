package it.almaviva.impleme.bolite.domain.dto.booking.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "NewEvento")
public class NewEventDTO {

    @JsonProperty(required = true)
    @NotNull(message = "Please provide evento")
    @ApiModelProperty(required = true, example = "Matrimonio")
    private String evento;
    
    @ApiModelProperty(example = "Descrizione")
    private String descrizione;
}
