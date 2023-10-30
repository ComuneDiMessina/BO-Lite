package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "Allegato")
public class AttachmentDTO {

    @NotEmpty
    @ApiModelProperty( required = true , value = "Nome del file", example = "cartaidentita.txt")
    @JsonProperty("fileName")
    private String fileName;


    @ApiModelProperty( value = "Descrizione del file", example = "documento d'identità")
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @ApiModelProperty( required = true, value = "Contenuto base64 del file", example = "cHJvdmEgcHJvdmEgcHJvdmE=")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "base64Content")
    private String base64Content;

    @NotNull
    @ApiModelProperty(required = true, value = "byte[]")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "content")
    private byte[] content;

    @NotEmpty
    @ApiModelProperty( required = true, value = "Identificativo del documento", example = "12353285-a725-4373-824f-d482fa89206f")
    @JsonProperty("idDocumentale")
    private String idDocument;
}