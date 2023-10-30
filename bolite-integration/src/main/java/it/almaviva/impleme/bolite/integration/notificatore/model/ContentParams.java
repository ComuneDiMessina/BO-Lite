package it.almaviva.impleme.bolite.integration.notificatore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentParams {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("nome_ric")
	private String name;
	
	@JsonProperty("cognome_ric")
	private String surname;
	
	@JsonProperty("spazio")
	private String room_name;
	
	@JsonProperty("importo")
	private String amount;
	
	@JsonProperty("iuv")
	private String iuv;

	@JsonProperty("note")
	private String note;

	@JsonProperty("numero_protocollo")
	private String numeroProtocollo;

	@JsonProperty("data_protocollo")
	private String dataProtocollo;
	
	@JsonProperty("data_prenotazione")
	private String dataPrenotazione;
	
	@JsonProperty("orario_prenotazione")
	private String orarioPrenotazione;
	
	@JsonProperty("causale")
	private String causale;
	
	@JsonProperty("data_scadenza")
	private String dataScadenza;
	
	@JsonProperty("anno")
	private String anno;



	public ContentParams(String id, String name, String surname, String room_name, String amount, String iuv, String note,
			String numeroProtocollo, String dataProtocollo, String dataPrenotazione, String orarioPrenotazione, String causale, String dataScadenza, String anno) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.room_name = room_name;
		this.amount = amount;
		this.iuv = iuv;
		this.note = note;
		this.numeroProtocollo = numeroProtocollo;
		this.dataProtocollo = dataProtocollo;
		this.dataPrenotazione = dataPrenotazione;
		this.orarioPrenotazione = orarioPrenotazione;
		this.causale = causale;
		this.dataScadenza = dataScadenza;
		this.anno = anno;
	}





	
	
	

}
