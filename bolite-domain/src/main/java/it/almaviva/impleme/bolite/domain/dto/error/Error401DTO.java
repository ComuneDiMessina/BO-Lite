package it.almaviva.impleme.bolite.domain.dto.error;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Error 401")
public class Error401DTO {


    @ApiModelProperty( required = true , value = "Timestamp", example = "2020-11-06 17:37:51")
    private long timestamp;

    @ApiModelProperty( required = true , value = "Status Error", example = "UNAUTHORIZED")
      private int status;


    @ApiModelProperty( required = true , value = "Error message", example = "JWT in X-Auth-Token is null or empty")
      private String message;

    @ApiModelProperty( required = true , value = "Debug message", example = "JWT in X-Auth-Token is null or empty")
    private String debugMessage;

    @ApiModelProperty( required = true , value = "path", example = "/v2/petstore")
      private String path;

}
