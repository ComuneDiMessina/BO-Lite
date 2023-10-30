package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Setter
@Getter
@ToString
public class PagamentoResponse {
	
	@JsonProperty("DATA_RICHIESTA")
	private Date dataRichiesta;
	
	@JsonProperty("ID_RICHIESTA")
	private String idrichiesta;

	@JsonProperty("CODICE_AZIENDA")
	private String codiceAzienda;

	@JsonProperty("URL_TO_CALL")
	private String urlToCall;

	@JsonProperty("CODICE_ERRORE")
	private Integer codiceErrore;

	@JsonProperty("DESCRIZIONE_ERRORE")
	private String descrizionErrore;


	
	

}
