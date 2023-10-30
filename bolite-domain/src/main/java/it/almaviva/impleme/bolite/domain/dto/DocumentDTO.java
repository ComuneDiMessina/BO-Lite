package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(value = "NewDocument")
public class DocumentDTO {

    @NotNull
    @Valid
    @JsonProperty(required = true, value = "attachments")
    @ApiModelProperty(required = true)
    private List<AttachmentDTO> attachments;
    

}