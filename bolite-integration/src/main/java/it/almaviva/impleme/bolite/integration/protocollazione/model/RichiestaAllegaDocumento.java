package it.almaviva.impleme.bolite.integration.protocollazione.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@ToString
@Builder
public class RichiestaAllegaDocumento {
	@Autowired
    @JsonProperty("riferimento")
    private Riferimento riferimento;
	
	@Autowired
    @JsonProperty("documento")
    private Documento documento;


}
