package it.almaviva.impleme.bolite.domain.dto.booking.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "Servizio")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDTO extends NewServiceDTO{


    @ApiModelProperty(example = "1", notes = "Identificativo del Servizio")
    private Integer id;

}
