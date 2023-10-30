package it.almaviva.impleme.bolite.domain.dto.booking.room;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Valid
@ApiModel(value = "NewCategory", description = "Categoria della struttura")
public class NewCategoryDTO {

    @JsonProperty(required = true)
    @NotBlank(message = "Valorizzare il campo categoria")
    @ApiModelProperty(required = true, example = "PROVA", notes = "label categoria")
    private String categoria;
    
    @ApiModelProperty(example = "descrizione", notes = "descrizione categoria")
    private String descrizione;
}
