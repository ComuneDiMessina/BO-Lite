package it.almaviva.impleme.bolite.integration.notificatore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InsertAttachmentResponse {
	
	@JsonProperty("uuid")
    private String uuid;

}
