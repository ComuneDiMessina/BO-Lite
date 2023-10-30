package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Valid
@ApiModel(value = "AvailabilityDay")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityDayDTO {


    @NotNull
    @ApiModelProperty(required = true, example = "2020-01-01", notes = "Giorno")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giorno", required = true)
    private LocalDate giorno;

    @NotNull
    @ApiModelProperty(required = true, notes = "Indica se il giorno è completamente libero durante l'orario di apertura")
    @JsonProperty(value = "libera", required = true)
    private Boolean libera = false;

    @NotNull
    private List<AvailabilityDayHourDTO> ore = new ArrayList<>(24);
}
