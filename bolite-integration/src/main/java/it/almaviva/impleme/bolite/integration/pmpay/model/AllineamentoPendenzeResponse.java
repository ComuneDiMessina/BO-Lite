package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PMPAYAllineamentoPendenze
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor

public class AllineamentoPendenzeResponse {
	@JsonProperty("Esito")
	private String esito;

	@JsonProperty("Id_Trasmissione")
	private String idTrasmissione;

	@JsonProperty("NumeroPosizioni")
	private String numeroPosizioni;

	@JsonProperty("Errore")
	private String errore;

}
