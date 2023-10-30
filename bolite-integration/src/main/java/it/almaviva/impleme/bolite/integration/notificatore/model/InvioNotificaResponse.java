package it.almaviva.impleme.bolite.integration.notificatore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InvioNotificaResponse {
	
	@JsonProperty("code")
    private String code;
	
	@JsonProperty("messageId")
    private String messageId;
	
	@JsonProperty("descrizione")
    private String descrizione;
	
	@JsonProperty("success")
    private String success;

}
