package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Smistamenti {
	@JsonProperty("smistamento")
	private Smistamento smistamento;


}
