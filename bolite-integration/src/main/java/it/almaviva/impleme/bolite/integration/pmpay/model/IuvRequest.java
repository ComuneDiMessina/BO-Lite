package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
@Setter
@Getter
@ToString

@NoArgsConstructor
public class IuvRequest {
	@Autowired
	@JsonProperty("headerRichiestaIUV")
	private HeaderRichiestaIUV headerRichiestaIUV; 
	@Autowired
	@JsonProperty("richiestaIUV")
	private RichiestaIUV richiestaIUV; 
	
	
	
	
}
