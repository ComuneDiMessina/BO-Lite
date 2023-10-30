package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaEsitoAllineamentoPendenze
 */
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor

public class EsitoAllineamentoPendenzeResponseWrapper   {
  @JsonProperty("PMPAY_EsitoAllineamentoPendenze")
  private EsitoAllineamentoPendenzeResponse esitoAllineamentoPendenzeResponse;

  
}

