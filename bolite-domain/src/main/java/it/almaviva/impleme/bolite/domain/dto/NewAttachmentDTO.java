package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "NuovoAllegato")
public class NewAttachmentDTO {

    @NotEmpty
    @ApiModelProperty( required = true , value = "Nome del file", example = "cartaidentita.pdf")
    @JsonProperty("fileName")
    private String fileName;


    @ApiModelProperty( value = "Descrizione del file", example = "documento d'identità")
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @ApiModelProperty( required = true, value = "Contenuto base64 del file", example = "cHJvdmEgcHJvdmEgcHJvdmE=")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "base64Content")
    private String base64Content;

}