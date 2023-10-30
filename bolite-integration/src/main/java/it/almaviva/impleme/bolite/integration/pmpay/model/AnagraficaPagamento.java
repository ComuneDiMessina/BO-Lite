package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
@NoArgsConstructor

public class AnagraficaPagamento {
	@JsonProperty("NOME")
	private String nome;
	
	@JsonProperty("COGNOME")
	private String cognome;
	
	@JsonProperty("INDIRIZZO")
	private String indirizzo;
	
	@JsonProperty("CITTA")
	private String citta;
	
	@JsonProperty("CAP")
	private String cap;
	
	@JsonProperty("PROVINCIA")
    private String provincia;
	
	@JsonProperty("MAIL")
	private String mail;
	
	@JsonProperty("RIF_TELEFONICO")
	private String rifTelefonico;

}
