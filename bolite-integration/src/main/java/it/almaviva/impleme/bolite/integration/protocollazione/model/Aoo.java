package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class Aoo {
	@JsonProperty("codice")
	private String codice;
	@JsonProperty("descrizione")
	private String descrizione;
	@JsonProperty("email")
	private String email;

}
