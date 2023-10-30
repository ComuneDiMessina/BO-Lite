package it.almaviva.impleme.bolite.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(value = "Tribute")
@Data
@JsonInclude(Include.NON_NULL)
public class TributeDTO {
	
	@NotNull
	@ApiModelProperty(required = true, example = "01", notes = "Identificativo del tributo")
	@JsonProperty("IDTributo")
    private String id;
	
	@ApiModelProperty(required = true, example = "Nome", notes = "Nome del tributo")
	@JsonProperty("NomeTributo")
    private String descrizioneTributo;
	
	@ApiModelProperty(example = "Descrizione", notes = "Descrizione del tributo")
    @JsonProperty("DescrizioneTributo")
    private String descrizioneTributoEstesa;
	
	@ApiModelProperty(example = "Tipo", notes = "Tipo del tributo")
	@JsonProperty("TipoTributo")
    private String tipo;
	
	@ApiModelProperty(required = true, example = "2020-01-01 20:00:00", notes = "Data creazione del tributo")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	@JsonProperty("DataCreazione")
    private LocalDateTime dataCreazione;

}
