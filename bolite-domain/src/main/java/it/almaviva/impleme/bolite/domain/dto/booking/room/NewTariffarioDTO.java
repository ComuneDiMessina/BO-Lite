package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ApiModel
@Data
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewTariffarioDTO {

    @ApiModelProperty(position = 1, notes = "Il campo è obbligatorio se flagInteraGiornata è false e flagInteraSettimana è false")
    @JsonProperty(value = "costoFasce")
    private List<@Valid @NotNull NewRangeDTO> costoFasce;

    @ApiModelProperty(position = 4,example = "50", notes = "Il campo è obbligatorio se flagInteraGiornata è true")
    private BigDecimal costoInteraGiornata;

    @NotNull(message = "Il campo flagInteraSettimana deve essere valorizzato")
    @ApiModelProperty(required = true, example = "false", notes = "Indica se si può prenotare per la settimana intera. Valorizzare a true se flagInteraGiornata è false")
    private Boolean flagInteraSettimana;

    @NotNull(message = "Il campo flagInteraGiornata deve essere valorizzato")
    @ApiModelProperty(required = true, example = "true", notes = "Indica se si può prenotare per intera giornata. Valorizzare a true se flagInteraSettimana è false")
    @JsonProperty(required = true, value = "flagInteraGiornata")
    private Boolean flagInteraGiornata;

    @ApiModelProperty(position = 5,example = "1000", notes = "Il campo è obbligatorio se flagInteraSettimana è true")
    private BigDecimal costoSettimanale;

    @ApiModelProperty(position = 6, example = "10", notes = "Il campo è obbligatorio se flagInteraGiornata è true")
    private BigDecimal costoOrario;

    @ApiModelProperty(required = true, position = 7, example = "Matrimonio", notes = "Tipo di evento")
    @JsonProperty(value = "evento", required = true)
    @NotBlank(message = "Il campo tipoEvento deve essere valorizzato")
    private String tipoEvento;

    @ApiModelProperty(position = 8, example = "bla bla", notes = "Note")
    @JsonProperty(value = "note")
    private String note;

}
