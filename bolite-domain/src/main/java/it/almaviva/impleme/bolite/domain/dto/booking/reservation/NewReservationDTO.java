package it.almaviva.impleme.bolite.domain.dto.booking.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@ApiModel(value = "NewReservation")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewReservationDTO {

    @ApiModelProperty( example = "bla bla bla", notes = "note")
    private String note;

    @NotBlank(message = "Valorizzare il campo motivo")
    @ApiModelProperty(required = true, example = "alluvione")
    private String motivo;

    @NotNull(message = "Valorizzare il campo giornoDa")
    @ApiModelProperty(required = true, notes = "Data inizio indiponibilità", example = "2020-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoDa", required = true)
    private LocalDate giornoDa;

    @NotNull(message = "Valorizzare il campo giornoA")
    @ApiModelProperty(required = true, notes = "Data fine indiponibilità", example = "2020-01-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
    @JsonProperty(value = "giornoA", required = true)
    private LocalDate giornoA;

    @NotNull(message = "Valorizzare il campo oraDa")
    @ApiModelProperty(required = true, notes = "Ora fine indiponibilità", example = "09:00:00")
    @JsonProperty(value = "oraDa", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime oraDa;

    @NotNull(message = "Valorizzare il campo oraA")
    @ApiModelProperty(required = true, notes = "Ora fine indiponibilità", example = "10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty(value = "oraA", required = true)
    private LocalTime oraA;


}
