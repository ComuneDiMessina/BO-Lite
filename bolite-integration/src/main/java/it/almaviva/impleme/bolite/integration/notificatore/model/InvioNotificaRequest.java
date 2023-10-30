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
public class InvioNotificaRequest {
	
	@JsonProperty("userId")
    private String userId;
	
	@JsonProperty("templateId")
    private String templateId;
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("attachment")
	private String attachment;
	
	@JsonProperty("contentParams")
	private ContentParams contentParams;

	@JsonProperty("appio")
	private String appio;

	public InvioNotificaRequest(String userId, String templateId, String subject, String appio) {
		super();
		this.userId = userId;
		this.templateId = templateId;
		this.subject = subject;
		this.appio = appio;
	}

	public InvioNotificaRequest(String userId, String templateId, String subject) {
		super();
		this.userId = userId;
		this.templateId = templateId;
		this.subject = subject;
	}
}
