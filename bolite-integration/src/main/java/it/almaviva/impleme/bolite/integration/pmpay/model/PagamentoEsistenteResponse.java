package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * PAGAMENTOESISTENTERESPONSE
 */
@Component
@Setter
@Getter
@ToString

public class PagamentoEsistenteResponse   {
  @JsonProperty("DATA_RICHIESTA")
  private String dataRichiesta;

  @JsonProperty("ID_RICHIESTA")
  private String idRichiesta;

  @JsonProperty("CODICE_AZIENDA")
  private String codiceAzienda;

  @JsonProperty("URL_TO_CALL")
  private String url_to_call;

}

