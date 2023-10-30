package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * RichiestaBOLLO
 */
@Component
@Setter
@Getter
@ToString
public class RichiestaBOLLO   {
  @JsonProperty("IMPRONTA")
  private String IMPRONTA;

}

