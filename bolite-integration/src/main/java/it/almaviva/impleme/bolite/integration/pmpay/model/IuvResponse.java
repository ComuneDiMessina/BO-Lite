package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
@Component
@Setter
@Getter
@ToString
public class IuvResponse {

	
	@JsonProperty("DATA_RICHIESTA")
	private Date dataRichiesta;
	
	@JsonProperty("ID_RICHIESTA")
	private String idrichiesta;

	@JsonProperty("CODICE_AZIENDA")
	private String codiceAzienda;

	@JsonFormat(with=Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@JsonProperty("IUV")
	private ArrayList<String> iuv;

	@JsonProperty("CODICE_ERRORE")
	private Integer codiceErrore;

	@JsonProperty("DESCRIZIONE_ERRORE")
	private String descrizionErrore;
}
