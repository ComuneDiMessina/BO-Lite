package it.almaviva.impleme.bolite.integration.serrature.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseOpenDoor {

	@JsonProperty("state")
	private String state;
	@JsonProperty("success")
	private Boolean success;

}
