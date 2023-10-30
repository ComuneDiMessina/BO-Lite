package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * REGISTRAPAGAMENTORESPONSE
 */
@Component
@Setter
@Getter
@ToString

public class RegistraPagamentoResponse   {
  @JsonProperty("DATA_RICHIESTA")
  private String dataRichiesta ;

  @JsonProperty("ID_RICHIESTA")
  private String idRichiesta ;

  @JsonProperty("CODICE_AZIENDA")
  private String codiceAzienda ;

  @JsonProperty("ESITO")
  private String esito ;
  
  @JsonProperty("CODICE_ERRORE")
  private String codiceErrore ;
  
  @JsonProperty("DESCRIZIONE_ERRORE")
  private String descrizioneErrore ;


}

