package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RICHIEDIBOLLORESPONSE
 */
@Component
@Setter
@Getter
@ToString

public class BolloResponse {
	@JsonProperty("DATA_RICHIESTA")
	private String dataRichiesta;

	@JsonProperty("ID_RICHIESTA")
	private String idRichiesta;

	@JsonProperty("CODICE_AZIENDA")
	private String codiceAzienda;

	@JsonProperty("STREAM_BOLLO")
	private String streamBollo;

	@JsonProperty("CODICE_ERRORE")
	private String codiceErrore;

	@JsonProperty("DESCRIZIONE_ERRORE")
	private String descrizioneErrore;

}
