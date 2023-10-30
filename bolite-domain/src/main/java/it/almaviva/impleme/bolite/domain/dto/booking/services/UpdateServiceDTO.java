package it.almaviva.impleme.bolite.domain.dto.booking.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@ApiModel
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateServiceDTO {

    @JsonProperty(required = true)
    @NotEmpty(message = "Valorizzare il campo descrizione")
    @ApiModelProperty(required = true,position = 1 ,example = "TV", notes = "Descrizione del servizio accessorio")
    private String descrizione;

    @JsonProperty(required = true)
    @NotEmpty(message = "Valorizzare il campo note")
    @ApiModelProperty(position = 2, example = "note", notes = "Eventuali Note")
    private String note;

    @JsonProperty(required = true, defaultValue = "0.00")
    @ApiModelProperty(position = 3, example = "3.00", notes = "Eventuale costo del servizio accessorio")
    private BigDecimal importo;

}
