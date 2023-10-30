package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Fascicolo {
	@JsonProperty("anno")
	private String anno;
	@JsonProperty("numero")
	private String numero;
	@JsonProperty("descrizione")
	private String descrizione;

}
