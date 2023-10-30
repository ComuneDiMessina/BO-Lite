package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RegistraPagamento
 */
@Component
@Setter
@Getter
@ToString

public class RegistraPagamento {
	@JsonProperty("ID_DEBITO")
	private String ID_DEBITO;

	@JsonProperty("RIF_PRATICA")
	private String RIF_PRATICA;

	@JsonProperty("CAUSALE_PAGAMENTO")
	private String CAUSALE_PAGAMENTO;

	@JsonProperty("SERVIZIO_PAGAMENTO")
	private String SERVIZIO_PAGAMENTO;

	@JsonProperty("CODICE_FISCALE")
	private String CODICE_FISCALE;

	@JsonProperty("DIVISA")
	private String DIVISA;

	@JsonProperty("IMPORTO_TOTALE")
	private String IMPORTO_TOTALE;

	@JsonProperty("DATA_SCADENZA")
	private String DATA_SCADENZA;

	@JsonProperty("DATI_BOLLO")
	private DatiBollo DATI_BOLLO;

	@JsonProperty("ANAGRAFICA")
	private AnagraficaPagamento ANAGRAFICA;

}
