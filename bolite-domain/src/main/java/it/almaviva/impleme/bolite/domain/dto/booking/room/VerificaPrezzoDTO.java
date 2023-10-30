package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "VerificaPrezzo")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerificaPrezzoDTO {

    @NotNull
    @JsonProperty(value = "importo", required = true)
    @ApiModelProperty(required = true, example = "47.30")
    private BigDecimal importo;
}
