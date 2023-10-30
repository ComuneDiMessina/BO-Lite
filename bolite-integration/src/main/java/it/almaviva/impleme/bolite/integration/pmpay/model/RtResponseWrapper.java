package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
@Component
@Setter
@Getter
@ToString
public class RtResponseWrapper {
	@JsonProperty("RICHIEDI_RT_RESPONSE")
	private RtResponse rtResponse;
}
