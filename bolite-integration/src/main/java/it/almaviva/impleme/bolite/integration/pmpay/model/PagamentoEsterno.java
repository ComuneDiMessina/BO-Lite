package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PagamentoEsterno
 */
@Component
@Setter
@Getter
@ToString

public class PagamentoEsterno {
	@JsonProperty("ID_DEBITO")
	private String idDebito;

	@JsonProperty("IUV")
	private String IUV;

	@JsonProperty("DATA_PAGAMENTO")
	private String dataPagamento;

	@JsonProperty("NOTE_PAGAMENTO")
	private String notePagamento;

}
