package it.almaviva.impleme.bolite.domain.dto.booking.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "Evento")
public class EventDTO {

    @NotNull
    @JsonProperty(required = true, access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(required = true, example = "1", dataType = "integer")
    private Integer id;

    @JsonProperty(required = true)
    @NotNull(message = "Please provide event")
    @ApiModelProperty(required = true, example = "Matrimonio")
    private String evento;

    @ApiModelProperty(example = "Descrizione")
    private String descrizione;
}
