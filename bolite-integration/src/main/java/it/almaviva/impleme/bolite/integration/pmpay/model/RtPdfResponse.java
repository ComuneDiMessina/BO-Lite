package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * GetRTPdfResponse
 */
@Component
@Setter
@Getter
@ToString

public class RtPdfResponse {
	@JsonProperty("esito")
	private String esito;

	@JsonProperty("errore")
	private String errore;

	@JsonProperty("rt_inline")
	private RtInline rtInline;

	@JsonProperty("rt")
	private Rt rt;

}
