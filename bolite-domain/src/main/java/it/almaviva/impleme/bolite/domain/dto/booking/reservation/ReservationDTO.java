package it.almaviva.impleme.bolite.domain.dto.booking.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel(value = "Reservation")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReservationDTO {

    @ApiModelProperty(example = "1", notes = "Identificativo")
    private Integer id;

    @ApiModelProperty(example = "bla bla bla")
    private String note;

    @NotEmpty
    @ApiModelProperty(required = true, example = "alluvione")
    private String motivo;

    @NotNull
    @ApiModelProperty(required = true, notes = "Data inizio indiponibilità", example = "2020-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoDa", required = true)
    private LocalDate giornoDa;

    @NotNull
    @ApiModelProperty(required = true, notes = "Data fine indiponibilità", example = "2020-01-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoA", required = true)
    private LocalDate giornoA;

    @NotNull
    @ApiModelProperty(required = true, notes = "Ora fine indiponibilità", example = "09:00:00")
    @JsonProperty(value = "oraDa", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @NotNull
    @ApiModelProperty(required = true, notes = "Ora fine indiponibilità", example = "10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA", required = true)
    private LocalTime oraA;



}
