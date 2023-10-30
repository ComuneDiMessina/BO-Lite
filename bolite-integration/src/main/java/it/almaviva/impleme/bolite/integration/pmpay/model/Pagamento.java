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
public class Pagamento {
	@JsonProperty("RIF_PRATICA")
	private String rifPratica;

	@JsonProperty("CAUSALE_PAGAMENTO")
	private String causalePagamento;

	@JsonProperty("SERVIZIO_PAGAMENTO")
	private String servizioPagamento;

	@JsonProperty("DIVISA")
	private String divisa;

	@JsonProperty("IMPORTO_TOTALE")
	private String importoTotale;

	@JsonProperty("DATA_PAGAMENTO")
	private String dataPagamento;

	@JsonProperty("DATI_BOLLO")
	private DatiBollo datiBollo;
}
