package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class RichiestaProtocollazioneRequest {
	@JsonProperty("inserisciArrivo")
	private InserisciArrivo inserisciArrivo;

}
