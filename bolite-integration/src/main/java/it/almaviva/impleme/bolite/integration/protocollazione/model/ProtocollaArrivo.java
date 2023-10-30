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
public class ProtocollaArrivo {
	
	@JsonProperty("corrispondente")
	private Formato corrispondente;
	
	@JsonProperty("soggetti")
	private Soggetti soggetti;
	
	@JsonProperty("smistamenti")
	private Smistamenti smistamenti;
	
	@JsonProperty("oggetto")
	private String oggetto;
	
	@JsonProperty("classificazione")
	private Classificazione classificazione;

	@JsonProperty("altriDati")
	private AltriDati altriDati;

	@JsonProperty("sezione")
	private Sezione sezione;

	@JsonProperty("registro")
	private Registro registro;



}
