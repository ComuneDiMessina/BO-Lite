package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;


@Data
@Valid
@ApiModel
public class UpdateRangeDTO {

	@ApiModelProperty(example = "LUN", allowableValues = "LUN,MAR,MER,GIO,VEN,SAB,DOM", notes = "Giorni della settimana")
	@JsonProperty(value = "giorno")
	private AperturaDTO.DAYS giorno;

	@ApiModelProperty(notes = "fasce")
	@JsonProperty(value = "fasce")
	private List<NewFasciaDTO> fasce;

}
