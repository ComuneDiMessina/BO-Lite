package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalTime;


@Data
@Valid
@ApiModel
public class UpdateFasciaDTO {

    @NotNull
    @ApiModelProperty( required = true, example = "09:00:00")
    @JsonProperty(value = "oraDa", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @NotNull
    @ApiModelProperty(position = 1, required = true, example = "10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA", required = true)
    private LocalTime oraA;

    @ApiModelProperty(position = 2, example = "10.30", notes = "Costo della fascia")
    @JsonProperty("costoFascia")
    private BigDecimal costoFascia;
}
