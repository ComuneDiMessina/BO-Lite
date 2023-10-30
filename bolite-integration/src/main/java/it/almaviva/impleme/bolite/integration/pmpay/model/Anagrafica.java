package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
@Component
@Setter
@Getter
@ToString
public class Anagrafica {
	@JsonProperty("codice")
	private String codice;
	@JsonProperty("denominazione")
	private String denominazione;
	@JsonProperty("indirizzo")
	private String indirizzo;
	@JsonProperty("email")
	private String email;
	@JsonProperty("nota")
	private String nota;

}
