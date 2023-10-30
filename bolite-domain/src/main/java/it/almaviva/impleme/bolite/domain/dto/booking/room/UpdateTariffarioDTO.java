package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTariffarioDTO {

    @ApiModelProperty(position = 4,example = "50", notes = "Il campo è obbligatorio se flagInteraGiornata è true")
    @JsonProperty("costoInteraGiornata")
    private BigDecimal costoInteraGiornata;

    @NotNull(message = "Valorizzare il campo flagInteraSettimana")
    @ApiModelProperty(required = true, example = "false", notes = "Indica se si può prenotare per la settimana intera. Valorizzare a true se flagInteraGiornata è false")
    @JsonProperty(required = true, value = "flagInteraSettimana")
    private Boolean flagInteraSettimana;

    @NotNull(message = "Valorizzare il campo flagInteraGiornata")
    @ApiModelProperty(required = true, example = "true", notes = "Indica se si può prenotare per intera giornata. Valorizzare a true se flagInteraSettimana è false")
    @JsonProperty(required = true, value = "flagInteraGiornata")
    private Boolean flagInteraGiornata;

    @ApiModelProperty(position = 5,example = "1000", notes = "Il campo è obbligatorio se flagInteraSettimana è true")
    @JsonProperty("costoSettimanale")
    private BigDecimal costoSettimanale;

    @ApiModelProperty(position = 6, example = "10", notes = "Il campo è obbligatorio se flagInteraGiornata è true")
    @JsonProperty("costoOrario")
    private BigDecimal costoOrario;


    @ApiModelProperty(required = true, position = 7, example = "Matrimonio", notes = "Tipo di evento")
    @JsonProperty(required = true, value = "evento")
    @NotBlank(message = "Valorizzare il campo evento")
    private String tipoEvento;

    @ApiModelProperty(position = 8, example = "bla bla", notes = "Note")
    @JsonProperty(value = "note")
    private String note;

}
