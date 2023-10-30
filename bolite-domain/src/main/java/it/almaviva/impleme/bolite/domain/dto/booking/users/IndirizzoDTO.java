package it.almaviva.impleme.bolite.domain.dto.booking.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Data
@Valid
@ApiModel(value = "Indirizzo")
public class IndirizzoDTO {

    @ApiModelProperty(example = "Italia")
    private String stato;

    @NotEmpty
    @Max(2)
    @ApiModelProperty(example = "ME", required = true)
    private String provincia;

    @NotEmpty
    @ApiModelProperty(example = "Messina", required = true)
    private String comune;

    @NotEmpty
    @ApiModelProperty(example = "via Veneto 15", required = true)
    private String indirizzo;

    @ApiModelProperty(example = "45/B")
    private String civico;

    @ApiModelProperty(example = "00100")
    private String cap;

}
