package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RispostaProtocollazioneWrapper {
	@JsonProperty("rispostaProtocollazione")
	private RispostaProtocollazione richiestaProtocollazioneResponse;

}
