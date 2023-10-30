package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Valid
@ApiModel
public class NewRangeDTO {

	@ApiModelProperty(required = true, example = "LUN", allowableValues = "LUN,MAR,MER,GIO,VEN,SAB,DOM", notes = "Giorni della settimana")
	@JsonProperty(value = "giorno", required = true)
	@NotNull(message = "Il campo giorno deve essere valorizzato")
	@Valid
	private AperturaDTO.DAYS giorno;

	@ApiModelProperty(required = true, notes = "fasce")
	@JsonProperty(value = "fasce")
	@NotEmpty(message = "Il campo fasce deve essere valorizzato")
	private List<@Valid @NotNull NewFasciaDTO> fasce;

}
