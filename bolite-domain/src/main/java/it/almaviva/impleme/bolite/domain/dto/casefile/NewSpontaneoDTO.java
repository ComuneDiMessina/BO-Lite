package it.almaviva.impleme.bolite.domain.dto.casefile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.users.DebitoreDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Valid
@Data
@ApiModel(value = "NewSpontaneo")
public class NewSpontaneoDTO {

    @NotBlank(message = "Valorizzare il campo causale")
    @ApiModelProperty(required = true, value = "Causale del pagamento", example = "servizi cimiteriali")
    private String causale;

    @NotNull(message = "Valorizzare il campo importo")
    @ApiModelProperty(required = true, value = "Importo da pagare", notes = "Verrà aperta una posizione debitoria con tale importo", example = "48")
    private BigDecimal importo;

    @NotBlank(message = "Valorizzare il campo ente")
    @ApiModelProperty(required = true, value = "Identifica il Comune", notes = "Verrà aperta una posizione debitoria con riferimento all'ente", example = "SIF07")
    private String ente;

    @ApiModelProperty(value = "Quantità", notes = "Campo opzionale", example = "4")
    private Integer quantita;

    @ApiModelProperty(value = "Tariffa", notes = "Tariffa", example = "1")
    private Integer tariffa;

    @NotBlank(message = "Valorizzare il campo tributo")
    @JsonProperty(defaultValue = "01")
    @ApiModelProperty(required = true, value = "Identifica il tributo", notes = "Verrà aperta una posizione debitoria con riferimento al tributo", example = "01")
    private String tributo;

    @NotBlank(message = "Valorizzare il campo anno")
    @ApiModelProperty(required = true, value = "Anno di riferimento", example = "2020")
    private String anno;

    @NotNull(message = "Valorizzare il campo debitore")
    @Valid
    @ApiModelProperty(required = true, value = "debitore")
    private DebitoreDTO debitore;

    @NotNull(message = "Valorizzare il campo details")
    @JsonRawValue
    @JsonProperty(defaultValue = "{}")
    @ApiModelProperty(required = true, value = "Dettagli del tributo", notes = "Campi variabili che dipendono dal tipo di tributo", example = "{\"n° ospiti\":5,\"camere\":[1,2]}")
    private JsonNode details;
}
