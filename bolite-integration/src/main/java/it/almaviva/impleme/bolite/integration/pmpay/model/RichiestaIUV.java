package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Setter
@Getter
@ToString
public class RichiestaIUV {
	@JsonProperty("RIF_INTERNO")
	private String rifInterno; 
	
	@JsonProperty("TIPO_REFERENCE")
	private String tipoReference; 
	
	@JsonProperty("RIF_ALFANUMERICO")
	private String rifAlfanumerico; 
	
	@JsonProperty("RIF_CONTABILE")
	private BigDecimal rifContabile; 
	
	@JsonProperty("RIF_COD_UTENTE")
	private String rifCodUtente; 
	
	@JsonProperty("STANDARD_ISO")
	private Boolean standardIso; 
}
