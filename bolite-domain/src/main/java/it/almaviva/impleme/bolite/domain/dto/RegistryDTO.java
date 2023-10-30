package it.almaviva.impleme.bolite.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Registry")
public class RegistryDTO {
	
	private String esito;
	private String messaggio;
	private String anno;	
	private String numero;	
	private String data;	
	private String ora;

}
