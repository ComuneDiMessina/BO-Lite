package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "Tariffario")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TariffarioDTO {

    @ApiModelProperty(example = "1", notes = "Identificativo")
    private Integer id;

    @ApiModelProperty(position = 1,notes = "Costo per fasce")
    @JsonProperty(value = "costoFasce")
    private List<RangeDTO> costoFasce;

    @ApiModelProperty(position = 4,example = "50", notes = "Costo giornaliero")
    @JsonProperty("costoInteraGiornata")
    private BigDecimal costoInteraGiornata;

    @NotNull
    @ApiModelProperty(example = "false", notes = "Indica se si può prenotare per la settimana intera")
    @JsonProperty("flagInteraSettimana")
    private Boolean flagInteraSettimana;

    @NotNull
    @ApiModelProperty(example = "true", notes = "Indica se si può prenotare per intera giornata")
    @JsonProperty(value = "flagInteraGiornata")
    private Boolean flagInteraGiornata;

    @ApiModelProperty(position = 5,example = "1000", notes = "Costo mensile")
    @JsonProperty("costoSettimanale")
    private BigDecimal costoSettimanale;

    @ApiModelProperty(position = 6, example = "10", notes = "Costo mensile")
    @JsonProperty("costoOrario")
    private BigDecimal costoOrario;

    @ApiModelProperty(position = 7, example = "1", notes = "Tipo di evento")
    @JsonProperty("evento")
    private String tipoEvento;

    @ApiModelProperty(position = 8, example = "bla bla", notes = "Note")
    @JsonProperty(value = "note")
    private String note;

    @ApiModelProperty(position = 9, example = "bla bla", notes = "A FASCE")
    @JsonProperty(value = "tipo")
    private String tipo;

}
