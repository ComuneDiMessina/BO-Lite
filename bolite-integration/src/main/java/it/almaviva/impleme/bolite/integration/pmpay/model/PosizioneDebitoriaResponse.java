package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
public class PosizioneDebitoriaResponse {
    @JsonProperty("esito")
    private String esito;
    
    @JsonProperty("descrizione")
    private String descrizione;
    
    @JsonProperty("IUV")
    private String iuv;
    
    @JsonProperty("codice_errore_pmpay")
    private String codiceErrorePmpay;
    
    @JsonProperty("descrizione_errore_pmpay")
    private String descrizioneErrorePmpay;
}
