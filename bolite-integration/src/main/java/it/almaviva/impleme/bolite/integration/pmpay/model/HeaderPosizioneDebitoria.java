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

public class HeaderPosizioneDebitoria {
	@JsonProperty("TOKEN")
    private String token;
	
	@JsonProperty("DATA_RICHIESTA")
    private String dataRichiesta;
	
	@JsonProperty("ID_RICHIESTA")
    private String idRichiesta;
	
	@JsonProperty("TIPO_REFERENCE")	
    private String tipoReference;	
	
	@JsonProperty("STANDARD_ISO")
    private boolean standardISO;
	
	@JsonProperty("TIPO_CLIENT")
    private String tipoClient;
}
