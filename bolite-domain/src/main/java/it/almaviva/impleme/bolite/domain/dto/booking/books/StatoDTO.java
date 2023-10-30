package it.almaviva.impleme.bolite.domain.dto.booking.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Stato")
public class StatoDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, required = true)
    @ApiModelProperty(required = true, example = "1")
    private Integer id;

    @JsonProperty(required = true)
    @ApiModelProperty(required = true, example = "RIFIUTATA", notes = "label stato")
    private String stato;
}
