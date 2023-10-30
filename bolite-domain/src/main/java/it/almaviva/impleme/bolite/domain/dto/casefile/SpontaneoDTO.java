package it.almaviva.impleme.bolite.domain.dto.casefile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Valid
@Data
@ApiModel(value = "Estremi Pratica")
public class SpontaneoDTO {

    @NotEmpty
    @ApiModelProperty(required = true, value = "Identifica il tributo", notes = "Verrà aperta una posizione debitoria con riferimento al tributo", example = "01")
    private String tributo;

    @NotEmpty
    @ApiModelProperty(required = true, value = "Codice fiscale del debitore/fruitore", example = "SLVFNC00A00H282X")
    private String codiceFiscale;

    @NotEmpty
    @ApiModelProperty(required = true, value = "IUV", example = "1234567890")
    private String iuv;

    @NotNull
    @ApiModelProperty(required = true, value = "Importo da pagare", notes = "Verrà aperta una posizione debitoria con tale importo", example = "100.30")
    private BigDecimal importo;
}
