package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * Bollo
 */
@Component
@Setter
@Getter
@ToString

public class BolloRequest   {
  @JsonProperty("headerRichiestaBOLLO")
  private HeaderRichiestaBOLLO headerRichiestaBOLLO;

  @JsonProperty("richiestaBOLLO")
  private RichiestaBOLLO richiestaBOLLO;

}

