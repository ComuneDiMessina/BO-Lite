package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * HeaderPagamentoEsistente
 */
@Component
@Setter
@Getter
@ToString

public class HeaderPagamentoEsistente {
	@JsonProperty("TOKEN")
	private String TOKEN;

	@JsonProperty("DATA_RICHIESTA")
	private String DATA_RICHIESTA;

	@JsonProperty("ID_RICHIESTA")
	private String ID_RICHIESTA;

	@JsonProperty("CODICE_FISCALE")
	private String CODICE_FISCALE;

	@JsonProperty("TIPO_CLIENT")
	private String TIPO_CLIENT;

}
