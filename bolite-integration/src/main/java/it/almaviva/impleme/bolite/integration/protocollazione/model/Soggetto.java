package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Builder
@Getter
@ToString
public class Soggetto {
	@JsonProperty("denominazione")
	private String denominazione;
	@JsonProperty("indirizzo")
	private String indirizzo;
	@JsonProperty("nota")
	private String nota;

}
