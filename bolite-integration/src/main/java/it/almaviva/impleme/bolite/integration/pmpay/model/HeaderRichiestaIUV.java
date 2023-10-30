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
public class HeaderRichiestaIUV {
	@JsonProperty("TOKEN")
	private String token; 
	
	@JsonProperty("DATA_RICHIESTA")
	private String dataRichiesta;
	
	@JsonProperty("ID_CLIENT")
	private String idClient;

	@JsonProperty("ID_RICHIESTA")
	private String idrichiesta;

	@JsonProperty("IUV_RICHIESTI")
	private String iuvRichiesti;

}
