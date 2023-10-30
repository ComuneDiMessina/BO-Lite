package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Estremi {
	@JsonProperty("data")
	private String data;
	@JsonProperty("numero")
	private String numero;
	@JsonProperty("importo")
	private String importo;
	@JsonProperty("allegati")
	private String allegati;

}
