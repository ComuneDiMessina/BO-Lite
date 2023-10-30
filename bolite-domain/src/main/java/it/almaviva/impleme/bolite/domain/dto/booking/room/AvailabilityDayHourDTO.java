package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@Valid
@ApiModel(value = "AvailabilityDayHour")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityDayHourDTO {


    public enum EStato {
        @JsonProperty("DISPONIBILE")
        DISPONIBILE,
        @JsonProperty("DISPONIBILE CON RISERVA")
        DISPONIBILE_CON_RISERVA,
        @JsonProperty("RISERVATA DAL COMUNE")
        RISERVATA,
        @JsonProperty("OCCUPATA")
        OCCUPATA,
        @JsonProperty("NON DISPONIBILE")
        NON_DISPONIBILE,
        @JsonProperty("CHIUSA")
        CHIUSA;
    }

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

    @JsonProperty(required = true)
    @NotNull(message = "Please provide tipo")
    @ApiModelProperty(required = true, example = "DISPONIBILE CON RISERVA", value = "Stato della struttura nel periodo selezionato", allowableValues = "DISPONIBILE, DISPONIBILE CON RISERVA, RISERVATA DAL COMUNE, OCCUPATA")
    public EStato stato;

    @JsonProperty(required = true)
    @NotNull
    @ApiModelProperty(required = true, example = "5", value = "Numero di prenotazioni in stato ATTESA DI VALIDAZIONE")
    private Integer numeroInAttesa;

}
