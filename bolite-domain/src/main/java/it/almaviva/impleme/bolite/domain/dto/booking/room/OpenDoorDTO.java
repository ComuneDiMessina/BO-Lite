package it.almaviva.impleme.bolite.domain.dto.booking.room;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Valid
@ApiModel(value = "OpenDoorRequest")
public class OpenDoorDTO {
	
    @NotNull
    @ApiModelProperty(required = true, example = "2ca3a7bb-8785-4fba-831d-085ce7f67f4d", notes = "Identificativo della pratica")
    private UUID casefileId;
    
    @NotNull
    @ApiModelProperty(required = true, example = "2ca3a7bb-8785-4fba-831d-085ce7f67f5d", notes = "Identificativo della serratura")
    private UUID lockId;

}
