package it.almaviva.impleme.bolite.domain.dto.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.room.RangeDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@ApiModel(value = "UpdateRoom")
public class UpdateRoomDTO {

    @JsonProperty(required = true)
    @NotNull
    @ApiModelProperty(required = true)
    private Set<RangeDTO> availability;
}
