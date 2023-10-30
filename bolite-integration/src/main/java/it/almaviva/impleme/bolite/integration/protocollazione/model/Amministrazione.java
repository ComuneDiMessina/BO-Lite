package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Amministrazione {
	@JsonProperty("ente")
	private Formato ente;
	@JsonProperty("aoo")
	private Aoo aoo;
	@JsonProperty("uo")
	private Aoo uo;
	@JsonProperty("nota")
	private String nota;

}
