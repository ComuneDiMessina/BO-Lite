package it.almaviva.impleme.bolite.domain.dto.booking.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@ApiModel(value = "Fruitore")
public class FruitoreDTO extends DebitoreDTO{

  @JsonProperty(required = true)
  @NotNull(message = "Valorizzare il campo flagFruitore")
  @ApiModelProperty(required = true, example = "false", allowableValues = "true, false", value = "Valorizzare true se il richiedente è anche il fruitore")
  private Boolean flagFruitore;
}
