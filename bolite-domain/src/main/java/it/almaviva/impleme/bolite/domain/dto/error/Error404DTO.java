package it.almaviva.impleme.bolite.domain.dto.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Error 404")
public class Error404DTO {


    @ApiModelProperty( required = true , value = "Timestamp", example = "1604668163861")
    private long timestamp;

    @ApiModelProperty( required = true , value = "Status Error", example = "404")
      private int status;

    @ApiModelProperty( required = true , value = "Status Error", example = "Not Found")
      private String error;

    @ApiModelProperty( required = true , value = "Error message", example = "Errore bla bla bla")
      private String message;

    @ApiModelProperty( required = true , value = "path", example = "/v2/petstore")
      private String path;

}
