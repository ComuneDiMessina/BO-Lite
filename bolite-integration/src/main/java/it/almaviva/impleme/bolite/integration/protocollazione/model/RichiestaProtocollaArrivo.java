package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RichiestaProtocollaArrivo {
	@JsonProperty("protocollaArrivo")
    private ProtocollaArrivo protocollaArrivo;
	
	@JsonProperty("documento")
    private Documento documento;

	@JsonProperty("confermaSegnatura")
    private String confermaSegnatura;


}
