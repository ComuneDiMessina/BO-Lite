package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RispostaBollo
 */
@Component
@Setter
@Getter
@ToString

public class BolloResponseWrapper   {
  @JsonProperty("RICHIEDI_BOLLO_RESPONSE")
  private BolloResponse bolloResponse;

}

