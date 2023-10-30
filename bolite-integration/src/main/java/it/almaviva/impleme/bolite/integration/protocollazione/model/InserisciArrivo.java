package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class InserisciArrivo {
	@JsonProperty("richiestaProtocollaArrivo")
	private RichiestaProtocollaArrivo richiestaProtocollaArrivo;

}
