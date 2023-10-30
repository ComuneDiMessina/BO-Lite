package it.almaviva.impleme.bolite.integration.notificatore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InsertAttachmentRequest {

	@JsonProperty("file")
	private String file;

	public InsertAttachmentRequest(String file) {
		super();
		this.file = file;
	}
}
