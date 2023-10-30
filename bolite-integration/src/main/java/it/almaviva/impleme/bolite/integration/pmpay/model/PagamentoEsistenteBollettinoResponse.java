package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PagamentoEsistenteBollettinoResponse
 */
@Component
@Setter
@Getter
@ToString

public class PagamentoEsistenteBollettinoResponse {
	@JsonProperty("esito")
	private String esito;

	@JsonProperty("errore")
	private String errore;

	@JsonProperty("bollettino_inline")
	private BollettinoInline bollettinoInline;

	@JsonProperty("bollettino")
	private Bollettino bollettino;

}
