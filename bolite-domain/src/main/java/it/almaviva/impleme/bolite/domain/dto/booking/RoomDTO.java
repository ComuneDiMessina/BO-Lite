package it.almaviva.impleme.bolite.domain.dto.booking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import it.almaviva.impleme.bolite.domain.dto.booking.room.RangeDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@ApiModel(value = "Room")
@Data
@JsonInclude(Include.NON_NULL)
public class RoomDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty( required = true)
    private UUID id;

    @JsonProperty(required = true)
    @NotNull(message = "Please provide municipality")
    @ApiModelProperty(required = true)
    private String municipality;

    @JsonProperty(required = true)
    @NotNull(message = "Please provide name")
    @ApiModelProperty(required = true)
    private String name;
    
    @JsonIgnore
    @JsonProperty(required = true)
    @ApiModelProperty(required = true)
    @NotNull(message = "Please provide availability")
    private Set<RangeDTO> availability;

}
