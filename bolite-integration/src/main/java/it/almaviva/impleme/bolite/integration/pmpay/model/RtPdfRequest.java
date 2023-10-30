package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RtPdf
 */
@Component
@Setter
@Getter
@ToString

public class RtPdfRequest {
	@JsonProperty("RICHIEDI_RT_REQUEST")
	private RichiediRtRequest richiediRtRequest;

	@JsonProperty("inline")
	private Boolean inline;

}
